package by.mksn.epam.mentalaid.service.exception;

/**
 * Thrown to indicate that something wrong with DAO layer
 */
public class ServiceDaoException extends ServiceException {

    public ServiceDaoException() {
        super();
    }

    public ServiceDaoException(String cause) {
        super(cause);
    }

    public ServiceDaoException(Throwable t) {
        super(t);
    }

    public ServiceDaoException(String cause, Throwable t) {
        super(cause, t);
    }

}
