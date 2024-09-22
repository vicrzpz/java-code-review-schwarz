package schwarz.jobs.interview.coupon.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) for representing a coupon in the system.
 * Contains information about the coupon's discount value, code, and minimum basket value required for the coupon to be applicable.
 */
@Data
@Builder
public class Coupon {
    /**
     * The discount value applied by the coupon.
     * It must be greater than 0.
     */
    @NotNull
    @DecimalMin(value = "0.00", inclusive = false, message = "Discount must be greater than 0")
    private BigDecimal discountValue;

    /**
     * The coupon code.
     * This field is mandatory and must not be blank.
     */
    @NotBlank(message = "Code is mandatory")
    private String code;

    /**
     * The minimum basket value for which the coupon can be applied.
     * It must be at least 0.
     */
    @NotNull
    @DecimalMin(value = "0.00", inclusive = true, message = "Minimum basket value must be at least 0")
    private BigDecimal minBasketValue;

}
