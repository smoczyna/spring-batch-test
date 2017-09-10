package eu.squadd.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * <h1>TemplateApplication</h1> TemplateApplication is the entry point of the application. It is
 * a standard Spring Boot application class.
 * <p>
 * 
 */

@SpringBootApplication
@EnableBatchProcessing
@EnableScheduling
public class SpringBatchTestApplication {

	/**
	 * This the main method for the application.
	 * 
	 * @param args
	 *            The runtime arguments are passed to the spring boot
	 *            application.
	 */
	public static void main(String[] args) {
		SpringApplication.run(SpringBatchTestApplication.class, args);
	}

}
