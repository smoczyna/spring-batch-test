/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;

/**
 *
 * @author smorcja
 */
public class CsvFileReaderListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileReaderListener.class);
    
    @Override
    public void beforeStep(StepExecution se) {
        LOGGER.info("Csv File Reader Listeer - before step");
    }

    @Override
    public ExitStatus afterStep(StepExecution se) {
        LOGGER.info("Step completed, read count: "+se.getReadCount() + ", write count: "+se.getWriteCount());
        return se.getExitStatus();
    }

}
