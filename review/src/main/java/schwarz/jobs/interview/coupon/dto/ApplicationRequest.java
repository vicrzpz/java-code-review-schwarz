package schwarz.jobs.interview.coupon.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ApplicationRequest {

    @NotBlank(message = "Coupon code must not be blank")
    private String code;

    @NotNull(message = "Basket must not be null")
    private Basket basket;

}
