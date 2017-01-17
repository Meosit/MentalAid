package by.mksn.epam.mentalaid.command.impl;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.mksn.epam.mentalaid.command.resource.Constants.PAGE_INDEX_PARAMETER;
import static by.mksn.epam.mentalaid.command.resource.Constants.SEARCH_QUERY_PARAMETER;
import static by.mksn.epam.mentalaid.util.UrlUtil.encodeUrl;
import static by.mksn.epam.mentalaid.util.UrlUtil.removeParameterFromUrl;

public class SearchRedirectCommand implements Command {

    private static final String BASE_URL_PARAMETER = "baseUrl";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String baseUrlParameter = request.getParameter(BASE_URL_PARAMETER);
        String queryParameter = request.getParameter(SEARCH_QUERY_PARAMETER);

        String redirectUrl = removeParameterFromUrl(baseUrlParameter, PAGE_INDEX_PARAMETER);
        redirectUrl = removeParameterFromUrl(redirectUrl, SEARCH_QUERY_PARAMETER);
        redirectUrl += "&" + SEARCH_QUERY_PARAMETER + "=" + encodeUrl(queryParameter);

        Command.sendRedirect(redirectUrl, response);
    }
}
