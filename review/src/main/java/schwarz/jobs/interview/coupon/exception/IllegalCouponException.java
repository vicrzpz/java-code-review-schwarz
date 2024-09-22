package schwarz.jobs.interview.coupon.exception;

/**
 * Exception thrown when an illegal coupon is encountered.
 * This is an unchecked exception, which means it does not need to be explicitly handled or declared in method signatures.
 */
public class IllegalCouponException extends RuntimeException {

    /**
     * Default constructor that initializes the exception with a generic message.
     */
    public IllegalCouponException() {
        super("The coupon is illegal.");
    }

    /**
     * Constructor that accepts a custom message.
     *
     * @param message the detail message explaining the reason for the exception
     */
    public IllegalCouponException(String message) {
        super(message);
    }

    /**
     * Constructor that accepts a custom message and a cause.
     *
     * @param message the detail message explaining the reason for the exception
     * @param cause the cause of the exception (the throwable that caused this exception to be thrown)
     */
    public IllegalCouponException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructor that accepts a cause.
     *
     * @param cause the cause of the exception (the throwable that caused this exception to be thrown)
     */
    public IllegalCouponException(Throwable cause) {
        super(cause);
    }
}
