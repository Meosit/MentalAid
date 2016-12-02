package by.mksn.epam.bidbuy.entity.exception;

/**
 * Thrown to indicate that a method has been passed an illegal or inappropriate argument.
 */
public class EntityArgumentException extends RuntimeException {


    public EntityArgumentException() {
        super();
    }

    public EntityArgumentException(String cause) {
        super(cause);
    }

    public EntityArgumentException(Throwable t) {
        super(t);
    }

    public EntityArgumentException(String cause, Throwable t) {
        super(cause, t);
    }

}
