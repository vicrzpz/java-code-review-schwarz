package schwarz.jobs.interview.coupon.exception;

public class IllegalDiscountException extends RuntimeException {

    public IllegalDiscountException() {
        super("Invalid Discount");
    }

    public IllegalDiscountException(String message) {
        super(message);
    }

    public IllegalDiscountException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalDiscountException(Throwable cause) {
        super(cause);
    }
}
