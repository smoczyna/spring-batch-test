package eu.squadd.batch.launchers;

import eu.squadd.batch.jobs.DynaScheduleJob;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.ScheduledFuture;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.Trigger;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;
import org.springframework.scheduling.support.CronTrigger;

/**
 * this runner is supposed to schedule jobs dynamically but how ???
 * @author smorcja
 */
@Component
public class DynamicallyScheduledJobLauncher {

    private final JobLauncher jobLauncher;
    private final ThreadPoolTaskScheduler  threadPoolTaskScheduler;
    private final DynaScheduleJob jobRunner;
    private final List<ScheduledFuture> scheduledTasks;

    @Value("${csv.to.database.job.cron}")
    private String cronExpression;
    
    @Autowired
    public DynamicallyScheduledJobLauncher(JobLauncher jobLauncher, Job jobToBeExecuted, ThreadPoolTaskScheduler  threadPoolTaskScheduler, DynaScheduleJob jobRunner) throws Exception {
        this.jobLauncher = jobLauncher;
        this.threadPoolTaskScheduler = threadPoolTaskScheduler;
        this.jobRunner = jobRunner;
        Trigger trigger = new CronTrigger(cronExpression);
        this.jobRunner.setJob(jobToBeExecuted);
        this.scheduledTasks = new ArrayList();
        this.scheduledTasks.add(this.threadPoolTaskScheduler.schedule((Runnable) jobRunner, trigger));
    }
}
