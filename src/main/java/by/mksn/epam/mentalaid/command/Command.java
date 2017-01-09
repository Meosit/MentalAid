package by.mksn.epam.mentalaid.command;

import by.mksn.epam.mentalaid.command.exception.CommandException;
import by.mksn.epam.mentalaid.command.factory.CommandFactory;

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

    /**
     * Default dispatch for request
     *
     * @param pagePath path of page to forward
     * @param isAsync  if {@code true}, exception handling will be for async requests
     * @param request  request to dispatch
     * @param response response to dispatch
     * @throws CommandException if cannot dispatch request for specified {@code pagePath}
     */
    static void dispatchRequest(String pagePath, boolean isAsync, HttpServletRequest request,
                                HttpServletResponse response) throws CommandException {
        try {
            request.getRequestDispatcher(pagePath).forward(request, response);
        } catch (ServletException e) {
            throw new CommandException("Servlet exception occurs. ", e, isAsync);
        } catch (IOException e) {
            throw new CommandException("IOException exception occurs. ", e, isAsync);
        }
    }

    /**
     * Default redirect for the specified url
     *
     * @param url      url to redirect to
     * @param response response for redirecting
     * @throws CommandException if internal error occurs
     */
    static void sendRedirect(String url, HttpServletResponse response) throws CommandException {
        try {
            url = response.encodeRedirectURL(url);
            response.sendRedirect(url);
        } catch (IOException e) {
            throw new CommandException("IOException exception occurs. ", e, false);
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

    /**
     * This method generates command url with parameters to provide ability to back-redirecting mechanism.
     * If command doesn't have any additional parameters, there is no need to implement this method.
     *
     * @param urlWithoutParameters url of the concrete command in format {@code /Context/Servlet?command_parameter=command_name}
     * @param fromParameterArgs    array of parameters, which always contains {@code command_name} as first element,
     *                             the following elements is just values of parameters for the concrete command
     * @return string with Url which represents current command with specified parameters (if that is necessary)
     * @see CommandFactory#defineFromUrl(HttpServletRequest)
     */
    default String generateFromUrl(String urlWithoutParameters, String[] fromParameterArgs) {
        return urlWithoutParameters;
    }
}
