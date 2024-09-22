package schwarz.jobs.interview.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the Coupon API.
 */
@SpringBootApplication
public class CouponApplication {
	/**
	 * The method that starts the Spring application.
	 *
	 * @param args command line arguments
	 */
	public static void main(String[] args) {
		SpringApplication.run(CouponApplication.class, args);
	}

}
