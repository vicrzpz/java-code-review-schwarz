package schwarz.jobs.interview.coupon.dto;

import java.math.BigDecimal;

import lombok.Builder;
import lombok.Data;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class Coupon {

    @NotNull
    @NotBlank(message = "Code is mandatory")
    private String code;

    @NotNull
    @DecimalMin(value = "0.00", inclusive = false, message = "Discount must be greater than 0")
    private BigDecimal discountValue;


    @NotNull
    @DecimalMin(value = "0.00", message = "Minimum basket value must be at least 0")
    private BigDecimal minBasketValue;

}
