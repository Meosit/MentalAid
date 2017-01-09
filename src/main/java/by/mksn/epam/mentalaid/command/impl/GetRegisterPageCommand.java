package by.mksn.epam.mentalaid.command.impl;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.command.resource.PathManager;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.mksn.epam.mentalaid.command.resource.Constants.FROM_URL_ATTRIBUTE;
import static by.mksn.epam.mentalaid.command.resource.Constants.FROM_URL_PARAMETER;

/**
 * Returns to client page with registration form
 */
public class GetRegisterPageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String pagePath = PathManager.getProperty(PathManager.REGISTER);
        request.setAttribute(FROM_URL_ATTRIBUTE, request.getParameter(FROM_URL_PARAMETER));
        Command.dispatchRequest(pagePath, false, request, response);
    }

    @Override
    public String generateFromUrl(String urlWithoutParameters, String[] fromParameterArgs) {
        String url = urlWithoutParameters;
        if (fromParameterArgs.length > 1) {
            url += '&' + FROM_URL_PARAMETER + '=';
            url += fromParameterArgs[1];
            for (int i = 2; i < fromParameterArgs.length; i++) {
                url += '+' + fromParameterArgs[i];
            }
        }
        return url;
    }
}
