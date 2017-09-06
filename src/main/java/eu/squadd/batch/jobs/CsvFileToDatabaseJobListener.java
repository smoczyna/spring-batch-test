/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.jobs;

import java.util.List;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 *
 * @author smorcja
 */
public class CsvFileToDatabaseJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution je) {
        System.out.println("Before job kick off listener can do something here");
    }

    @Override
    public void afterJob(JobExecution je) {
        if(je.getStatus() == BatchStatus.COMPLETED) {
            System.out.println("After job fisnishes with success listener can do something");
        }
        else {
            System.out.println("After job fisnishes with failure listener can dislay all exceptions:");
            List<Throwable> exceptionList = je.getAllFailureExceptions();
            for(Throwable th : exceptionList){
                System.err.println("exception :" +th.getLocalizedMessage());
            }
        }
        
    }
    
}
