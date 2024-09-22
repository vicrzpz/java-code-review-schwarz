package schwarz.jobs.interview.coupon.exception;

/**
 * Exception thrown when an invalid discount is applied.
 * This is an unchecked exception, which means it does not need to be explicitly handled or declared in method signatures.
 */
public class IllegalDiscountException extends RuntimeException {

    /**
     * Constructs a new IllegalDiscountException with a default error message: "Invalid Discount".
     */
    public IllegalDiscountException() {
        super("Invalid Discount");
    }

    /**
     * Constructs a new IllegalDiscountException with the specified error message.
     *
     * @param message The detail message that explains the reason for the exception.
     */
    public IllegalDiscountException(String message) {
        super(message);
    }

    /**
     * Constructs a new IllegalDiscountException with the specified error message and cause.
     *
     * @param message The detail message that explains the reason for the exception.
     * @param cause The cause of the exception (a throwable that caused this exception to be thrown).
     */
    public IllegalDiscountException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * Constructs a new IllegalDiscountException with the specified cause.
     *
     * @param cause The cause of the exception (a throwable that caused this exception to be thrown).
     */
    public IllegalDiscountException(Throwable cause) {
        super(cause);
    }
}
