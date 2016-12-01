package by.mksn.epam.bidbuy.command;

import by.mksn.epam.bidbuy.command.exception.CommandException;

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
     * Executes command using {@link HttpServletRequest} specified
     *
     * @param request request from the user with necessary data to execute
     * @param response response to the user for future actions
     * @throws CommandException if command cannot be executed or execution failed
     * @throws ServletException if something with servlet occurs
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException, ServletException, IOException;

}
