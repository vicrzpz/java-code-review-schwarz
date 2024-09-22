package schwarz.jobs.interview.coupon.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import schwarz.jobs.interview.coupon.exception.IllegalCouponException;
import schwarz.jobs.interview.coupon.exception.IllegalDiscountException;
import schwarz.jobs.interview.coupon.model.CouponEntity;
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

    /**
     * Applies a coupon to the given basket.
     *
     * @param applicationRequest the request containing basket and coupon code
     * @return the updated basket or an error response
     */
    @PostMapping("/apply")
    public ResponseEntity<Basket> apply(@RequestBody @Valid ApplicationRequest applicationRequest) {
        log.info("Applying coupon: {}", applicationRequest.getCode());

        try {
            Optional<Basket> optionalBasket = couponService.applyCoupon(
                    applicationRequest.getBasket(), applicationRequest.getCode());

            return optionalBasket
                    .map(basketDTO -> {
                        log.info("Coupon applied successfully");
                        return ResponseEntity.ok(basketDTO);
                    })
                    .orElseGet(() -> {
                        log.warn("Coupon application failed: Basket not found");
                        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(applicationRequest.getBasket());
                    });
        } catch (IllegalDiscountException e) {
            log.error("Error applying coupon: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        } catch (Exception e) {
            log.error("Unexpected error applying coupon: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Creates a new coupon.
     *
     * @param couponDTO the coupon data
     * @return the created coupon
     */
    @PostMapping("/create")
    public ResponseEntity<CouponEntity> create(@RequestBody @Valid Coupon couponDTO) {
        try {
            CouponEntity couponEntity = couponService.createCoupon(couponDTO);
            log.info("Coupon created successfully with ID: {}", couponEntity.getId());
            return ResponseEntity.status(HttpStatus.CREATED).body(couponEntity);
        } catch (IllegalCouponException e) { // Captura de excepción específica
            log.error("Specific error creating coupon: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        } catch (Exception e) {
            log.error("Unexpected error creating coupon: {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * Retrieves a list of coupons based on the request.
     *
     * @param couponRequest the request containing coupon filters
     * @return the list of coupons
     */
    @PostMapping("/coupons")
    public ResponseEntity<List<CouponEntity>> getCoupons(@RequestBody @Valid CouponRequest couponRequest) {
        List<CouponEntity> couponEntities = couponService.getCoupons(couponRequest);
        log.info("Retrieved {} coupons", couponEntities.size());
        return ResponseEntity.ok(couponEntities);
    }
}
