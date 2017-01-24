package by.mksn.epam.mentalaid.command.impl.async;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.entity.Answer;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.AnswerService;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.factory.ServiceFactory;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.mksn.epam.mentalaid.command.resource.Constants.USER_ATTRIBUTE;
import static by.mksn.epam.mentalaid.util.AjaxUtil.*;
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

public class DeleteAnswerCommand implements Command {

    private static final Logger logger = Logger.getLogger(DeleteAnswerCommand.class);
    private static final String ANSWER_ID_PARAMETER = "answer_id";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
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
                        answerService.delete(answer.getId());
                        logger.debug("Answer deleted: \n" + answer);
                        setSuccessResponse(request);
                    } else {
                        logger.warn("User '" + user.getUsername() +
                                "' trying to delete answer (id=" + idParameter + ") without permission.");
                        setAccessDeniedResponse(request);
                    }
                } else {
                    logger.warn("Answer not found (id=" + idParameter + ")");
                    setAccessDeniedResponse(request);
                }
            } else {
                logger.warn("Unauthorized user trying to delete answer.\n" +
                        "(answerID=" + idParameter + ")");
                setAccessDeniedResponse(request);
            }
        } catch (NumberFormatException e) {
            logger.warn("Invalid question ID parameter passed (" + idParameter + ")");
            setAccessDeniedResponse(request);
        } catch (ServiceException e) {
            throw new CommandException(e);
        }

        dispatchAjaxRequest(request, response);
    }
}
