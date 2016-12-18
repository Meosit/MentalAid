package by.mksn.epam.bidbuy.command.exception;

import by.mksn.epam.bidbuy.command.Command;

/**
 * Thrown to indicate that a {@link Command} cannot be executed
 * or something went wrong while executing
 */
public class CommandException extends Exception {

    private boolean isAsync;

    public CommandException() {
        super();
        isAsync = false;
    }

    public CommandException(String cause) {
        super(cause);
        isAsync = false;
    }

    public CommandException(Throwable t) {
        super(t);
        isAsync = false;
    }

    public CommandException(String cause, Throwable t) {
        super(cause, t);
        isAsync = false;
    }

    public CommandException(boolean isAsync) {
        super();
        this.isAsync = isAsync;
    }

    public CommandException(String cause, boolean isAsync) {
        super(cause);
        this.isAsync = isAsync;
    }

    public CommandException(Throwable t, boolean isAsync) {
        super(t);
        this.isAsync = isAsync;
    }

    public CommandException(String cause, Throwable t, boolean isAsync) {
        super(cause, t);
        this.isAsync = isAsync;
    }

    /**
     * Indicates that exception occurs while executing async command.
     *
     * @return {@code true} if occurs while executing async command
     */
    public boolean isAsync() {
        return isAsync;
    }

}
