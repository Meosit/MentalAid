package by.mksn.epam.mentalaid.command.impl.async;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.UserService;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.mksn.epam.mentalaid.command.resource.Constants.USER_ATTRIBUTE;
import static by.mksn.epam.mentalaid.util.AjaxUtil.*;
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

public class UpdateProfileCommand implements Command {

    private static final Logger logger = Logger.getLogger(UpdateProfileCommand.class);
    private static final String IMAGE_URL_PARAMETER = "image_url";
    private static final String WEBSITE_PARAMETER = "website";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String imageUrlParameter = request.getParameter(IMAGE_URL_PARAMETER);
        String websiteParameter = request.getParameter(WEBSITE_PARAMETER);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        if (!isNull(user)) {
            user.setImageUrl(imageUrlParameter);
            user.setWebsite(websiteParameter);
            try {
                UserService userService = ServiceFactory.getInstance().getUserService();
                userService.updateUser(user);
                setSuccessResponse(request);
                logger.debug("Profile updated for user " + user.getUsername());
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } else {
            logger.warn("Unauthorized user trying to update profile.");
            setAccessDeniedResponse(request);
        }

        dispatchAjaxRequest(request, response);
    }
}
