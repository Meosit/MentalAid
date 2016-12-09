package by.mksn.epam.bidbuy.dao.exception;

/**
 * Exception which represents not normal work with database and data
 */
public class DAOException extends Exception {

    public DAOException() {
        super();
    }

    public DAOException(String cause) {
        super(cause);
    }

    public DAOException(Throwable t) {
        super(t);
    }

    public DAOException(String cause, Throwable t) {
        super(cause, t);
    }

}
