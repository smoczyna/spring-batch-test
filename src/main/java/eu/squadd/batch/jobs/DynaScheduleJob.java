/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.jobs;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobInstanceAlreadyExistsException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author smorcja
 */
@Component
public class DynaScheduleJob extends Thread {

    private static final Logger LOGGER = LoggerFactory.getLogger(DynaScheduleJob.class);

    private Job job;
    private JobParameters jobParameters;
    private final JobOperator jobOperator;

    @Autowired
    public DynaScheduleJob(JobOperator jobOperator) {
        this.jobOperator = jobOperator;
    }

    public void setJob(Job job){
        this.job = job;
    }

    private JobParameters newExecution() {
        Map<String, JobParameter> parameters = new HashMap<>();
        JobParameter parameter = new JobParameter(new Date());
        parameters.put("currentTime", parameter);
        return new JobParameters(parameters);
    }
    
    @Override
    public void run(){
        try {
            this.jobParameters = this.newExecution();
            LOGGER.info("Executing job: " + job.getName());
            jobOperator.start(job.getName(), jobParameters.toString());
        } catch (NoSuchJobException | JobInstanceAlreadyExistsException | JobParametersInvalidException ex) {
            LOGGER.error(ex.getMessage());
        }
    }
}
