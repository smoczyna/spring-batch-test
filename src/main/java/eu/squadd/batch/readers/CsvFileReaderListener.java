/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.vzw.booking.ms.batch.readers;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.logging.Level;
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
        LOGGER.info("Check if file exists and stop the job if not, step name: "+se.getStepName());
        File f = new File(PROPERTY_CSV_SOURCE_FILE_PATH.concat("students.csv"));
        if(!f.exists() || f.isDirectory()) {
            try {
                LOGGER.error("File not found, aborting job ...");
                throw new FileNotFoundException();
            } catch (FileNotFoundException ex) {
               LOGGER.error(ex.getLocalizedMessage(), Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public ExitStatus afterStep(StepExecution se) {
        LOGGER.info("Step completed, read count: "+se.getReadCount() + ", write count: "+se.getWriteCount());
        return se.getExitStatus();
    }

}
