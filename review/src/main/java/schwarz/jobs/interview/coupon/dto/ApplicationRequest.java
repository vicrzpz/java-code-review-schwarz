package schwarz.jobs.interview.coupon.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

/**
 * DTO for discount application requests.
 */
@Data
@Builder
public class ApplicationRequest {
    /**
     * The code of the coupon to be applied.
     * This field is mandatory and cannot be blank.
     */
    @NotBlank(message = "Coupon code must not be blank")
    private String code;

    /**
     * The basket to which the coupon should be applied.
     * This field is mandatory and must not be null.
     */
    @NotNull(message = "Basket must not be null")
    private Basket basket;

}
