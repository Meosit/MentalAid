package by.mksn.epam.mentalaid.command.impl.async;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.entity.Answer;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.AnswerService;
import by.mksn.epam.mentalaid.service.QuestionService;
import by.mksn.epam.mentalaid.service.exception.AnswerServiceException;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.factory.ServiceFactory;
import by.mksn.epam.mentalaid.util.MapUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.util.Locale;

import static by.mksn.epam.mentalaid.command.resource.Constants.*;
import static by.mksn.epam.mentalaid.util.AjaxUtil.*;
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

public class AddAnswerCommand implements Command {

    private static final Logger logger = Logger.getLogger(AddAnswerCommand.class);
    private static final String QUESTION_ID_PARAMETER = "question_id";
    private static final String NEW_ANSWER_TEXT_PARAMETER = "new_answer_text";
    private static final String CREATED_AT_NAME = "createdAt";
    private static final String CREATOR_USERNAME_NAME = "creatorUsername";
    private static final String ANSWER_ID_NAME = "answerId";

    private static String formatDateTime(Timestamp timestamp, String locale) {
        return DateFormat
                .getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, new Locale(locale))
                .format(timestamp);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String questionIdParameter = request.getParameter(QUESTION_ID_PARAMETER);
        String answerTextParameter = request.getParameter(NEW_ANSWER_TEXT_PARAMETER);

        try {
            long quid = Long.parseLong(questionIdParameter);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            if (!isNull(user)) {
                QuestionService questionService = ServiceFactory.getInstance().getQuestionService();
                if (questionService.isQuestionExists(quid)) {
                    AnswerService answerService = ServiceFactory.getInstance().getAnswerService();
                    if (!answerService.isAnswerAlreadyExists(quid, user.getId())) {
                        Answer newAnswer = new Answer();
                        newAnswer.setQuestionId(quid);
                        newAnswer.setCreatorId(user.getId());
                        newAnswer.setText(answerTextParameter);
                        newAnswer = answerService.add(newAnswer);
                        setSuccessResponse(request, MapUtil.<String, Object>builder()
                                .put(ANSWER_ID_NAME, newAnswer.getId())
                                .put(CREATOR_USERNAME_NAME, newAnswer.getCreatorUsername())
                                .put(CREATED_AT_NAME,
                                        formatDateTime(newAnswer.getCreatedAt(),
                                                (String) session.getAttribute(LOCALE_ATTRIBUTE)))
                                .build());
                    } else {
                        logger.warn("User '" + user.getUsername() +
                                "' trying to add two answers for one question (questionId="
                                + questionIdParameter + ")");
                        setErrorResponse(request, ERROR_TITLE_ANSWER_EXISTS, ERROR_MESSAGE_ANSWER_EXISTS);
                    }
                } else {
                    logger.warn("User '" + user.getUsername() +
                            "' trying to add answer to not existing question (questionID="
                            + questionIdParameter + "; " + "newText=" + answerTextParameter + ")");
                    setErrorResponse(request, ERROR_TITLE_QUESTION_NOT_FOUND, ERROR_MESSAGE_QUESTION_NOT_FOUND);
                }
            } else {
                logger.warn("Unauthorized user trying to add answer.\n" +
                        "(questionID=" + questionIdParameter + "; " +
                        "newText=" + answerTextParameter + ")");
                setAccessDeniedResponse(request);
            }
        } catch (NumberFormatException e) {
            logger.warn("Invalid question ID parameter passed (" + questionIdParameter + ")");
            setAccessDeniedResponse(request);
        } catch (AnswerServiceException e) {
            setErrorResponse(request, ERROR_TITLE_ANSWER_WRONG_INPUT, ERROR_MESSAGE_ANSWER_WRONNG_INPUT);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        dispatchAjaxRequest(request, response);
    }
}
