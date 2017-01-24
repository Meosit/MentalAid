package by.mksn.epam.mentalaid.command.impl.async;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.UserService;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.factory.ServiceFactory;
import by.mksn.epam.mentalaid.util.MapUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.mksn.epam.mentalaid.command.resource.Constants.USER_ATTRIBUTE;
import static by.mksn.epam.mentalaid.util.AjaxUtil.*;
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

public class BanUserCommand implements Command {

    private static final Logger logger = Logger.getLogger(BanUserCommand.class);
    private static final String USERNAME_PARAMETER = "username";
    private static final String IS_USER_BANNED_NAME = "isUserBanned";

    private static boolean canBanUser(User user) {
        return !isNull(user) &&
                user.getStatus() != User.STATUS_DELETED &&
                user.getRole() != User.ROLE_ADMIN;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String usernameParameter = request.getParameter(USERNAME_PARAMETER);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        if (!isNull(user)) {
            if (user.getRole() == User.ROLE_ADMIN) {
                UserService userService = ServiceFactory.getInstance().getUserService();
                try {
                    User targetUser = userService.getByUsername(usernameParameter);
                    if (!user.equals(targetUser)) {
                        if (canBanUser(targetUser)) {
                            boolean isUserWasBanned = targetUser.getStatus() == User.STATUS_BANNED;
                            targetUser.setStatus(
                                    isUserWasBanned ? User.STATUS_ACTIVE : User.STATUS_BANNED
                            );
                            userService.updateUser(targetUser);
                            setSuccessResponse(request, MapUtil.<String, Object>builder()
                                    .put(IS_USER_BANNED_NAME, !isUserWasBanned)
                                    .build());
                            logger.debug("User " + targetUser.getUsername() + " " +
                                    (isUserWasBanned ? "unbanned" : "banned") + " by " + user.getUsername());
                        } else {
                            logger.warn("Admin " + user.getUsername() + " trying to ban/unban deleted or not existed user or other admin" +
                                    " with username " + usernameParameter);
                            setAccessDeniedResponse(request);
                        }
                    } else {
                        logger.warn("Admin " + user.getUsername() + " trying to ban/unban himself");
                        setAccessDeniedResponse(request);
                    }
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }

            } else {
                logger.warn("Simple user (not admin) " + user.getUsername() + " trying to ban user with username " + usernameParameter);
                setAccessDeniedResponse(request);
            }
        } else {
            logger.warn("Unauthorized user trying to ban user with username " + usernameParameter);
            setAccessDeniedResponse(request);
        }

        dispatchAjaxRequest(request, response);
    }
}
