package schwarz.jobs.interview.coupon.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public interface ResponseHandler {
    <T> ResponseEntity<T> handleSuccess(T data, String message);

    ResponseEntity<?> handleError(String errorMessage, HttpStatus status);
}

