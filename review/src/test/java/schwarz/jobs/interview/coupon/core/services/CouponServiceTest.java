package schwarz.jobs.interview.coupon.core.services;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import schwarz.jobs.interview.coupon.exception.IllegalDiscountException;
import schwarz.jobs.interview.coupon.model.CouponEntity;
import schwarz.jobs.interview.coupon.repository.CouponRepository;
import schwarz.jobs.interview.coupon.dto.Basket;
import schwarz.jobs.interview.coupon.dto.Coupon;
import schwarz.jobs.interview.coupon.dto.CouponRequest;
import schwarz.jobs.interview.coupon.service.BasketService;
import schwarz.jobs.interview.coupon.service.CouponService;


/**
 * Unit tests for the CouponService class.
 * This class contains tests to verify the behavior of the CouponService,
 * including coupon creation, applying coupons to baskets, and retrieving
 * multiple coupons.
 */
@ExtendWith(SpringExtension.class)
@ExtendWith(SpringExtension.class)
public class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;

    @Mock
    private BasketService basketService;

    /**
     * Test case for creating a coupon.
     * <p>
     * This test verifies that a coupon can be created and saved in the
     * repository.
     * </p>
     */
    @Test
    public void test_create_coupon() {
        Coupon coupon = Coupon.builder()
            .code("1111")
            .discountValue(BigDecimal.TEN)
            .minBasketValue(BigDecimal.valueOf(50))
            .build();

        couponService.createCoupon(coupon);

        verify(couponRepository, times(1)).save(any());
    }

    /**
     * Test case for applying a coupon to a basket.
     * <p>
     * This test verifies that a coupon can be applied to a basket if the
     * basket's value meets the minimum requirements, and checks if the
     * discount is applied correctly.
     * </p>
     *
     * @throws IllegalDiscountException if the basket's value is below the
     *                                  minimum required for the coupon.
     */
    @Test
    public void test_apply_coupon() {
        final Basket firstBasket = Basket.builder()
                .value(BigDecimal.valueOf(100))
                .build();

        when(couponRepository.findByCode("1111")).thenReturn(Optional.of(CouponEntity.builder()
                .code("1111")
                .discount(BigDecimal.TEN)
                .minBasketValue(BigDecimal.valueOf(50))
                .build()));

        when(basketService.applyDiscount(firstBasket, BigDecimal.TEN)).thenReturn(Optional.of(Basket.builder()
                .value(BigDecimal.valueOf(90))
                .appliedDiscount(BigDecimal.TEN)
                .applicationSuccessful(true)
                .build()));

        Optional<Basket> optionalBasket = couponService.applyCoupon(firstBasket, "1111");

        assertThat(optionalBasket).isNotEmpty();
        assertThat(optionalBasket).hasValueSatisfying(b -> {
            assertThat(b.getAppliedDiscount()).isEqualTo(BigDecimal.TEN);
            assertThat(b.isApplicationSuccessful()).isTrue();
        });

        final Basket secondBasket = Basket.builder()
                .value(BigDecimal.valueOf(500))
                .appliedDiscount(BigDecimal.TEN)
                .applicationSuccessful(true)
                .build();

        when(basketService.applyDiscount(secondBasket, BigDecimal.TEN)).thenReturn(Optional.of(Basket.builder()
                .value(BigDecimal.valueOf(500))
                .appliedDiscount(BigDecimal.TEN)
                .applicationSuccessful(true)
                .build()));

        optionalBasket = couponService.applyCoupon(secondBasket, "1111");

        assertThat(optionalBasket).hasValueSatisfying(b -> {
            assertThat(b).isEqualTo(secondBasket);
            assertThat(b.isApplicationSuccessful()).isTrue();

        });

        final Basket thirdBasket = Basket.builder()
                .value(BigDecimal.valueOf(-1))
                .build();

        assertThatThrownBy(() -> {
            couponService.applyCoupon(thirdBasket, "1111");
        }).isInstanceOf(IllegalDiscountException.class)
          .hasMessage("Min basket value must be greater than: " +50);
    }

    /**
     * Test case for retrieving multiple coupons.
     * <p>
     * This test verifies that multiple coupons can be retrieved from the
     * repository based on their codes.
     * </p>
     */
    @Test
    public void test_get_Coupons() {

        CouponRequest dto = CouponRequest.builder()
            .codes(Arrays.asList("1111", "1234"))
            .build();

        when(couponRepository.findByCode(any()))
            .thenReturn(Optional.of(CouponEntity.builder()
                .code("1111")
                .discount(BigDecimal.TEN)
                .minBasketValue(BigDecimal.valueOf(50))
                .build()))
            .thenReturn(Optional.of(CouponEntity.builder()
                .code("1234")
                .discount(BigDecimal.TEN)
                .minBasketValue(BigDecimal.valueOf(50))
                .build()));

        List<CouponEntity> returnedCouponEntities = couponService.getCoupons(dto);

        assertThat(returnedCouponEntities.get(0).getCode()).isEqualTo("1111");

        assertThat(returnedCouponEntities.get(1).getCode()).isEqualTo("1234");
    }
}
