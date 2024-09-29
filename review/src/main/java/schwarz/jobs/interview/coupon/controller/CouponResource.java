package schwarz.jobs.interview.coupon.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import schwarz.jobs.interview.coupon.exception.IllegalCouponException;
import schwarz.jobs.interview.coupon.exception.IllegalDiscountException;
import schwarz.jobs.interview.coupon.handler.ResponseHandler;
import schwarz.jobs.interview.coupon.model.CouponEntity;
import schwarz.jobs.interview.coupon.repository.CouponRepository;
import schwarz.jobs.interview.coupon.service.CouponService;
import schwarz.jobs.interview.coupon.dto.Basket;
import schwarz.jobs.interview.coupon.dto.ApplicationRequest;
import schwarz.jobs.interview.coupon.dto.Coupon;
import schwarz.jobs.interview.coupon.dto.CouponRequest;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class CouponResource {

    private final CouponService couponService;
    private final CouponRepository couponRepository;
    private final ResponseHandler responseHandler;

    @PostMapping("/applyCoupon")
    public ResponseEntity<Basket> applyCoupon(@RequestBody @Valid ApplicationRequest applicationRequest) {
        log.info("Applying coupon: {}", applicationRequest.getCode());
        Basket basket = null;
        try {
            basket = couponService.applyCoupon(
                    applicationRequest.getBasket(), applicationRequest.getCode());
            log.info("Coupon applied successfully");
            return responseHandler.handleSuccess(basket, "Coupon applied successfully");
        } catch (IllegalDiscountException e) {
            log.error("Error applying coupon: {}", e.getMessage());
            return (ResponseEntity<Basket>) responseHandler.handleError
                    (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("/createCoupon")
    public ResponseEntity<CouponEntity> createCoupon(@RequestBody @Valid Coupon couponDTO) {

        Optional<CouponEntity> existingCoupon = couponRepository.findByCode(couponDTO.getCode());
        if (existingCoupon.isPresent()) {
            log.error("Coupon: {} already exists.", couponDTO.getCode());
            return (ResponseEntity<CouponEntity>) responseHandler.handleError
                    ("Coupon already exists", HttpStatus.BAD_REQUEST);
        }
        CouponEntity couponEntity = couponService.createCoupon(couponDTO);
        log.info("Coupon created successfully with ID: {}", couponEntity.getId());
        return responseHandler.handleSuccess(couponEntity, "Coupon created");
    }

    @GetMapping("/getCoupons")
    public ResponseEntity<List<CouponEntity>> findCouponsByCodes(@RequestParam List<String> codes) {
        CouponRequest couponRequest = new CouponRequest(codes);
        try {
            List<CouponEntity> couponEntities = couponService.findCouponsByCodes(couponRequest);
            if (couponEntities.isEmpty()) {
                return (ResponseEntity<List<CouponEntity>>) responseHandler.handleError
                        ("Coupon/s not found.", HttpStatus.NOT_FOUND);
            }
            log.info("Retrieved {} coupons.", couponEntities.size());
            return responseHandler.handleSuccess(couponEntities, "Coupon/s retrieved successfully");
        } catch (IllegalCouponException e) {
            log.info("List param empty.");
            return (ResponseEntity<List<CouponEntity>>) responseHandler.handleError
                    (e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }
}
