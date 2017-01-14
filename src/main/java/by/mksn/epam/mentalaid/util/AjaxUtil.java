package by.mksn.epam.mentalaid.util;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.command.resource.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static by.mksn.epam.mentalaid.command.resource.Constants.*;

/**
 * Provides some usable method for ajax commands
 */
public final class AjaxUtil {

    private AjaxUtil() {
    }

    /**
     * Sets to request ajax error attributes
     *
     * @param request         request where this attributes will be set
     * @param errorTitleKey   title key of appropriate error
     * @param errorMessageKey message key of appropriate error
     */
    public static void setErrorResponse(
            HttpServletRequest request, String errorTitleKey, String errorMessageKey) {
        request.setAttribute(AJAX_IS_RESULT_SUCCESS_ATTRIBUTE, false);
        request.setAttribute(ERROR_TITLE_ATTRIBUTE, errorTitleKey);
        request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, errorMessageKey);
    }

    /**
     * Sets to request ajax success attributes
     *
     * @param request       request where this attributes will be set
     * @param successValues map of success values which will be passed to client through JSON
     */
    public static void setSuccessResponse(HttpServletRequest request, Map<String, Object> successValues) {
        request.setAttribute(AJAX_IS_RESULT_SUCCESS_ATTRIBUTE, true);
        request.setAttribute(AJAX_SUCCESS_VALUE_MAP_ATTRIBUTE, successValues);
    }

    /**
     * Sets to request ajax access denied error attributes.
     * Usually this response used if user is trying to do something what he is not permitted to.
     *
     * @param request request where this attributes will be set
     */
    public static void setAccessDeniedResponse(HttpServletRequest request) {
        request.setAttribute(AJAX_IS_RESULT_SUCCESS_ATTRIBUTE, false);
        request.setAttribute(ERROR_TITLE_ATTRIBUTE, ERROR_TITLE_ACCESS_DENIED);
        request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_MESSAGE_ACCESS_DENIED);
    }

    /**
     * Dispatches ajax request
     *
     * @param request  request to dispatch
     * @param response response to dispatch
     * @throws CommandException if cannot dispatch ajax request
     */
    public static void dispatchAjaxRequest(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String pagePath = PathManager.getProperty(PathManager.AJAX_RESPONSE);
        Command.dispatchRequest(pagePath, true, request, response);
    }
}
