package schwarz.jobs.interview.coupon.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import schwarz.jobs.interview.coupon.exception.IllegalCouponException;
import schwarz.jobs.interview.coupon.exception.IllegalDiscountException;
import schwarz.jobs.interview.coupon.model.CouponEntity;
import schwarz.jobs.interview.coupon.repository.CouponRepository;
import schwarz.jobs.interview.coupon.dto.Basket;
import schwarz.jobs.interview.coupon.dto.Coupon;
import schwarz.jobs.interview.coupon.dto.CouponRequest;

/**
 * Service class for managing coupons and applying discounts.
 */
@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final BasketService basketService;

    /**
     * Retrieves a coupon by its code.
     *
     * @param code the code of the coupon
     * @return an Optional containing the CouponEntity if found, otherwise empty
     */
    public Optional<CouponEntity> getCoupon(final String code) {
        return couponRepository.findByCode(code);
    }

    /**
     * Applies a coupon to a given basket and returns the updated basket.
     *
     * @param basket the basket to which the coupon will be applied
     * @param code the code of the coupon to apply
     * @return an Optional containing the updated Basket if successful, otherwise empty
     * @throws IllegalDiscountException if the coupon is not found or the basket value is less than the minimum required
     */
    public Optional<Basket> applyCoupon(final Basket basket, final String code) {
        CouponEntity couponEntity = getCoupon(code)
                .orElseThrow(() -> new IllegalDiscountException("Coupon not found"));

        if (basket.getValue().compareTo(couponEntity.getMinBasketValue()) < 0) {
            throw new IllegalDiscountException("Min basket value must be greater than: " + couponEntity.getMinBasketValue());
        }


        return (basketService.applyDiscount(basket, couponEntity.getDiscount()));
    }

    /**
     * Creates a new coupon and saves it to the repository.
     *
     * @param couponDTO the coupon data transfer object containing coupon details
     * @return the created CouponEntity
     * @throws IllegalCouponException if the coupon code is null or empty
     */
    public CouponEntity createCoupon(final Coupon couponDTO) {
        validateCouponDTO(couponDTO);

        CouponEntity couponEntity = CouponEntity.builder()
                .code(couponDTO.getCode().toLowerCase())
                .discount(couponDTO.getDiscountValue())
                .minBasketValue(couponDTO.getMinBasketValue())
                .build();

        return couponRepository.save(couponEntity);
    }

    /**
     * Retrieves a list of coupons based on the provided coupon request.
     *
     * @param couponRequest the request containing a list of coupon codes
     * @return a list of CouponEntity that match the provided codes
     * @throws IllegalCouponException if the coupon request or its codes are null or empty
     */
    public List<CouponEntity> getCoupons(final CouponRequest couponRequest) {
        validateCouponRequest(couponRequest);

        return couponRequest.getCodes().stream()
                .map(couponRepository::findByCode)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }

    /**
     * Validates the provided coupon data transfer object.
     *
     * @param couponDTO the coupon to validate
     * @throws IllegalCouponException if the coupon is null or the code is null or empty
     */
    private void validateCouponDTO(Coupon couponDTO) {
        if (couponDTO == null || couponDTO.getCode() == null || couponDTO.getCode().isEmpty()) {
            throw new IllegalCouponException("Coupon code cannot be null or empty");
        }
    }

    /**
     * Validates the provided coupon request.
     *
     * @param couponRequest the coupon request to validate
     * @throws IllegalCouponException if the coupon request is null or its codes are null or empty
     */
    private void validateCouponRequest(CouponRequest couponRequest) {
        if (couponRequest == null || couponRequest.getCodes() == null || couponRequest.getCodes().isEmpty()) {
            throw new IllegalCouponException("CouponRequestDTO or its codes cannot be null or empty");
        }
    }
}
