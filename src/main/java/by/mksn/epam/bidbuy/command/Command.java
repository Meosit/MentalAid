package by.mksn.epam.bidbuy.command;

import by.mksn.epam.bidbuy.command.exception.CommandException;
import by.mksn.epam.bidbuy.manager.PathManager;

import javax.servlet.http.HttpServletRequest;

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
     * @return path of the jsp page for dispatch, see also {@link PathManager}
     * @throws CommandException if command cannot be executed or execution failed
     */
    String execute(HttpServletRequest request) throws CommandException;

}
