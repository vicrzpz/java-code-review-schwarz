package schwarz.jobs.interview.coupon.handler.imp;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import schwarz.jobs.interview.coupon.handler.ResponseHandler;

import java.util.HashMap;
import java.util.Map;

@Service
public class ResponseHandlerImp implements ResponseHandler {

    @Override
    public <T> ResponseEntity<T> handleSuccess(T data, String message) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("data", data);
        responseBody.put("message", message);
        return (ResponseEntity<T>) ResponseEntity.ok(responseBody);
    }

    @Override
    public ResponseEntity<Map<String, Object>> handleError(String errorMessage, HttpStatus status) {
        Map<String, Object> responseBody = new HashMap<>();
        responseBody.put("error", errorMessage);
        responseBody.put("status", status.value());
        return ResponseEntity.status(status).body(responseBody);
    }
}
