package schwarz.jobs.interview.coupon.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import schwarz.jobs.interview.coupon.exception.IllegalDiscountException;
import schwarz.jobs.interview.coupon.model.CouponEntity;
import schwarz.jobs.interview.coupon.repository.CouponRepository;
import schwarz.jobs.interview.coupon.dto.Basket;
import schwarz.jobs.interview.coupon.dto.Coupon;
import schwarz.jobs.interview.coupon.dto.CouponRequest;
import schwarz.jobs.interview.coupon.service.validator.imp.BasicCouponValidator;


@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final BasketService basketService;
    private final BasicCouponValidator basicCouponValidator;

    private Optional<CouponEntity> getCoupon(final String code) {
        return couponRepository.findByCode(code);
    }

    public Basket applyCoupon(final Basket basket, final String code) {
        CouponEntity couponEntity = getCoupon(code)
                .orElseThrow(() -> new IllegalDiscountException("Coupon not found"));

        if (basket.getValue().compareTo(couponEntity.getMinBasketValue()) < 0) {
            throw new IllegalDiscountException("Min basket value must be greater than: " + couponEntity.getMinBasketValue()
                    + " for coupon code: " + code);
        }
        return (basketService.applyDiscount(basket, couponEntity.getDiscount()));
    }


    public CouponEntity createCoupon(final Coupon couponDTO) {
        basicCouponValidator.validateCouponDTO(couponDTO);

        CouponEntity couponEntity = CouponEntity.builder()
                .code(couponDTO.getCode().toLowerCase())
                .discount(couponDTO.getDiscountValue())
                .minBasketValue(couponDTO.getMinBasketValue())
                .build();

        return couponRepository.save(couponEntity);
    }


    public List<CouponEntity> findCouponsByCodes(final CouponRequest couponRequest) {
        basicCouponValidator.validateCouponRequest(couponRequest);

        return couponRequest.getCodes().stream()
                .map(couponRepository::findByCode)
                .flatMap(Optional::stream)
                .collect(Collectors.toList());
    }



}
