package schwarz.jobs.interview.coupon.dto;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class Basket {

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false, message = "The basket value must be greater than zero")
    private BigDecimal value;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private BigDecimal appliedDiscount;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private boolean applicationSuccessful;

}
