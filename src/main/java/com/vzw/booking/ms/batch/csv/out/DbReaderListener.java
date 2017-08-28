/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.csv.out;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 *
 * @author smorcja
 */
public class DbReaderListener implements StepExecutionListener {

    @Override
    public void beforeStep(StepExecution se) {
        System.out.println("Before step method executed, ste name: "+se.getStepName());
    }

    @Override
    public ExitStatus afterStep(StepExecution se) {
        System.out.println("step completed, read count: "+se.getReadCount() + ", write count: "+se.getWriteCount());
        return se.getExitStatus();
    }
    
}
