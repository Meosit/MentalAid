package by.mksn.epam.mentalaid.command;

import by.mksn.epam.mentalaid.command.exception.CommandException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Main interface of Command pattern which used to delegate
 * every web app action from controller to separated amount of classes,
 * flexible and extendable
 */
public interface Command {

    static void dispatchRequest(String pagePath, boolean isAsync, HttpServletRequest request, HttpServletResponse response) throws CommandException {
        try {
            request.getRequestDispatcher(pagePath).forward(request, response);
        } catch (ServletException e) {
            throw new CommandException("Servlet exception occurs. ", e, isAsync);
        } catch (IOException e) {
            throw new CommandException("IOException exception occurs. ", e, isAsync);
        }
    }

    /**
     * Executes command using {@link HttpServletRequest} specified
     *
     * @param request request from the user with necessary data to execute
     * @param response response to the user for future actions
     * @throws CommandException if command cannot be executed or execution failed
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;
}
