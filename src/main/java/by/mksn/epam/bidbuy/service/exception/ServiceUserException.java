package by.mksn.epam.bidbuy.service.exception;

/**
 * Thrown to indicate that something wrong with user logic
 */
public class ServiceUserException extends ServiceException {

    public ServiceUserException() {
        super();
    }

    public ServiceUserException(String cause) {
        super(cause);
    }

    public ServiceUserException(Throwable t) {
        super(t);
    }

    public ServiceUserException(String cause, Throwable t) {
        super(cause, t);
    }

}
