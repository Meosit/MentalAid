package by.mksn.epam.bidbuy.dao.exception;

public class DaoException extends Exception {

    public DaoException() {
        super();
    }

    public DaoException(String cause) {
        super(cause);
    }

    public DaoException(Throwable t) {
        super(t);
    }

    public DaoException(String cause, Throwable t) {
        super(cause, t);
    }

}
