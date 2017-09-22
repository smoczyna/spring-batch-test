/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.jobs;

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
    private String filename;
    
    @Value("${csv.to.database.job.source.file.path}")
    private String SOURCE_FILES_PATH;
    
    public SourceFilesExistanceChecker(String filename) {
        this.filename = filename;
    }
    
    @Override
    public RepeatStatus execute(StepContribution sc, ChunkContext cc) throws Exception {
        LOGGER.info("Checkig if file exists...");
        //String filename = (String) cc.getStepContext().getJobParameters().get("billed_csv_file_name");
        File f = new File(SOURCE_FILES_PATH.concat(filename));
        if (!f.exists() || f.isDirectory()) {
            LOGGER.error("One or more required files not found, job aborted");
            throw new JobInterruptedException("Source FIle does not exist");
        } else {
            return RepeatStatus.FINISHED;
        }
    }
}
