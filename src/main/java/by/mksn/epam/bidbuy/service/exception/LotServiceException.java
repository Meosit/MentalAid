package by.mksn.epam.bidbuy.service.exception;

public class LotServiceException extends ServiceException {

    /**
     * Cause of exception when incorrect status was passed.
     */
    public static final int INVALID_STATUS = 0x01;
    /**
     * Cause of exception when auction type status was passed.
     */
    public static final int INVALID_TYPE = 0x02;
    /**
     * Cause of exception when incorrect name was passed, {@code null} or empty, or more than max length.
     */
    public static final int INVALID_NAME = 0x03;
    /**
     * Cause of exception when incorrect description was passed, {@code null} or empty, or more than max length.
     */
    public static final int INVALID_DESCRIPTION = 0x04;
    /**
     * Cause of exception when incorrect duration time was passed, less than 5 minutes or mode than 30 days.
     */
    public static final int INVALID_DURATION_TIME = 0x05;
    /**
     * Cause of exception when incorrect price was passed, {@code null} less than zero.
     */
    public static final int INVALID_PRICE = 0x06;
    /**
     * Cause of exception when incorrect min and max were passed, min price cant be bigger than max.
     */
    public static final int MIN_PRICE_BIGGER = 0x07;
    /**
     * Cause of exception when incorrect bid step was passed, it can be only lower than difference between max and min price.
     */
    public static final int BID_STEP_OUT_OF_RANGE = 0x08;

    private int causeCode;

    public LotServiceException(int causeCode) {
        super();
        this.causeCode = causeCode;
    }

    public LotServiceException(String cause, int causeCode) {
        super(cause);
        this.causeCode = causeCode;
    }

    public LotServiceException(Throwable t, int causeCode) {
        super(t);
        this.causeCode = causeCode;
    }

    public LotServiceException(String cause, Throwable t, int causeCode) {
        super(cause, t);
        this.causeCode = causeCode;
    }

    /**
     * Represents code of exception cause
     *
     * @return code of exception cause, one of the following:<br>
     * - {@link #INVALID_STATUS} <br>
     * - {@link #INVALID_TYPE} <br>
     * - {@link #INVALID_NAME} <br>
     * - {@link #INVALID_DESCRIPTION} <br>
     * - {@link #INVALID_DURATION_TIME} <br>
     * - {@link #INVALID_PRICE} <br>
     * - {@link #MIN_PRICE_BIGGER} <br>
     * - {@link #BID_STEP_OUT_OF_RANGE} <br>
     */
    public int getCauseCode() {
        return causeCode;
    }


}
