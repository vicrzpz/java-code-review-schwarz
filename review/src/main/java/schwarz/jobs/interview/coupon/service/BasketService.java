package schwarz.jobs.interview.coupon.service;

import org.springframework.stereotype.Service;
import schwarz.jobs.interview.coupon.dto.Basket;

import java.math.BigDecimal;

@Service
public class BasketService {

    public Basket applyDiscount(Basket basket, BigDecimal discount) {
        basket.setValue(basket.getValue().subtract(discount));
        basket.setAppliedDiscount(discount);
        basket.setApplicationSuccessful(true);
        return basket;
    }


}
