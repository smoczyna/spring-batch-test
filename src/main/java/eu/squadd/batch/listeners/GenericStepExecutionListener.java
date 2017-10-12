/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.listeners;

import eu.squadd.batch.utils.WholesaleBookingProcessorHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class GenericStepExecutionListener implements StepExecutionListener, SkipListener, ItemProcessListener {

    @Autowired
    WholesaleBookingProcessorHelper tempSubLedgerOuput;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericStepExecutionListener.class);
    private Long recordCount;
    
    @Override
    public void beforeStep(StepExecution se) { 
        this.recordCount = 0L;
    }

    @Override
    public ExitStatus afterStep(StepExecution se) {
        LOGGER.info("Step completed, read count: "+se.getReadCount() + ", write count: "+se.getWriteCount());
        LOGGER.info("Number of sub ledger records created: "+this.tempSubLedgerOuput.getCounter("sub"));
        LOGGER.info("Number of zero charges: "+this.tempSubLedgerOuput.getCounter("zero"));
        LOGGER.info("Number of gaps: "+this.tempSubLedgerOuput.getCounter("gap"));
        LOGGER.info("Number of data errors: "+this.tempSubLedgerOuput.getCounter("error"));
        LOGGER.info("Number of bypasses: "+this.tempSubLedgerOuput.getCounter("bypass"));
        this.tempSubLedgerOuput.clearCounters();
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
        LOGGER.error(inputRecord.toString());
    }

    @Override
    public void beforeProcess(Object t) {
        LOGGER.info("Processing records: "+ (++recordCount));
    }

    @Override
    public void afterProcess(Object t, Object s) {
    }

    @Override
    public void onProcessError(Object t, Exception excptn) {
    }
}
