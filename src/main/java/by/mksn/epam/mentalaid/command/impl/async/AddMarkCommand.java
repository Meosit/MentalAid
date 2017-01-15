package by.mksn.epam.mentalaid.command.impl.async;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.entity.Answer;
import by.mksn.epam.mentalaid.entity.Mark;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.AnswerService;
import by.mksn.epam.mentalaid.service.MarkService;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.factory.ServiceFactory;
import by.mksn.epam.mentalaid.util.MapUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.mksn.epam.mentalaid.command.resource.Constants.USER_ATTRIBUTE;
import static by.mksn.epam.mentalaid.util.AjaxUtil.dispatchAjaxRequest;
import static by.mksn.epam.mentalaid.util.AjaxUtil.setSuccessResponse;
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

public class AddMarkCommand implements Command {

    private static final Logger logger = Logger.getLogger(AddMarkCommand.class);
    private static final String ANSWER_ID_PARAMETER = "answer_id";
    private static final String VALUE_PARAMETER = "value";
    private static final String RESULT_STATUS_NAME = "resultStatus";
    private static final String RESULT_STATUS_OK = "ok";
    private static final String RESULT_STATUS_DENIED = "denied";
    private static final String RESULT_STATUS_ERROR = "error";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String answerIdParameter = request.getParameter(ANSWER_ID_PARAMETER);
        String valueParameter = request.getParameter(VALUE_PARAMETER);

        try {
            long answerId = Long.parseLong(answerIdParameter);
            int value = Integer.parseInt(valueParameter);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            if (!isNull(user)) {
                AnswerService answerService = ServiceFactory.getInstance().getAnswerService();
                Answer answer = answerService.getById(answerId);
                if (!isNull(answer)) {
                    if (answer.getCreatorId() != user.getId()) {
                        MarkService markService = ServiceFactory.getInstance().getMarkService();
                        Mark mark = new Mark();
                        mark.setAnswerId(answerId);
                        mark.setValue(value);
                        mark.setUserId(user.getId());
                        markService.add(mark);
                        logger.debug("Added mark " + mark);
                        setSuccessResponse(request,
                                MapUtil.<String, Object>builder().put(RESULT_STATUS_NAME, RESULT_STATUS_OK).build());
                    } else {
                        logger.debug("User " + user.getUsername() + " trying to add mark to his own answer");
                        setSuccessResponse(request,
                                MapUtil.<String, Object>builder().put(RESULT_STATUS_NAME, RESULT_STATUS_DENIED).build());
                    }
                } else {
                    logger.warn("User " + user.getUsername() + " trying to add mark to not existing answer");
                    setSuccessResponse(request,
                            MapUtil.<String, Object>builder().put(RESULT_STATUS_NAME, RESULT_STATUS_DENIED).build());
                }
            } else {
                logger.warn("Unauthorized user trying to add mark.");
                setSuccessResponse(request,
                        MapUtil.<String, Object>builder().put(RESULT_STATUS_NAME, RESULT_STATUS_DENIED).build());
            }
        } catch (NumberFormatException e) {
            logger.warn("Invalid answer id or mark value passed (answerId=" + answerIdParameter + "; mark=" + valueParameter + ")");
            setSuccessResponse(request,
                    MapUtil.<String, Object>builder().put(RESULT_STATUS_NAME, RESULT_STATUS_ERROR).build());
        } catch (ServiceException e) {
            logger.warn("Cannot add mark \n", e);
            setSuccessResponse(request,
                    MapUtil.<String, Object>builder().put(RESULT_STATUS_NAME, RESULT_STATUS_ERROR).build());
        }

        dispatchAjaxRequest(request, response);
    }
}
