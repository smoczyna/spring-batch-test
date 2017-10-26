/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.launchers;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * This is database dumping job, it moved all job execution data from internal Derby db to Cassandra db
 * it has auto-execution disabled as it won't be run immediately after migration job, 
 * it rather will be executed manually after checking migration correctness
 * @author smorcja
 */
@Component
public class BatchExecutionSchemaDumpLauncher {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BatchExecutionSchemaDumpLauncher.class);
    private final Job job;
    private final JobLauncher jobLauncher;
    
    @Autowired
    public BatchExecutionSchemaDumpLauncher(@Qualifier("batchSchemaDumpToCsvFileJob") Job job, JobLauncher jobLauncher) {
        this.job = job;
        this.jobLauncher = jobLauncher;
    }
    
    @Scheduled(cron = "${database.cleanup.job.cron}")
    public void runSchemaDumpJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        LOGGER.info("Starting batch schema dump job");
        jobLauncher.run(job, newExecution());
        LOGGER.info("Stopping batch schema dump job");
    }

    private JobParameters newExecution() {
        Map<String, JobParameter> parameters = new HashMap<>();
        JobParameter parameter = new JobParameter(new Date());
        parameters.put("currentTime", parameter);
        return new JobParameters(parameters);
    }
}
