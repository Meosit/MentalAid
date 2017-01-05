package by.mksn.epam.mentalaid.service.exception;

public class AnswerServiceException extends ServiceException {

    /**
     * Cause of exception when invalid properties values passed
     */
    public static final int WRONG_INPUT = 0x11;

    private int causeCode;

    public AnswerServiceException(int causeCode) {
        super();
        this.causeCode = causeCode;
    }

    public AnswerServiceException(String cause, int causeCode) {
        super(cause);
        this.causeCode = causeCode;
    }

    public AnswerServiceException(Throwable t, int causeCode) {
        super(t);
        this.causeCode = causeCode;
    }

    public AnswerServiceException(String cause, Throwable t, int causeCode) {
        super(cause, t);
        this.causeCode = causeCode;
    }

    public int getCauseCode() {
        return causeCode;
    }
}
