package by.mksn.epam.mentalaid.command.impl.async;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.entity.Question;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.QuestionService;
import by.mksn.epam.mentalaid.service.exception.QuestionServiceException;
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

public class EditQuestionCommand implements Command {

    private static final Logger logger = Logger.getLogger(EditQuestionCommand.class);
    private static final String QUESTION_TITLE_PARAMETER = "question_title";
    private static final String QUESTION_DESCRIPTION_PARAMETER = "question_description";
    private static final String QUESTION_ID_PARAMETER = "question_id";
    private static final String MODIFIED_AT_NAME = "modifiedAt";

    private static void setNotFoundResponse(HttpServletRequest request) {
        setErrorResponse(request, ERROR_TITLE_QUESTION_NOT_FOUND, ERROR_MESSAGE_QUESTION_NOT_FOUND);
    }

    private static String formatDateTime(Timestamp timestamp, String locale) {
        return DateFormat
                .getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, new Locale(locale))
                .format(timestamp);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String titleParameter = request.getParameter(QUESTION_TITLE_PARAMETER);
        String descriptionParameter = request.getParameter(QUESTION_DESCRIPTION_PARAMETER);
        String idParameter = request.getParameter(QUESTION_ID_PARAMETER);

        try {
            long quid = Long.parseLong(idParameter);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            if (!isNull(user)) {
                QuestionService questionService = ServiceFactory.getInstance().getQuestionService();
                Question question = questionService.getById(quid);
                if (!isNull(question)) {
                    if (question.getCreatorId() == user.getId() || user.getRole() == User.ROLE_ADMIN) {
                        question.setTitle(titleParameter);
                        question.setDescription(descriptionParameter);
                        questionService.update(question);
                        logger.debug("Question updated: \n" + question);
                        setSuccessResponse(request, MapUtil.<String, Object>builder()
                                .put(MODIFIED_AT_NAME, formatDateTime(
                                        question.getModifiedAt(),
                                        (String) session.getAttribute(LOCALE_ATTRIBUTE)))
                                .build());
                    } else {
                        logger.warn("User '" + user.getUsername() +
                                "' trying to edit question (id=" + idParameter + ") without permission.");
                        setAccessDeniedResponse(request);
                    }
                } else {
                    logger.warn("Question not found (id=" + idParameter + ")");
                    setNotFoundResponse(request);
                }
            } else {
                logger.warn("Unauthorized user trying to edit question.\n" +
                        "(questionID=" + idParameter + "; " +
                        "newTitle=" + titleParameter + "; " +
                        "newDescription=" + descriptionParameter + ")");
                setAccessDeniedResponse(request);
            }
        } catch (NumberFormatException e) {
            logger.warn("Invalid question ID parameter passed (" + idParameter + ")");
            setNotFoundResponse(request);
        } catch (QuestionServiceException e) {
            setErrorResponse(request, ERROR_TITLE_QUESTION_WRONG_INPUT, ERROR_MESSAGE_QUESTION_WRONNG_INPUT);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        dispatchAjaxRequest(request, response);
    }

}
