package eu.squadd.batch.launchers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameter;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Migration job launcher, it takes 4 parameters: 3 for file names and 1 for current date and time
 * job starts automatically right after application start and gets executed every minute since then until terminated
 * 
 * @author smorcja
 */
@Component
public class BookingsJobLauncher {

    private static final Logger LOGGER = LoggerFactory.getLogger(BookingsJobLauncher.class);

    private final Job job;

    private final JobLauncher jobLauncher;

    @Autowired
    BookingsJobLauncher(@Qualifier("bookingAggregateJob") Job job, JobLauncher jobLauncher) {
        this.job = job;
        this.jobLauncher = jobLauncher;
    }

    @Scheduled(cron = "${csv.to.database.job.cron}")
    void launchCsvFileToDatabaseJob() throws JobParametersInvalidException, JobExecutionAlreadyRunningException, JobRestartException, JobInstanceAlreadyCompleteException {
        LOGGER.info("Starting booking files processing job");
        jobLauncher.run(job, newExecution());
        LOGGER.info("Stopping booking files processing job");
    }

    private JobParameters newExecution() {
        Map<String, JobParameter> parameters = new HashMap<>();
        parameters.put("currentTime", new JobParameter(new Date()));
//        parameters.put("billed_csv_file_name", new JobParameter("billed.csv"));
//        parameters.put("unbilled_csv_file_name", new JobParameter("unbilled.csv"));
//        parameters.put("bookdate_txt_file_name", new JobParameter("bookdate.txt"));
        return new JobParameters(parameters);
    }
}