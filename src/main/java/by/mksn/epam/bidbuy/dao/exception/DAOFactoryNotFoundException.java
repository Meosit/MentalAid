package by.mksn.epam.bidbuy.dao.exception;

/**
 * Indicates that concrete DAO factory not found
 */
public class DAOFactoryNotFoundException extends RuntimeException {

    public DAOFactoryNotFoundException() {
        super();
    }

    public DAOFactoryNotFoundException(String cause) {
        super(cause);
    }

    public DAOFactoryNotFoundException(Throwable t) {
        super(t);
    }

    public DAOFactoryNotFoundException(String cause, Throwable t) {
        super(cause, t);
    }

}
