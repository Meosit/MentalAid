package by.mksn.epam.mentalaid.command.impl;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.mksn.epam.mentalaid.command.resource.Constants.SEARCH_BASE_URL_ATTRIBUTE;
import static by.mksn.epam.mentalaid.command.resource.Constants.SEARCH_QUERY_PARAMETER;
import static by.mksn.epam.mentalaid.util.StringUtil.isNullOrEmpty;
import static by.mksn.epam.mentalaid.util.UrlUtil.addParameterToUrl;
import static by.mksn.epam.mentalaid.util.UrlUtil.encodeUrl;

public class SearchRedirectCommand implements Command {

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String baseUrlParameter = request.getParameter(SEARCH_BASE_URL_ATTRIBUTE);
        String queryParameter = request.getParameter(SEARCH_QUERY_PARAMETER);

        if (!isNullOrEmpty(queryParameter)) {
            baseUrlParameter = addParameterToUrl(baseUrlParameter, SEARCH_QUERY_PARAMETER, encodeUrl(queryParameter));
        }

        Command.sendRedirect(baseUrlParameter, response);
    }
}
