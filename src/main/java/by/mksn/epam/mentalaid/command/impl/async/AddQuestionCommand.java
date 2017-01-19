package by.mksn.epam.mentalaid.command.impl.async;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.command.factory.CommandEnum;
import by.mksn.epam.mentalaid.entity.Question;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.QuestionService;
import by.mksn.epam.mentalaid.service.exception.QuestionServiceException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
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

public class AddQuestionCommand implements Command {

    private static final Logger logger = Logger.getLogger(AddQuestionCommand.class);
    private static final String QUESTION_TITLE_PARAMETER = "question_title";
    private static final String QUESTION_DESCRIPTION_PARAMETER = "question_description";
    private static final String REDIRECT_URL_NAME = "redirectUrl";

    private static String getRedirectUrl(HttpServletRequest request, long questionId) {
        return UrlUtil.getServletUrl(request) + "?" +
                COMMAND_PARAMETER + "=" + CommandEnum.QUESTION.name().toLowerCase() +
                "&" + QUESTION_ID_PARAMETER + "=" + questionId;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String titleParameter = request.getParameter(QUESTION_TITLE_PARAMETER);
        String descriptionParameter = request.getParameter(QUESTION_DESCRIPTION_PARAMETER);

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute(USER_ATTRIBUTE);
        if (!isNull(user)) {
            if (user.getStatus() != User.STATUS_BANNED) {
                QuestionService questionService = ServiceFactory.getInstance().getQuestionService();
                Question question = new Question();
                question.setCreatorId(user.getId());
                question.setTitle(titleParameter);
                question.setDescription(descriptionParameter);
                try {
                    question = questionService.add(question);
                    logger.debug("Question added \n" + question);
                    setSuccessResponse(request, MapUtil.<String, Object>builder()
                            .put(REDIRECT_URL_NAME, getRedirectUrl(request, question.getId()))
                            .build());
                } catch (QuestionServiceException e) {
                    setErrorResponse(request, ERROR_TITLE_QUESTION_WRONG_INPUT, ERROR_MESSAGE_QUESTION_WRONNG_INPUT);
                } catch (ServiceException e) {
                    throw new CommandException(e);
                }
            } else {
                logger.warn("Banned user '" + user.getUsername() + "' trying to add question.");
                setUserBannedResponse(request);
            }
        } else {
            logger.warn("Unauthorized user trying to edit question.\n" +
                    "(title=" + titleParameter + "; " +
                    "description=" + descriptionParameter + ")");
            setAccessDeniedResponse(request);
        }

        dispatchAjaxRequest(request, response);
    }
}
