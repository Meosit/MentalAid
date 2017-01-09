package by.mksn.epam.mentalaid.command.impl;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.command.resource.PathManager;
import by.mksn.epam.mentalaid.entity.Answer;
import by.mksn.epam.mentalaid.entity.Question;
import by.mksn.epam.mentalaid.service.AnswerService;
import by.mksn.epam.mentalaid.service.QuestionService;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.factory.ServiceFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

import static by.mksn.epam.mentalaid.command.resource.Constants.*;
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

public class GetQuestionPageCommand implements Command {

    private static final String QUESTION_ID_PARAMETER = "quid";
    private static final String QUESTION_ATTRIBUTE = "question";
    private static final String ANSWER_LIST_ATTRIBUTE = "answers";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String quidParameter = request.getParameter(QUESTION_ID_PARAMETER);
        String pagePath;
        try {
            long quid = Long.parseLong(quidParameter);
            try {
                QuestionService questionService = ServiceFactory.getInstance().getQuestionService();
                Question question = questionService.getById(quid);
                if (!isNull(question)) {
                    AnswerService answerService = ServiceFactory.getInstance().getAnswerService();
                    List<Answer> answers = answerService.getAnswersForQuestion(quid);
                    request.setAttribute(ANSWER_LIST_ATTRIBUTE, answers);
                    request.setAttribute(QUESTION_ATTRIBUTE, question);
                    pagePath = PathManager.getProperty(PathManager.QUESTION);
                } else {
                    request.setAttribute(ERROR_TITLE_ATTRIBUTE, ERROR_TITLE_QUESTION_NOT_FOUND);
                    request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_MESSAGE_QUESTION_NOT_FOUND);
                    pagePath = PathManager.getProperty(PathManager.ERROR);
                }
            } catch (ServiceException e) {
                throw new CommandException(e);
            }
        } catch (NumberFormatException e) {
            request.setAttribute(ERROR_TITLE_ATTRIBUTE, ERROR_TITLE_QUESTION_NOT_FOUND);
            request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_MESSAGE_QUESTION_NOT_FOUND);
            pagePath = PathManager.getProperty(PathManager.ERROR);
        }
        Command.dispatchRequest(pagePath, false, request, response);
    }

    @Override
    public String generateFromUrl(String urlWithoutParameters, String[] fromParameterArgs) {
        String url = urlWithoutParameters;
        if (fromParameterArgs.length > 1) {
            url += '&' + QUESTION_ID_PARAMETER + '=';
            url += fromParameterArgs[1];
        }
        return url;
    }
}
