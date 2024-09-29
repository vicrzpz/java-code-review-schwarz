package schwarz.jobs.interview.coupon.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import org.springframework.stereotype.Repository;
import schwarz.jobs.interview.coupon.model.CouponEntity;


@Repository
public interface CouponRepository extends JpaRepository<CouponEntity, Long> {

    Optional<CouponEntity> findByCode(final String code);

}
