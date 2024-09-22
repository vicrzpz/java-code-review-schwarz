package schwarz.jobs.interview.coupon.model;

import java.math.BigDecimal;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Code cannot be null")
    @Column(name = "code", unique = true)
    private String code;

    @NotNull(message = "Discount cannot be null")
    @Column(name = "discount", precision = 10, scale = 2)
    private BigDecimal discount;

    @NotNull(message = "Minimum basket value cannot be null")
    @Column(name = "min_basket_value", precision = 10, scale = 2)
    private BigDecimal minBasketValue;

}
