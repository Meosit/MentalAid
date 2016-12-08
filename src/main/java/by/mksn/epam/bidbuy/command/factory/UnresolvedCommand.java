package by.mksn.epam.bidbuy.command.factory;

import by.mksn.epam.bidbuy.command.Command;
import by.mksn.epam.bidbuy.command.exception.CommandException;
import by.mksn.epam.bidbuy.command.manager.PathManager;
import org.apache.log4j.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class UnresolvedCommand implements Command {

    private static final Logger logger = Logger.getLogger(UnresolvedCommand.class);

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        logger.debug("Unresolved command has been executed.");
        //TODO: page with message that command is unresolved
        String pagePath = PathManager.getProperty(PathManager.INDEX);
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
