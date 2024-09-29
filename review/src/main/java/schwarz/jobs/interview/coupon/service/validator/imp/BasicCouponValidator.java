package schwarz.jobs.interview.coupon.service.validator.imp;

import org.springframework.stereotype.Component;
import schwarz.jobs.interview.coupon.dto.Coupon;
import schwarz.jobs.interview.coupon.dto.CouponRequest;
import schwarz.jobs.interview.coupon.exception.IllegalCouponException;
import schwarz.jobs.interview.coupon.service.validator.CouponValidator;

@Component
public class BasicCouponValidator implements CouponValidator {
    @Override
    public void validateCouponDTO(Coupon couponDTO) {
        if (couponDTO.getCode() == null || couponDTO.getCode().trim().isEmpty()) {
            throw new IllegalCouponException("Coupon code is null or empty.");
        }
    }
    @Override
    public void validateCouponRequest(CouponRequest couponRequest) {
        if (couponRequest == null || couponRequest.getCodes().stream().allMatch(code -> code.trim().isEmpty())) {
            throw new IllegalCouponException("List of codes is null or empty.");
        }
    }
}
