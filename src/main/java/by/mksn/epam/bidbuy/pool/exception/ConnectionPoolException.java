package by.mksn.epam.bidbuy.pool.exception;

public class ConnectionPoolException extends Exception {

    public ConnectionPoolException() {
        super();
    }

    public ConnectionPoolException(String cause) {
        super(cause);
    }

    public ConnectionPoolException(Throwable t) {
        super(t);
    }

    public ConnectionPoolException(String cause, Throwable t) {
        super(cause, t);
    }

}
