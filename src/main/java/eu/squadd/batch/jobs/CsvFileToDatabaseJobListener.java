/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.squadd.batch.jobs;

import eu.squadd.batch.util.DateTimeUtils;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
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
public class CsvFileToDatabaseJobListener implements JobExecutionListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CsvFileToDatabaseJobListener.class);

    @Value("${csv.to.database.job.source.file.path}")
    private String PROPERTY_CSV_SOURCE_FILE_PATH;

    @Override
    public void beforeJob(JobExecution je) {
        LOGGER.info("Check if file exists and stop the job if not");
        File f = new File(PROPERTY_CSV_SOURCE_FILE_PATH.concat("students.csv"));
        if (!f.exists() || f.isDirectory()) {
            try {
                LOGGER.error("File not found, aborting job ...");
                throw new FileNotFoundException();
            } catch (FileNotFoundException ex) {
                LOGGER.error(ex.getLocalizedMessage(), Level.SEVERE, null, ex);
            }
        }
    }

    @Override
    public void afterJob(JobExecution je) {
        if (je.getStatus() == BatchStatus.COMPLETED) {
            this.movFileToArchive();
        } else {
            System.out.println("After job fisnishes with failure listener can dislay all exceptions:");
            List<Throwable> exceptionList = je.getAllFailureExceptions();
            for (Throwable th : exceptionList) {
                System.err.println("exception :" + th.getLocalizedMessage());
            }
        }

    }

    private void movFileToArchive() {
        InputStream inStream = null;
        OutputStream outStream = null;
        try {
            File srcFile = new File(PROPERTY_CSV_SOURCE_FILE_PATH.concat("students.csv"));
            File destFile = new File(PROPERTY_CSV_SOURCE_FILE_PATH.concat("archive/students.csv").concat(DateTimeUtils.dateToString(new Date(), DateTimeUtils.SHORTDATETIME_FORMAT)));

            inStream = new FileInputStream(srcFile);
            outStream = new FileOutputStream(destFile);
            byte[] buffer = new byte[1024];
            int length;
            //copy the file content in bytes
            while ((length = inStream.read(buffer)) > 0) {
                outStream.write(buffer, 0, length);
            }
            inStream.close();
            outStream.close();
            srcFile.delete();
            LOGGER.info("File moved successful!");
        } catch (IOException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
