package by.mksn.epam.mentalaid.command.impl.async;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.command.resource.PathManager;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.UserService;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.exception.UserServiceException;
import by.mksn.epam.mentalaid.service.factory.ServiceFactory;
import by.mksn.epam.mentalaid.util.UrlUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

import static by.mksn.epam.mentalaid.command.resource.Constants.*;

/**
 * Command to register a new user. This is async command.
 */
public class RegisterCommand implements Command {

    private static final Logger logger = Logger.getLogger(RegisterCommand.class);
    private static final String USERNAME_PARAMETER = "username";
    private static final String EMAIL_PARAMETER = "email";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String REDIRECT_VALUE_NAME = "redirectUrl";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String username = request.getParameter(USERNAME_PARAMETER);
        String email = request.getParameter(EMAIL_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        try {
            HttpSession session = request.getSession();
            UserService userService = ServiceFactory.getInstance().getUserService();
            User user = userService.register(username, email, password);
            request.setAttribute(AJAX_IS_RESULT_SUCCESS_ATTRIBUTE, true);
            request.setAttribute(AJAX_SUCCESS_VALUE_MAP_ATTRIBUTE, new HashMap<String, String>(1) {{
                put(REDIRECT_VALUE_NAME, UrlUtil.getBackRedirectUrl(request));
            }});
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
            request.setAttribute(AJAX_IS_RESULT_SUCCESS_ATTRIBUTE, false);
            request.setAttribute(ERROR_TITLE_ATTRIBUTE, errorTitle);
            request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, errorMessage);
        } catch (ServiceException e) {
            throw new CommandException(e, true);
        }

        String pagePath = PathManager.getProperty(PathManager.AJAX_RESPONSE);
        Command.dispatchRequest(pagePath, true, request, response);
    }

}
