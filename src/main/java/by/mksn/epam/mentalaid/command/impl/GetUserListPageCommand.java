package by.mksn.epam.mentalaid.command.impl;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.command.resource.PathManager;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.ItemPageService;
import by.mksn.epam.mentalaid.service.UserService;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.mksn.epam.mentalaid.command.resource.Constants.*;
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;
import static by.mksn.epam.mentalaid.util.StringUtil.isNullOrEmpty;

public class GetUserListPageCommand implements Command {

    private static final Logger logger = Logger.getLogger(GetUserListPageCommand.class);
    private static final String USER_LIST_ATTRIBUTE = "users";

    private static boolean isAuthorizedAdmin(HttpServletRequest request) {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        return (!isNull(user) && user.getRole() == User.ROLE_ADMIN);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String pageParameter = request.getParameter(PAGE_INDEX_PARAMETER);
        String searchQuery = request.getParameter(SEARCH_QUERY_PARAMETER);

        int pageIndex = 1;
        if (!isNullOrEmpty(pageParameter)) {
            try {
                pageIndex = Integer.parseInt(pageParameter);
            } catch (NumberFormatException e) {
                logger.trace("Invalid page parameter passed (" + pageParameter + ")");
            }
        }

        String pagePath;
        if (isAuthorizedAdmin(request)) {
            ItemPageService.ItemsPage<User> usersPage;
            try {
                UserService userService = ServiceFactory.getInstance().getUserService();
                if (isNullOrEmpty(searchQuery)) {
                    usersPage = userService.getPage(pageIndex);
                } else {
                    usersPage = userService.getSearchPage(pageIndex, searchQuery);
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }

            request.setAttribute(CURRENT_PAGE_ATTRIBUTE, usersPage.getCurrentIndex());
            request.setAttribute(USER_LIST_ATTRIBUTE, usersPage.getItems());
            request.setAttribute(PAGE_COUNT_ATTRIBUTE, usersPage.getPageCount());
            pagePath = PathManager.getProperty(PathManager.USER_LIST);
        } else {
            request.setAttribute(ERROR_TITLE_ATTRIBUTE, ERROR_TITLE_ACCESS_DENIED);
            request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_MESSAGE_ACCESS_DENIED);
            pagePath = PathManager.getProperty(PathManager.ERROR);
            logger.warn("Simple user trying to get user list");
        }

        Command.dispatchRequest(pagePath, false, request, response);
    }
}
