package eu.squadd.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <h1>WholesaleBookingProcessorApplication</h1> 
 * <p>
 * Entry point of the application. 
 * It is a standard Spring Boot application class.
 * </p>
 */

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class BookingProcessorApplication {

	/**
	 * This the main method for the application. 
         * It executes the application
	 * 
	 * @param args - arguments passed to the spring boot application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(BookingProcessorApplication.class, args);
	}

}
