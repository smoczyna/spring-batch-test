/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.listeners;

import eu.squadd.batch.util.ProcessingUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Value;

/**
 *
 * @author smorcja
 */
public class BilledBookingFileJobListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(BilledBookingFileJobListener.class);

    @Value("${csv.to.database.job.source.file.path}")
    private String BIILED_CSV_SOURCE_FILE_PATH;

    @Override
    public void beforeJob(JobExecution je) {
        LOGGER.info("Wholesale booking processor started at: "+ProcessingUtils.dateToString(je.getJobParameters().getDate("currentTime"), ProcessingUtils.SHORTDATETIME_FORMAT));
    }

    @Override
    public void afterJob(JobExecution je) {
        if (je.getStatus() == BatchStatus.COMPLETED) {
            String filename = je.getJobParameters().getString("billed_csv_file_name");
            this.moveFileToArchive(filename);
        } else {
            LOGGER.info("All encountered exceptions:");
            List<Throwable> exceptionList = je.getAllFailureExceptions();
            exceptionList.forEach((th) -> {
                LOGGER.error("exception :" + th.getLocalizedMessage());
            });
        }
    }

    /**
     * moved source file to archive folder to avoid duplicates
     * process reacts for a file of such name
     * @param filename 
     */
    private void moveFileToArchive(String filename) {
        try {
            File srcFile = new File(BIILED_CSV_SOURCE_FILE_PATH.concat(filename));
            String archiveFileName = filename.concat(".").concat(ProcessingUtils.dateTimeToStringWithourSpaces(new Date())).concat(".bak");
            File destFile = new File(BIILED_CSV_SOURCE_FILE_PATH.concat("archive/").concat(archiveFileName));

            InputStream inStream = new FileInputStream(srcFile);
            OutputStream outStream = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int length;
            //copy file content
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
            inStream.close();
            outStream.close();
            srcFile.delete();
            LOGGER.info("File archived successfully!");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
