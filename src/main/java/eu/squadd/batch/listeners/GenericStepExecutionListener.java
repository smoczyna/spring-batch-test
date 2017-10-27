/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.listeners;

import eu.squadd.batch.constants.Constants;
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
    WholesaleBookingProcessorHelper processingHelper;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericStepExecutionListener.class);
    private Long recordCount;
    
    @Override
    public void beforeStep(StepExecution se) { 
        this.recordCount = 0L;
        //this.processingHelper.setStepExecutionContext(se.getExecutionContext());
    }

    @Override
    public ExitStatus afterStep(StepExecution se) {
        //this.processingHelper.setStepExecutionContext(null);
        LOGGER.info(String.format(Constants.JOB_EXECUTION_FINISHED, se.getReadCount(), se.getWriteCount()));        
        LOGGER.info(String.format(Constants.WHOLESALE_REPORT_NO, this.processingHelper.getCounter(Constants.WHOLESALES_REPORT)));
        LOGGER.info(String.format(Constants.SUBLEDGER_REPORD_NO, this.processingHelper.getCounter(Constants.SUBLEDGER)));
        LOGGER.info(String.format(Constants.ZERO_CHARGE_NO, this.processingHelper.getCounter(Constants.ZERO_CHARGES)));
        LOGGER.info(String.format(Constants.CODE_GAPS_NO, this.processingHelper.getCounter(Constants.GAPS)));
        LOGGER.info(String.format(Constants.DATA_ERRORS_NO, this.processingHelper.getCounter(Constants.DATA_ERRORS)));
        LOGGER.info(String.format(Constants.BYPASS_NO, this.processingHelper.getCounter(Constants.BYPASS)));
        this.processingHelper.clearCounters();
        return se.getExitStatus();
    }

    @Override
    public void onSkipInRead(Throwable exception) {
        LOGGER.error(String.format(Constants.READER_EXCEPTION, exception.getMessage()));
    }

    @Override
    public void onSkipInWrite(Object s, Throwable exception) {
        LOGGER.error(String.format(Constants.WRITER_EXCEPTION, exception.getMessage()));
    }

    @Override
    public void onSkipInProcess(Object inputRecord, Throwable exception) {
        LOGGER.error(inputRecord.toString());
    }

    @Override
    public void beforeProcess(Object t) {
        LOGGER.info(String.format(Constants.PROCESSING_RECORD, ++recordCount));
    }

    @Override
    public void afterProcess(Object t, Object s) {
    }

    @Override
    public void onProcessError(Object t, Exception excptn) {
    }
}
