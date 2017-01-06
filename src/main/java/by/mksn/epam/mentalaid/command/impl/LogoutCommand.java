package by.mksn.epam.mentalaid.command.impl;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.command.resource.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static by.mksn.epam.mentalaid.command.resource.Constants.USER_ATTRIBUTE;

/**
 * Logs out user from the site
 */
public class LogoutCommand implements Command {

    private static final Logger logger = Logger.getLogger(LogoutCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        request.getSession().removeAttribute(USER_ATTRIBUTE);
        logger.debug("User logged out");
        String pagePath = PathManager.getProperty(PathManager.INDEX);
        Command.dispatchRequest(pagePath, false, request, response);
    }
}
