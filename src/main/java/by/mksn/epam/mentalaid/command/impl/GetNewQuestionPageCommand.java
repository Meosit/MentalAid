package by.mksn.epam.mentalaid.command.impl;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.command.resource.PathManager;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.util.UrlUtil;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.mksn.epam.mentalaid.command.resource.Constants.*;
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

public class GetNewQuestionPageCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        String pagePath;
        if (!isNull(user)) {
            if (user.getStatus() != User.STATUS_BANNED) {
                pagePath = PathManager.getProperty(PathManager.NEW_QUESTION);
            } else {
                request.setAttribute(ERROR_TITLE_ATTRIBUTE, ERROR_TITLE_USER_BANNED);
                request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_MESSAGE_USER_BANNED);
                pagePath = PathManager.getProperty(PathManager.ERROR);
            }
            Command.dispatchRequest(pagePath, false, request, response);
        } else {
            Command.sendRedirect(UrlUtil.getServletUrl(request), response);
        }

    }
}
