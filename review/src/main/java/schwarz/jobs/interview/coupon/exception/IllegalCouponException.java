package schwarz.jobs.interview.coupon.exception;


public class IllegalCouponException extends RuntimeException {


    public IllegalCouponException() {
        super("The coupon is invalid.");
    }

    public IllegalCouponException(String message) {
        super(message);
    }

    public IllegalCouponException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalCouponException(Throwable cause) {
        super(cause);
    }
}
