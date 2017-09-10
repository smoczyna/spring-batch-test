/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.readers;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author smorcja
 */
public class CsvFileReaderListener implements StepExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileReaderListener.class);
    
    @Value("${csv.to.database.job.source.file.path}")
    private String PROPERTY_CSV_SOURCE_FILE_PATH;
    
    @Override
    public void beforeStep(StepExecution se) {
        // file check moved to job listener
    }

    @Override
    public ExitStatus afterStep(StepExecution se) {
        LOGGER.info("Step completed, read count: "+se.getReadCount() + ", write count: "+se.getWriteCount() + "Moving file to archive..");
        
        return se.getExitStatus();
    }

    
    
}
