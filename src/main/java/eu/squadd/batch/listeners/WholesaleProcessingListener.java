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
import org.springframework.batch.core.ItemProcessListener;
import org.springframework.batch.core.SkipListener;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smoczyna
 */
public class WholesaleProcessingListener implements ItemProcessListener, SkipListener {
    
    private static final Logger LOGGER = LoggerFactory.getLogger(WholesaleProcessingListener.class);
    
    @Autowired
    WholesaleBookingProcessorHelper processingHelper;
    
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
        processingHelper.incrementCounter(Constants.RECORD_COUNT);
        LOGGER.info(String.format(Constants.PROCESSING_RECORD, processingHelper.getCounter(Constants.RECORD_COUNT)));
    }

    @Override
    public void afterProcess(Object t, Object s) {
    }

    @Override
    public void onProcessError(Object t, Exception excptn) {
    }
}
