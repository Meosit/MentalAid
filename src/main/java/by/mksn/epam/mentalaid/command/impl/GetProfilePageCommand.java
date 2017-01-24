package by.mksn.epam.mentalaid.command.impl;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.command.resource.PathManager;
import by.mksn.epam.mentalaid.entity.Question;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.AnswerService;
import by.mksn.epam.mentalaid.service.ItemPageService;
import by.mksn.epam.mentalaid.service.QuestionService;
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

public class GetProfilePageCommand implements Command {

    private static final Logger logger = Logger.getLogger(GetProfilePageCommand.class);
    private static final String USERNAME_PARAMETER = "username";
    private static final String QUESTION_COUNT_ATTRIBUTE = "questionCount";
    private static final String ANSWER_COUNT_ATTRIBUTE = "answerCount";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String pageParameter = request.getParameter(PAGE_INDEX_PARAMETER);
        String searchQuery = request.getParameter(SEARCH_QUERY_PARAMETER);
        String usernameParameter = request.getParameter(USERNAME_PARAMETER);

        int pageIndex = 1;
        if (!isNullOrEmpty(pageParameter)) {
            try {
                pageIndex = Integer.parseInt(pageParameter);
            } catch (NumberFormatException e) {
                logger.trace("Invalid page parameter passed (" + pageParameter + ")");
            }
        }

        String pagePath;
        if (!isNullOrEmpty(usernameParameter)) {
            HttpSession session = request.getSession();
            User loggedUser = (User) session.getAttribute(USER_ATTRIBUTE);
            User user;
            if (!isNull(loggedUser) && usernameParameter.equals(loggedUser.getUsername())) {
                user = loggedUser;
            } else {
                try {
                    UserService userService = ServiceFactory.getInstance().getUserService();
                    user = userService.getByUsername(usernameParameter);
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }
            }

            if (!isNull(user) && user.getStatus() != User.STATUS_DELETED) {
                ItemPageService.ItemsPage<Question> questionsPage;
                int questionCount;
                int answerCount;
                try {
                    QuestionService questionService = ServiceFactory.getInstance().getQuestionService();
                    if (isNullOrEmpty(searchQuery)) {
                        questionsPage = questionService.getPageForUser(user.getUsername(), pageIndex);
                    } else {
                        questionsPage = questionService.getSearchPageForUser(user.getUsername(), pageIndex, searchQuery);
                    }

                    questionCount = questionService.getUserQuestionCount(user.getUsername());
                    AnswerService answerService = ServiceFactory.getInstance().getAnswerService();
                    answerCount = answerService.getCountByUserId(user.getId());
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }
                request.setAttribute(USER_ATTRIBUTE, user);
                request.setAttribute(QUESTION_COUNT_ATTRIBUTE, questionCount);
                request.setAttribute(ANSWER_COUNT_ATTRIBUTE, answerCount);
                request.setAttribute(QUESTION_LIST_ATTRIBUTE, questionsPage.getItems());
                request.setAttribute(PAGE_COUNT_ATTRIBUTE, questionsPage.getPageCount());
                request.setAttribute(CURRENT_PAGE_ATTRIBUTE, questionsPage.getCurrentIndex());
                pagePath = PathManager.getProperty(PathManager.PROFILE);
            } else {
                request.setAttribute(ERROR_TITLE_ATTRIBUTE, ERROR_TITLE_PROFILE_NOT_FOUND);
                request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_MESSAGE_PROFILE_NOT_FOUND);
                pagePath = PathManager.getProperty(PathManager.ERROR);
            }
        } else {
            request.setAttribute(ERROR_TITLE_ATTRIBUTE, ERROR_TITLE_PROFILE_NOT_FOUND);
            request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_MESSAGE_PROFILE_NOT_FOUND);
            pagePath = PathManager.getProperty(PathManager.ERROR);
        }
        Command.dispatchRequest(pagePath, false, request, response);
    }
}
