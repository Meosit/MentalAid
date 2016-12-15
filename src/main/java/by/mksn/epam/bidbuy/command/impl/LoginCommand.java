package by.mksn.epam.bidbuy.command.impl;

import by.mksn.epam.bidbuy.command.Command;
import by.mksn.epam.bidbuy.command.exception.CommandException;
import by.mksn.epam.bidbuy.command.resource.PathManager;
import by.mksn.epam.bidbuy.entity.User;
import by.mksn.epam.bidbuy.service.UserService;
import by.mksn.epam.bidbuy.service.exception.ServiceException;
import by.mksn.epam.bidbuy.service.exception.ServiceUserException;
import by.mksn.epam.bidbuy.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static by.mksn.epam.bidbuy.command.resource.Constants.*;

/**
 * Command to authorize user. This is async command.
 */
public class LoginCommand implements Command {

    private static final Logger logger = Logger.getLogger(LoginCommand.class);
    private static final String USERNAME_PARAMETER = "username";
    private static final String PASSWORD_PARAMETER = "password";
    private static final String AJAX_STATUS_ATTRIBUTE = "responseStatus";
    private static final String AJAX_STATUS_OK = "ok";
    private static final String AJAX_STATUS_FAIL = "fail";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        UserService userService = ServiceFactory.getInstance().getUserService();
        String username = request.getParameter(USERNAME_PARAMETER);
        String password = request.getParameter(PASSWORD_PARAMETER);
        try {
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            if (user == null) {
                user = userService.login(username, password);
                request.setAttribute(AJAX_STATUS_ATTRIBUTE, AJAX_STATUS_OK);
                session.setAttribute(USER_ATTRIBUTE, user);
                session.setAttribute(LOCALE_ATTRIBUTE, user.getLocale());
            } else {
                logger.debug("User trying to login twice without logout.");
                request.setAttribute(AJAX_STATUS_ATTRIBUTE, AJAX_STATUS_FAIL);
                request.setAttribute(ERROR_TITLE_ATTRIBUTE, ERROR_TITLE_LOGIN_TWICE);
                request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_MESSAGE_LOGIN_TWICE);
            }
        } catch (ServiceUserException e) {
            logger.debug("Authorization failed for \"" + username + "\"");
            String errorTitle = null;
            String errorMessage = null;
            switch (e.getCauseCode()) {
                case ServiceUserException.INCORRECT_PASSWORD:
                    errorTitle = ERROR_TITLE_LOGIN_PASSWORD;
                    errorMessage = ERROR_MESSAGE_LOGIN_PASSWORD;
                    break;
                case ServiceUserException.USER_NOT_EXIST:
                    errorTitle = ERROR_TITLE_LOGIN_NOT_EXISTS;
                    errorMessage = ERROR_MESSAGE_LOGIN_NOT_EXISTS;
                    break;
            }
            request.setAttribute(AJAX_STATUS_ATTRIBUTE, AJAX_STATUS_FAIL);
            request.setAttribute(ERROR_TITLE_ATTRIBUTE, errorTitle);
            request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, errorMessage);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        try {
            String pagePath = PathManager.getProperty(PathManager.AJAX_RESPONSE);
            request.getRequestDispatcher(pagePath).forward(request, response);
        } catch (ServletException e) {
            throw new CommandException("Servlet exception occurs. ", e);
        } catch (IOException e) {
            throw new CommandException("IOException exception occurs. ", e);
        }
    }
}
