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
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

/**
 * Command to authorize user. This is async command.
 */
public class LoginCommand implements Command {

    private static final Logger logger = Logger.getLogger(LoginCommand.class);
    private static final String USERNAME_PARAMETER = "username";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String REDIRECT_URL_NAME = "redirectUrl";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String username = request.getParameter(USERNAME_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);

        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            if (isNull(user)) {
                UserService userService = ServiceFactory.getInstance().getUserService();
                user = userService.login(username, password);
                setSuccessResponse(request, MapUtil.<String, Object>builder()
                        .put(REDIRECT_URL_NAME, UrlUtil.getBackRedirectUrl(request))
                        .build());
                session.setAttribute(USER_ATTRIBUTE, user);
                session.setAttribute(LOCALE_ATTRIBUTE, user.getLocale());
                logger.debug("User \"" + username + "\" logged in");
            } else {
                logger.debug("User trying to login twice without logout.");
                setErrorResponse(request, ERROR_TITLE_LOGIN_TWICE, ERROR_MESSAGE_LOGIN_TWICE);
            }
        } catch (UserServiceException e) {
            logger.debug("Authorization failed for \"" + username + "\"");
            String errorTitle = null;
            String errorMessage = null;
            switch (e.getCauseCode()) {
                case UserServiceException.INCORRECT_PASSWORD:
                    errorTitle = ERROR_TITLE_LOGIN_PASSWORD;
                    errorMessage = ERROR_MESSAGE_LOGIN_PASSWORD;
                    break;
                case UserServiceException.USER_NOT_EXIST:
                    errorTitle = ERROR_TITLE_LOGIN_NOT_EXISTS;
                    errorMessage = ERROR_MESSAGE_LOGIN_NOT_EXISTS;
                    break;
            }
            setErrorResponse(request, errorTitle, errorMessage);
        } catch (ServiceException e) {
            throw new CommandException(e, true);
        }

        dispatchAjaxRequest(request, response);
    }
}
