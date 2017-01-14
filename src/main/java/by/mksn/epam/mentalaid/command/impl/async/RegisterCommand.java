package by.mksn.epam.mentalaid.command.impl.async;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.UserService;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.exception.UserServiceException;
import by.mksn.epam.mentalaid.service.factory.ServiceFactory;
import by.mksn.epam.mentalaid.util.MapUtil;
import by.mksn.epam.mentalaid.util.UrlUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.mksn.epam.mentalaid.command.resource.Constants.*;
import static by.mksn.epam.mentalaid.util.AjaxUtil.*;

/**
 * Command to register a new user. This is async command.
 */
public class RegisterCommand implements Command {

    private static final Logger logger = Logger.getLogger(RegisterCommand.class);
    private static final String USERNAME_PARAMETER = "username";
    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String REDIRECT_URL_NAME = "redirectUrl";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String username = request.getParameter(USERNAME_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        try {
            HttpSession session = request.getSession();
            UserService userService = ServiceFactory.getInstance().getUserService();
            User user = userService.register(username, email, password);
            setSuccessResponse(request, MapUtil.<String, Object>builder()
                    .put(REDIRECT_URL_NAME, UrlUtil.getBackRedirectUrl(request))
                    .build());
            session.setAttribute(USER_ATTRIBUTE, user);
        } catch (UserServiceException e) {
            logger.debug("Registration failed for (" + username + ", " + email + ")");
            String errorTitle = null;
            String errorMessage = null;
            switch (e.getCauseCode()) {
                case UserServiceException.USER_EXISTS:
                    errorTitle = ERROR_TITLE_REGISTER_EXISTS_USERNAME;
                    errorMessage = ERROR_MESSAGE_REGISTER_EXISTS_USERNAME;
                    break;
                case UserServiceException.EMAIL_EXISTS:
                    errorTitle = ERROR_TITLE_REGISTER_EXISTS_EMAIL;
                    errorMessage = ERROR_MESSAGE_REGISTER_EXISTS_EMAIL;
                    break;
                case UserServiceException.WRONG_INPUT:
                    errorTitle = ERROR_TITLE_REGISTER_FORMAT;
                    errorMessage = ERROR_MESSAGE_REGISTER_FORMAT;
                    break;
            }
            setErrorResponse(request, errorTitle, errorMessage);
        } catch (ServiceException e) {
            throw new CommandException(e, true);
        }

        dispatchAjaxRequest(request, response);
    }

}
