/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.listeners;

import eu.squadd.batch.domain.BilledCsvFileDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 *
 * @author smorcja
 */
public class BilledBookingFileStepExecutionListener implements StepExecutionListener, SkipListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BilledBookingFileStepExecutionListener.class);
    
    @Override
    public void beforeStep(StepExecution se) {        
    }

    @Override
    public ExitStatus afterStep(StepExecution se) {
        LOGGER.info("Step completed, read count: "+se.getReadCount() + ", write count: "+se.getWriteCount());
        return se.getExitStatus();
    }

    @Override
    public void onSkipInRead(Throwable exception) {
        LOGGER.error("Reader exception encountered: " + exception.getMessage());
    }

    @Override
    public void onSkipInWrite(Object s, Throwable exception) {
        LOGGER.error("Writer exception encountered: " + exception.getMessage());
    }

    @Override
    public void onSkipInProcess(Object inputRecord, Throwable exception) {
        if (inputRecord instanceof BilledCsvFileDTO) {
            BilledCsvFileDTO rec = (BilledCsvFileDTO) inputRecord;
            // export errornous record somewhere if neccessary
            LOGGER.error(rec.toString());            
        }
    }
}
