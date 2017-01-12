package by.mksn.epam.mentalaid.command.impl.async;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.command.resource.PathManager;
import by.mksn.epam.mentalaid.entity.Question;
import by.mksn.epam.mentalaid.entity.User;
import by.mksn.epam.mentalaid.service.QuestionService;
import by.mksn.epam.mentalaid.service.exception.ServiceException;
import by.mksn.epam.mentalaid.service.factory.ServiceFactory;
import by.mksn.epam.mentalaid.util.UrlUtil;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.HashMap;

import static by.mksn.epam.mentalaid.command.resource.Constants.*;
import static by.mksn.epam.mentalaid.util.NullUtil.isNull;

public class DeleteQuestionCommand implements Command {

    private static final Logger logger = Logger.getLogger(DeleteQuestionCommand.class);
    private static final String QUESTION_ID_PARAMETER = "question_id";
    private static final String REDIRECT_URL_NAME = "redirectUrl";

    private static void setAccessDeniedResponse(HttpServletRequest request) {
        request.setAttribute(AJAX_IS_RESULT_SUCCESS_ATTRIBUTE, false);
        request.setAttribute(ERROR_TITLE_ATTRIBUTE, ERROR_TITLE_ACCESS_DENIED);
        request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_MESSAGE_ACCESS_DENIED);
    }

    private static String getRedirectUrl(HttpServletRequest request) {
        return UrlUtil.getServletUrl(request) + "?question_deleted=success";
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
                        request.setAttribute(AJAX_IS_RESULT_SUCCESS_ATTRIBUTE, true);
                        request.setAttribute(AJAX_SUCCESS_VALUE_MAP_ATTRIBUTE, new HashMap<String, String>(1) {{
                            put(REDIRECT_URL_NAME, getRedirectUrl(request));
                        }});
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

        String pagePath = PathManager.getProperty(PathManager.AJAX_RESPONSE);
        Command.dispatchRequest(pagePath, true, request, response);
    }

}
