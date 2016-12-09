package by.mksn.epam.bidbuy.command.impl.content;

import by.mksn.epam.bidbuy.command.Command;
import by.mksn.epam.bidbuy.command.exception.CommandException;
import by.mksn.epam.bidbuy.command.impl.manager.PathManager;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Returns to client page with registration form
 */
public class GetRegisterPageCommand implements Command {
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException {
        String pagePath = PathManager.getProperty(PathManager.REGISTER);
        try {
            request.getRequestDispatcher(pagePath).forward(request, response);
        } catch (ServletException e) {
            throw new CommandException("Servlet exception occurs. ", e);
        } catch (IOException e) {
            throw new CommandException("IOException exception occurs. ", e);
        }
    }
}
