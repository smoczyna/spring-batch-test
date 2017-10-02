/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.jobs;

import eu.squadd.batch.constants.Constants;
import java.io.File;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.JobInterruptedException;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author smorcja
 */
public class SourceFilesExistanceChecker implements Tasklet {

    private static final Logger LOGGER = LoggerFactory.getLogger(SourceFilesExistanceChecker.class);
    
    @Value("${csv.to.database.job.source.file.path}")
    private String SOURCE_FILES_PATH;

    @Override
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {
        LOGGER.info("Checkig if file exists...");
        File f1 = new File(SOURCE_FILES_PATH.concat(Constants.BOOK_DATE_FILENAME));
        File f2 = new File(SOURCE_FILES_PATH.concat(Constants.BILLED_BOOKING_FILENAME));
        File f3 = new File(SOURCE_FILES_PATH.concat(Constants.UNBILLED_BOOKING_FILENAME));
        File f4 = new File(SOURCE_FILES_PATH.concat(Constants.ADMIN_FEES_FILENAME));        
        if ((!f1.exists() || f1.isDirectory()) ||
            (!f2.exists() || f2.isDirectory()) ||
            (!f3.exists() || f3.isDirectory()) ||
            (!f4.exists() || f4.isDirectory())) {
            LOGGER.error("One or more required files not found, job aborted.");
            throw new JobInterruptedException("Source FIle(s) do not arrived yet");
        } else {
            return RepeatStatus.FINISHED;
        }
    }
}
