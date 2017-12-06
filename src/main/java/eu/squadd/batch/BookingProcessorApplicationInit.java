package eu.squadd.batch;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import eu.squadd.batch.utils.CustomIdGenerator;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

/**
 * <h1>WholesaleBookingProcessorApplicationInit</h1>
 * This is initialization class for the application.
 * <p>
 * 1. Inject the application config classes. 
 * 2. Define beans to initialize
 * 3. Can create the logger based on the application specific configurations
 * </p>
 *
 */
@Component
public class BookingProcessorApplicationInit {

//    @Autowired
//    private LoggerConfig loggerConfig;

    /**
     * Stateful bean providing generated IDs
     */
    public CustomIdGenerator idGenerator = new CustomIdGenerator();
    
    @Bean(name = "jobScheduler")
    public TaskScheduler jobScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setThreadNamePrefix("poolScheduler");
        scheduler.setPoolSize(10);
        return scheduler;
    }
    
    @Bean(name = "metaDataSource")
    @Primary
    public DataSource memoryDataSource() {
        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.H2) //.H2 or .HSQL or .DERBY
                .addScript("data/meta/schema-h2.sql")
                .build();
        return db;
    }
}
