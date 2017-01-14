package by.mksn.epam.mentalaid.service.exception;

public class MarkServiceException extends ServiceException {

    /**
     * Cause of exception when invalid properties values passed
     */
    public static final int WRONG_INPUT = 0x11;

    private int causeCode;

    public MarkServiceException(int causeCode) {
        super();
        this.causeCode = causeCode;
    }

    public MarkServiceException(String cause, int causeCode) {
        super(cause);
        this.causeCode = causeCode;
    }

    public MarkServiceException(Throwable t, int causeCode) {
        super(t);
        this.causeCode = causeCode;
    }

    public MarkServiceException(String cause, Throwable t, int causeCode) {
        super(cause, t);
        this.causeCode = causeCode;
    }

    /**
     * Represents code of exception cause
     *
     * @return code of exception cause, one of the following:<br>
     * - {@link #WRONG_INPUT}
     */
    public int getCauseCode() {
        return causeCode;
    }
}
