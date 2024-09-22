package schwarz.jobs.interview.coupon.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * DTO for handling coupon request operations that includes a list of coupon codes.
 */
@Data
@Builder
public class CouponRequest {
    /**
     * List of coupon codes to be processed.
     * This field must not be null or empty.
     */
    @NotNull(message = "Codes list cannot be null")
    @Size(min = 1, message = "Codes list cannot be empty")
    private List<String> codes;
    /**
     * Constructor to create a CouponRequest.
     *
     * @param codes the list of coupon codes
     */
    @JsonCreator
    public CouponRequest(@JsonProperty("codes") List<String> codes) {
        this.codes = codes;
    }

}