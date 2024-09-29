package schwarz.jobs.interview.coupon.service.validator.imp;

import schwarz.jobs.interview.coupon.dto.Coupon;
import schwarz.jobs.interview.coupon.dto.CouponRequest;
import schwarz.jobs.interview.coupon.exception.IllegalCouponException;
import schwarz.jobs.interview.coupon.service.validator.CouponValidator;

public class SpecialCouponValidator implements CouponValidator {

    @Override
    public void validateCouponDTO(Coupon couponDTO) {
        if (couponDTO.getCode() == null || couponDTO.getCode().trim().isEmpty()) {
            throw new IllegalCouponException("Coupon code is null or empty.");
        }
        if (!couponDTO.getCode().startsWith("SPECIAL")) {
            throw new IllegalCouponException("Coupon code must start with 'SPECIAL'.");
        }
    }

    @Override
    public void validateCouponRequest(CouponRequest couponRequest) {
        if (couponRequest == null || couponRequest.getCodes().stream().allMatch(code -> code.trim().isEmpty())) {
            throw new IllegalCouponException("List of codes is null or empty.");
        }
        if (couponRequest.getCodes().stream().noneMatch(code -> code.startsWith("SPECIAL"))) {
            throw new IllegalCouponException("At least one code must start with 'SPECIAL'.");
        }
    }
}
