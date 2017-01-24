package by.mksn.epam.mentalaid.command.impl.async;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.UserService;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.exception.UserServiceException;
import by.mksn.epam.mentalaid.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.mksn.epam.mentalaid.command.resource.Constants.*;
import static by.mksn.epam.mentalaid.util.AjaxUtil.*;
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

public class ChangePasswordCommand implements Command {

    private static final Logger logger = Logger.getLogger(ChangePasswordCommand.class);
    private static final String OLD_PASSWORD_PARAMETER = "old_password";
    private static final String NEW_PASSWORD_PARAMETER = "new_password";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String oldPasswordParameter = request.getParameter(OLD_PASSWORD_PARAMETER);
        String newPasswordParameter = request.getParameter(NEW_PASSWORD_PARAMETER);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        if (!isNull(user)) {
            try {
                UserService userService = ServiceFactory.getInstance().getUserService();
                userService.updateUserPassword(user, oldPasswordParameter, newPasswordParameter);
                setSuccessResponse(request);
                logger.debug("Password changed for user " + user.getUsername());
            } catch (UserServiceException e) {
                logger.warn("Changing password failed for user " + user);
                switch (e.getCauseCode()) {
                    case UserServiceException.INCORRECT_PASSWORD:
                        setErrorResponse(request,
                                ERROR_TITLE_SETTINGS_INCORRECT_PASSWORD,
                                ERROR_MESSAGE_SETTINGS_INCORRECT_PASSWORD
                        );
                        break;
                    case UserServiceException.WRONG_INPUT:
                        setErrorResponse(request,
                                ERROR_TITLE_SETTINGS_WRONG_INPUT,
                                ERROR_MESSAGE_SETTINGS_WRONG_INPUT
                        );
                        break;
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            logger.warn("Unauthorized user trying to change password.");
            setAccessDeniedResponse(request);
        }

        dispatchAjaxRequest(request, response);
    }
}
