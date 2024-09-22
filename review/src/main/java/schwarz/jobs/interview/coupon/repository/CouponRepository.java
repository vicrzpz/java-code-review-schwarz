package schwarz.jobs.interview.coupon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import schwarz.jobs.interview.coupon.model.CouponEntity;

/**
 * Repository interface for Coupon entity.
 * Provides methods to perform CRUD operations and custom queries.
 */
@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    /**
     * Finds a coupon by its code.
     *
     * @param code the coupon code
     * @return an Optional containing the found Coupon, or empty if no coupon found
     */
    Optional<CouponEntity> findByCode(final String code);

}
