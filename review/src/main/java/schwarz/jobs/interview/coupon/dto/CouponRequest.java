package schwarz.jobs.interview.coupon.dto;

import java.util.List;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class CouponRequest {

    @NotNull(message = "Codes list cannot be null")
    @Size(min = 1, message = "Codes list cannot be empty")
    private List<String> codes;

    public CouponRequest(@JsonProperty("codes") List<String> codes) {
        this.codes = codes;
    }

}