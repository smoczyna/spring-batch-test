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
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class GenericStepExecutionListener implements StepExecutionListener {

    @Autowired
    WholesaleBookingProcessorHelper processingHelper;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(GenericStepExecutionListener.class);
    
    
    @Override
    public void beforeStep(StepExecution se) {
    }

    @Override
    public ExitStatus afterStep(StepExecution se) {
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

    
}
