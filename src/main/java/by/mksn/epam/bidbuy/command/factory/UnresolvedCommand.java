package by.mksn.epam.bidbuy.command.factory;

import by.mksn.epam.bidbuy.command.Command;
import by.mksn.epam.bidbuy.command.exception.CommandException;
import by.mksn.epam.bidbuy.command.impl.manager.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UnresolvedCommand implements Command {

    private static final Logger logger = Logger.getLogger(UnresolvedCommand.class);

    private static final String ERROR_CODE_ATTR = "errorCode";
    private static final String ERROR_MESSAGE_ATTR = "errorMessage";
    private static final String ERROR_CODE_KEY = "unresolved.code";
    private static final String ERROR_MESSAGE_KEY = "unresolved.message";

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        logger.debug("Unresolved command has been executed.");

        request.setAttribute(ERROR_CODE_ATTR, ERROR_CODE_KEY);
        request.setAttribute(ERROR_MESSAGE_ATTR, ERROR_MESSAGE_KEY);
        String pagePath = PathManager.getProperty(PathManager.ERROR);

        logger.trace("Forward to " + pagePath);
        try {
            request.getRequestDispatcher(pagePath).forward(request, response);
        } catch (ServletException e) {
            throw new CommandException("Servlet exception occurs. ", e);
        } catch (IOException e) {
            throw new CommandException("IOException exception occurs. ", e);
        }
    }
}
