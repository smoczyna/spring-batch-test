/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.validations;

import eu.squadd.batch.constants.Constants;
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
            LOGGER.error(Constants.MAX_ALLOWED_EXCEPTION);
        } else {
            if (exception instanceof FileNotFoundException) {
                LOGGER.error(Constants.FILE_MISSING_MESSAGE);
            } else if (exception instanceof FlatFileParseException) {
                FlatFileParseException ffpe = (FlatFileParseException) exception;
                LOGGER.error(String.format(Constants.PARSING_ERROR, ffpe.getLineNumber()));
                result = true;
            } else if (exception instanceof NullPointerException) {
                NullPointerException npe = (NullPointerException) exception;
                result = true;
            }
            LOGGER.error(Constants.RECORD_SKIP_DETECTED);
            this.helper.incrementCounter(Constants.DATA_ERRORS);
        }
        return result;
    }
}
