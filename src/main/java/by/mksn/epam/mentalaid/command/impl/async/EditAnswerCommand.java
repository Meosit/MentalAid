package by.mksn.epam.mentalaid.command.impl.async;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.entity.Answer;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.AnswerService;
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

public class EditAnswerCommand implements Command {

    private static final Logger logger = Logger.getLogger(EditAnswerCommand.class);
    private static final String ANSWER_TEXT_PARAMETER = "answer_text";
    private static final String ANSWER_ID_PARAMETER = "answer_id";
    private static final String MODIFIED_AT_NAME = "modifiedAt";

    private static String formatDateTime(Timestamp timestamp, String locale) {
        return DateFormat
                .getDateTimeInstance(DateFormat.LONG, DateFormat.SHORT, new Locale(locale))
                .format(timestamp);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String textParameter = request.getParameter(ANSWER_TEXT_PARAMETER);
        String idParameter = request.getParameter(ANSWER_ID_PARAMETER);

        try {
            long aid = Long.parseLong(idParameter);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            if (!isNull(user)) {
                AnswerService answerService = ServiceFactory.getInstance().getAnswerService();
                Answer answer = answerService.getById(aid);
                if (!isNull(answer)) {
                    if (answer.getCreatorId() == user.getId() || user.getRole() == User.ROLE_ADMIN) {
                        answer.setText(textParameter);
                        answerService.update(answer);
                        logger.debug("Answer updated: \n" + answer);
                        setSuccessResponse(request, MapUtil.<String, Object>builder()
                                .put(MODIFIED_AT_NAME, formatDateTime(
                                        answer.getModifiedAt(),
                                        (String) session.getAttribute(LOCALE_ATTRIBUTE)))
                                .build());
                    } else {
                        logger.warn("User '" + user.getUsername() +
                                "' trying to edit answer (id=" + idParameter + ") without permission.");
                        setAccessDeniedResponse(request);
                    }
                } else {
                    logger.warn("Answer not found (id=" + idParameter + ")");
                    setAccessDeniedResponse(request);
                }
            } else {
                logger.warn("Unauthorized user trying to edit answer.\n" +
                        "(answerID=" + idParameter + "; " +
                        "newText=" + textParameter + ")");
                setAccessDeniedResponse(request);
            }
        } catch (NumberFormatException e) {
            logger.warn("Invalid question ID parameter passed (" + idParameter + ")");
            setAccessDeniedResponse(request);
        } catch (AnswerServiceException e) {
            setErrorResponse(request, ERROR_TITLE_ANSWER_WRONG_INPUT, ERROR_MESSAGE_ANSWER_WRONNG_INPUT);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        dispatchAjaxRequest(request, response);
    }
}
