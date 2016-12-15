package by.mksn.epam.bidbuy.service.exception;

/**
 * Thrown to indicate that something wrong with user logic
 */
public class ServiceUserException extends ServiceException {

    /**
     * Cause of exception when incorrect password was passed and login failed
     */
    public static final int INCORRECT_PASSWORD = 0x11;
    /**
     * Cause of exception when user with the specified username doesn't exists and login failed
     */
    public static final int USER_NOT_EXIST = 0x12;
    /**
     * Cause of exception when user with the specified username is already exists and registration failed
     */
    public static final int USER_EXISTS = 0x21;
    /**
     * Cause of exception when user with the specified email is already exists and registration failed
     */
    public static final int EMAIL_EXISTS = 0x22;

    private int causeCode;

    public ServiceUserException(int causeCode) {
        super();
        this.causeCode = causeCode;
    }

    public ServiceUserException(String cause, int causeCode) {
        super(cause);
        this.causeCode = causeCode;
    }

    public ServiceUserException(Throwable t, int causeCode) {
        super(t);
        this.causeCode = causeCode;
    }

    public ServiceUserException(String cause, Throwable t, int causeCode) {
        super(cause, t);
        this.causeCode = causeCode;
    }

    /**
     * Represents code of exception cause
     *
     * @return code of exception cause, one of the following:<br>
     * - {@link #INCORRECT_PASSWORD} <br>
     * - {@link #USER_NOT_EXIST} <br>
     * - {@link #USER_EXISTS} <br>
     * - {@link #EMAIL_EXISTS} <br>
     */
    public int getCauseCode() {
        return causeCode;
    }

}
