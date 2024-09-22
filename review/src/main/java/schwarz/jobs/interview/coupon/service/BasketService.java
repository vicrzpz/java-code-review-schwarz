package schwarz.jobs.interview.coupon.service;

import org.springframework.stereotype.Service;
import schwarz.jobs.interview.coupon.dto.Basket;

import java.math.BigDecimal;
import java.util.Optional;

@Service
public class BasketService {
    /**
     * Applies a discount to the given basket.
     *
     * @param basket  the basket to which the discount will be applied
     * @param discount the discount to apply
     */
    /*public Basket applyDiscount(Basket basket, BigDecimal discount) {
        return Basket.builder()
                .value(basket.getValue().subtract(discount))
                .appliedDiscount(discount)
                .applicationSuccessful(true)
                .build();
    }*/

    public Optional<Basket> applyDiscount(Basket basket, BigDecimal discount) {
        basket.setValue(basket.getValue().subtract(discount));
        basket.setAppliedDiscount(discount);
        basket.setApplicationSuccessful(true);
        return Optional.of(basket);
    }


}
