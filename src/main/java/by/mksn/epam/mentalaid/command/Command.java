package by.mksn.epam.mentalaid.command;

import by.mksn.epam.mentalaid.command.exception.CommandException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
     */
    void execute(HttpServletRequest request, HttpServletResponse response) throws CommandException;

}
