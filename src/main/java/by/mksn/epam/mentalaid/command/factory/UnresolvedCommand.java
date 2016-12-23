package by.mksn.epam.mentalaid.command.factory;

import by.mksn.epam.mentalaid.command.Command;
import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.command.resource.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static by.mksn.epam.mentalaid.command.resource.Constants.*;

/**
 * This command called if {@link CommandFactory} cannot resolve command name (There is not match in {@link CommandEnum})
 */
public class UnresolvedCommand implements Command {

    private static final Logger logger = Logger.getLogger(UnresolvedCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        logger.debug("Unresolved command has been executed.");
        request.setAttribute(ERROR_TITLE_ATTRIBUTE, ERROR_TITLE_UNRESOLVED);
        request.setAttribute(ERROR_MESSAGE_ATTRIBUTE, ERROR_MESSAGE_UNRESOLVED);
        String pagePath = PathManager.getProperty(PathManager.ERROR);
        try {
            request.getRequestDispatcher(pagePath).forward(request, response);
        } catch (ServletException e) {
            throw new CommandException("Servlet exception occurs. ", e);
        } catch (IOException e) {
            throw new CommandException("IOException exception occurs. ", e);
        }
    }
}
