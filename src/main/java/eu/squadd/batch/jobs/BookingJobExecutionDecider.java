/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.jobs;

import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.job.flow.FlowExecutionStatus;
import org.springframework.batch.core.job.flow.JobExecutionDecider;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author smorcja
 */
public class BookingJobExecutionDecider implements JobExecutionDecider {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingJobExecutionDecider.class);
    
    @Value("${source.files.path}")
    private String SOURCE_FILES_PATH;
    
    @Override
    public FlowExecutionStatus decide(JobExecution je, StepExecution se) {
        LOGGER.info("Check if file exists and return failure if not");
        String filename = je.getJobParameters().getString("billed_csv_file_name");
        File f = new File(SOURCE_FILES_PATH.concat(filename));
        if (!f.exists() || f.isDirectory()) {
            LOGGER.error("File not found, aborting job ...");
            return FlowExecutionStatus.STOPPED;
        } else {
            return FlowExecutionStatus.COMPLETED;
        }
    }    
}
