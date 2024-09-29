package schwarz.jobs.interview.coupon.service.validator;

import schwarz.jobs.interview.coupon.dto.Coupon;
import schwarz.jobs.interview.coupon.dto.CouponRequest;

public interface CouponValidator {
    void validateCouponDTO(Coupon couponDTO);
    void validateCouponRequest(CouponRequest couponRequest);
}
