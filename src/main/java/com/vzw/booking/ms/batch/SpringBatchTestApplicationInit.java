package com.vzw.booking.ms.batch;

import com.vzw.booking.ms.batch.config.DerbyDbConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.vzw.booking.ms.batch.config.LoggerConfig;
import com.vzw.booking.ms.batch.util.CustomerIdGenerator;
import com.vzw.vlf.lib.logger.VlfLogger;
import com.vzw.vlf.lib.logger.VlfLogger.LogLevel;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;

/**
 * <h1>TemplateApplicationInit</h1> TemplateApplicationInit is an initialization
 * class. 1. Inject the application config class. 2. Define a bean to initialize
 * the logger based on the application specific configurations Additional
 * initializations can be autowired here.
 * <p>
 *
 */
@Component
public class SpringBatchTestApplicationInit {

    @Autowired
    private LoggerConfig loggerConfig;

    /**
     * Statefull bean holding customerId 
     */
    public CustomerIdGenerator idGenerator = new CustomerIdGenerator();
    
    /**
     * Define a Bean to initialize the logger.
     *
     * @return Verizon Logger
     */
    @Bean
    public VlfLogger vlfLogger() {
        VlfLogger logger = new VlfLogger();
        logger.setAppName(this.loggerConfig.getAppname());
        logger.setServiceName(this.loggerConfig.getServicename());
        logger.setRegion(this.loggerConfig.getRegion());
        logger.setZone(this.loggerConfig.getZone());
        VlfLogger.setLogLevel(LogLevel.INFO);
        return logger;
    }

    /**
     * This is meta data source
     * this source is used by Spring Batch internally to control processing
     * @return 
     */
    @Bean(name = "metaDataSource")
    @Primary
    @ConfigurationProperties(prefix = "spring.datasource") //connection and credentials configured in application.properties
    public DataSource metaDataSource() {
        return DataSourceBuilder.create().build();
    }
    
//    public DataSource memoryDataSource() {
//        EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
//        EmbeddedDatabase db = builder
//                .setType(EmbeddedDatabaseType.H2) //.H2 or .HSQL or .DERBY
//                .addScript("data/meta/schema-h2.sql")
//                .addScript("data/meta/students_table.sql")
//                .build();
//        return db;
//    }
}
