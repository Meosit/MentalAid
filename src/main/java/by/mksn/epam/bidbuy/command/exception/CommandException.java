package by.mksn.epam.bidbuy.command.exception;

import by.mksn.epam.bidbuy.command.Command;

/**
 * Thrown to indicate that a {@link Command} cannot be executed
 * or something went wrong while executing
 */
public class CommandException extends Exception {

    public CommandException() {
        super();
    }

    public CommandException(String cause) {
        super(cause);
    }

    public CommandException(Throwable t) {
        super(t);
    }

    public CommandException(String cause, Throwable t) {
        super(cause, t);
    }

}
