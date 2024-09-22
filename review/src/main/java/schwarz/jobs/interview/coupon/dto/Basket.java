package schwarz.jobs.interview.coupon.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * Data Transfer Object (DTO) for representing a basket in coupon application.
 * This class holds the original value of the basket, the applied discount, and the application result.
 */
@Data
@Builder
public class Basket {
    /**
     * The original value of the basket.
     * This field is mandatory and must not be null.
     */
    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "The basket value must be greater than zero")
    private BigDecimal value;
    /**
     * The applied discount on the basket.
     * This field is read-only.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal appliedDiscount;
    /**
     * Indicates whether the coupon application was successful or not.
     * This field is read-only.
     */
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean applicationSuccessful;

}
