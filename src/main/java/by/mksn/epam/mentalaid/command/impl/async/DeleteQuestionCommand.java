package by.mksn.epam.mentalaid.command.impl.async;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.entity.Question;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.QuestionService;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.factory.ServiceFactory;
import by.mksn.epam.mentalaid.util.MapUtil;
import by.mksn.epam.mentalaid.util.UrlUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import static by.mksn.epam.mentalaid.command.resource.Constants.USER_ATTRIBUTE;
import static by.mksn.epam.mentalaid.util.AjaxUtil.*;
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

public class DeleteQuestionCommand implements Command {

    private static final Logger logger = Logger.getLogger(DeleteQuestionCommand.class);
    private static final String QUESTION_ID_PARAMETER = "question_id";
    private static final String REDIRECT_URL_NAME = "redirectUrl";

    private static String getRedirectUrl(HttpServletRequest request) {
        return UrlUtil.getBackRedirectUrl(request) + "?question_deleted=success";
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String idParameter = request.getParameter(QUESTION_ID_PARAMETER);

        try {
            long aid = Long.parseLong(idParameter);
            HttpSession session = request.getSession();
            User user = (User) session.getAttribute(USER_ATTRIBUTE);
            if (!isNull(user)) {
                QuestionService questionService = ServiceFactory.getInstance().getQuestionService();
                Question question = questionService.getById(aid);
                if (!isNull(question)) {
                    if (question.getCreatorId() == user.getId() || user.getRole() == User.ROLE_ADMIN) {
                        questionService.delete(question.getId());
                        logger.debug("Question deleted: \n" + question);
                        setSuccessResponse(request, MapUtil.<String, Object>builder()
                                .put(REDIRECT_URL_NAME, getRedirectUrl(request))
                                .build());
                    } else {
                        logger.warn("User '" + user.getUsername() +
                                "' trying to delete question (id=" + idParameter + ") without permission.");
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
