/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.listeners;

import eu.squadd.batch.launchers.BatchExecutionSchemaDumpLauncher;
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
public class BatchExecutionSchemaToFileConfigListener extends JobExecutionListenerSupport {

    private static final Logger LOGGER = LoggerFactory.getLogger(BatchExecutionSchemaDumpLauncher.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BatchExecutionSchemaToFileConfigListener(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * this has to leave current execution data otherwise following error occur: EmptyResultDataAccessException
     * @param jobExecution 
     */
    @Override
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            LOGGER.info("*** Transfer completed, it's time to cleanup ***");
            
            JobInstance instance = jobExecution.getJobInstance();
            
            LOGGER.info("Clearing BATCH_STEP_EXECUTION_CONTEXT table");
            String sql = "delete from batch.batch_step_execution_context where STEP_EXECUTION_ID in (\n" +
                         "select step_execution_id from batch.batch_step_execution step, batch.batch_job_execution job\n" +
                         "where step.job_execution_id = job.job_execution_id\n" +
                         "and job.job_instance_id < ?)";            
            this.jdbcTemplate.update(sql, new Object[]{instance.getInstanceId()});
            
            LOGGER.info("Clearing BATCH_STEP_EXECUTION table");
            sql = "delete from batch.batch_step_execution where job_execution_id in (\n" +
                  "select job.job_execution_id from batch.batch_job_execution job\n" +
                  "where job.job_instance_id < ?) ";
            this.jdbcTemplate.update(sql, new Object[]{instance.getInstanceId()});
            
            LOGGER.info("Clearing BATCH_JOB_EXECUTION_PARAMS table");
            sql = "delete from BATCH.BATCH_JOB_EXECUTION_PARAMS where job_execution_id in (\n" +
                  "select job.job_execution_id from batch.batch_job_execution job\n" +
                  "where job.job_instance_id < ?)";
            this.jdbcTemplate.update(sql, new Object[]{instance.getInstanceId()});
            
            LOGGER.info("Clearing BATCH_JOB_EXECUTION_CONTEXT table");
            sql = "delete from BATCH.BATCH_JOB_EXECUTION_CONTEXT where job_execution_id in (\n" +
                  "select job.job_execution_id from batch.batch_job_execution job\n" +
                  "where job.job_instance_id < ?)";
            this.jdbcTemplate.update(sql, new Object[]{instance.getInstanceId()});
            
            LOGGER.info("Clearing BATCH_JOB_EXECUTION table");
            sql = "DELETE FROM BATCH.BATCH_JOB_EXECUTION where job_instance_id < ?";
            this.jdbcTemplate.update(sql, new Object[]{instance.getInstanceId()});
            
            LOGGER.info("Clearing BATCH_JOB_INSTANCE table");
            sql = "DELETE FROM BATCH.BATCH_JOB_INSTANCE where job_instance_id < ?";
            this.jdbcTemplate.update(sql, new Object[]{instance.getInstanceId()});
        }
    }
}
