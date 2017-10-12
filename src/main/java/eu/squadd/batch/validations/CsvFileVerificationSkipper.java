/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.validations;

import eu.squadd.batch.utils.WholesaleBookingProcessorHelper;
import java.io.FileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author smorcja
 */
public class CsvFileVerificationSkipper implements SkipPolicy {

    @Autowired
    private WholesaleBookingProcessorHelper helper;
    
    private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileVerificationSkipper.class);

    @Override
    public boolean shouldSkip(Throwable exception, int skipCount) throws SkipLimitExceededException {
        boolean result = false;        
        if (skipCount > helper.getMaxSkippedRecords()) {
            LOGGER.error("Maximum allowed exceptions reached, terminating...");
        } else {
            if (exception instanceof FileNotFoundException) {
                LOGGER.error("File missing, terminating...");
            } else if (exception instanceof FlatFileParseException) {
                FlatFileParseException ffpe = (FlatFileParseException) exception;
                LOGGER.error("Parsing error when processing line: " + ffpe.getLineNumber());
                result = true;
            } else if (exception instanceof NullPointerException) {
                NullPointerException npe = (NullPointerException) exception;
                LOGGER.error("NULL encountered but the value was expected - skipping record ...");
                result = true;
            }            
        }
        return result;
    }
}