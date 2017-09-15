/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.validation;

import java.io.FileNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.step.skip.SkipLimitExceededException;
import org.springframework.batch.core.step.skip.SkipPolicy;
import org.springframework.batch.item.file.FlatFileParseException;

/**
 *
 * @author smorcja
 */
public class CsvFileVerificationSkipper implements SkipPolicy {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileVerificationSkipper.class);
    
    @Override
    public boolean shouldSkip(Throwable exception, int skipCount) throws SkipLimitExceededException {
        boolean result = false;
        if (exception instanceof FileNotFoundException) return false;
        else if (exception instanceof FlatFileParseException && skipCount<5) {
            FlatFileParseException ffpe = (FlatFileParseException) exception;
            LOGGER.error("Parsing error when processing line: " + ffpe.getLineNumber());
            result = true;
        }
        else if (exception instanceof NullPointerException && skipCount<5) {
            NullPointerException npe = (NullPointerException) exception;
            LOGGER.error("NULL encountered where the value was expected");
            result = true;
        }
        return result;
    }    
}
