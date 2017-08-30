/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.csv.processor;

import com.vzw.booking.ms.batch.dump.BatchExecutionSchemaDumpLauncher;
import javax.sql.DataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
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

    @Autowired
    private DataSource metaDataSource;
    
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public JobCompletionNotificationListener(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * this has to go to another job as clearing tables before job finish causes error: EmptyResultDataAccessException
     * @param jobExecution 
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("*** Transfer completed, it's time to cleanup ***");
            
            JobInstance instance = jobExecution.getJobInstance();
            String sql = "DELETE FROM BATCH.BATCH_STEP_EXECUTION_CONTEXT where step_execution_id in ( " +
                         "SELECT step_execution_id FROM BATCH.BATCH_STEP_EXECUTION where job_execution_id < ?)";            
            this.jdbcTemplate.update(sql, new Object[]{instance.getInstanceId()});
            
            sql = "DELETE FROM BATCH.BATCH_STEP_EXECUTION where job_execution_id < ?";
            this.jdbcTemplate.update(sql, new Object[]{instance.getInstanceId()});
            
            sql = "DELETE FROM BATCH.BATCH_JOB_EXECUTION_PARAMS where job_execution_id < ?";
            this.jdbcTemplate.update(sql, new Object[]{instance.getInstanceId()});
            
            sql = "DELETE FROM BATCH.BATCH_JOB_EXECUTION_CONTEXT where job_execution_id < ?";
            this.jdbcTemplate.update(sql, new Object[]{instance.getInstanceId()});
            
            sql = "DELETE FROM BATCH.BATCH_JOB_EXECUTION where job_execution_id < ?";
            this.jdbcTemplate.update(sql, new Object[]{instance.getInstanceId()});
            
            sql = "DELETE FROM BATCH.BATCH_JOB_INSTANCE where job_execution_id < ?";
            this.jdbcTemplate.update(sql, new Object[]{instance.getInstanceId()});
        }
    }

            //TreeSet<StepExecution> executions = (TreeSet<StepExecution>) jobExecution.getStepExecutions();
            //Collections.sort(executions, new StepExecutionComparator());
            
            //LOGGER.info("Smallest execution is: " + executions.first(). )
            
            //logger.write("Transfer completed, it's time to verify results but how to do that actually?", VlfLogger.Severity.INFO, null);
            
//            
//            this.jdbcTemplate.update("delete from batch_step_execution");
//            this.jdbcTemplate.update("delete from batch_job_execution_context");
//            this.jdbcTemplate.update("delete from batch_job_execution_params");
//            this.jdbcTemplate.update("delete from batch_job_execution");
//            this.jdbcTemplate.update("delete from batch_job_instance");
//            LOGGER.info("*** Cleanup completed, all batch tables are empty now ***");
    
//    private class StepExecutionComparator<StepExecution> implements Comparator {
//
//        @Override
//        public int compare(Object o1, Object o2) {
//            StepExecution seLeft = (StepExecution) o1;
//            StepExecution seRight = (StepExecution) o2;
//            seLeft.
//        }
//        
//    }
}
