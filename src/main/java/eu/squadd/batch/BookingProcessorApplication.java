package eu.squadd.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
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
@EnableCaching
public class BookingProcessorApplication {

    //static Logger LOGGER = Logger.getLogger(BookingProcessorApplication.class.getName());
    
    /**
     * This the main method for the application. It executes the application
     *
     * @param args - arguments passed to the spring boot application.
     */
    public static void main(String[] args) {
//        try {
//            FileHandler fh = new FileHandler("c:/Users/smorcja/Temp/tmp/File.log");
//            LOGGER.addHandler(fh);
//            SimpleFormatter formatter = new SimpleFormatter();
//            fh.setFormatter(formatter);
//            
//            // the following statement is used to log any messages
//            LOGGER.info("Booking Wholesale App: ");
//            
//        } catch (IOException | SecurityException ex) {
//            Logger.getLogger(BookingWholesaleApplication.class.getName()).log(Level.SEVERE, null, ex);
//        }
        SpringApplication.run(BookingProcessorApplication.class, args);
    }
}
