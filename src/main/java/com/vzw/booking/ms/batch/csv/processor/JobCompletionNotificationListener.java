/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.csv.processor;

import com.vzw.booking.ms.batch.dump.BatchExecutionSchemaDumpLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 *
 * @author smorcja
 */
@Component
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {
    
//    @Autowired
//    private VlfLogger logger;
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchExecutionSchemaDumpLauncher.class);
    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
            this.jdbcTemplate = jdbcTemplate;
    }
    
    @Override
    public void afterJob(JobExecution jobExecution) {
        if(jobExecution.getStatus() == BatchStatus.COMPLETED) {
            //logger.write("Transfer completed, it's time to verify results but how to do that actually?", VlfLogger.Severity.INFO, null);
            LOGGER.info("*** Transfer completed, it's time to verify results but how to do that actually? ***");
        }
    }
}
