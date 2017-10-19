/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.services;

import eu.squadd.batch.launchers.BatchExecutionSchemaDumpLauncher;
import eu.squadd.batch.launchers.BookingsJobLauncher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author smorcja
 */
@RestController
@RequestMapping("/")
public class BookingWholesaleJobController {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(BookingWholesaleJobController.class);
    
    @Autowired
    private BookingsJobLauncher bookingJobLauncher;
    
    @Autowired
    private BatchExecutionSchemaDumpLauncher dumpJobLauncher;
    
    
    @RequestMapping(path="/runJob", method=RequestMethod.GET)
    public String runSpringBatchJob(@RequestParam(name="jobName") String jobName) {
        try {
            switch (jobName) {
                case "dump":
                    dumpJobLauncher.runSchemaDumpJob();
                    break;
                case "booking":
                    bookingJobLauncher.runBookingWholesaleJob();
                    break;
                default:
                    return "Such job doesn not exist";
            }            
        } catch (JobParametersInvalidException | JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException ex) {
            LOGGER.error(ex.getMessage());
        }
        return "Job Executed";
    }
}
