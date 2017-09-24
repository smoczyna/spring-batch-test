package eu.squadd.batch.launchers;

//import eu.squadd.batch.jobs.DynaScheduleJob;
//import java.util.ArrayList;
//import org.springframework.batch.core.Job;
//import org.springframework.batch.core.launch.JobLauncher;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import java.util.List;
//import java.util.concurrent.ScheduledFuture;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.scheduling.TaskScheduler;
//import org.springframework.scheduling.Trigger;
//import org.springframework.scheduling.support.CronTrigger;

/**
 * this runner is supposed to schedule jobs dynamically but how ???
 * @author smorcja
 */
//@Component
public class DynamicallyScheduledJobLauncher {

//    private final JobLauncher jobLauncher;    
//    private final DynaScheduleJob jobRunner;
//    private final List<ScheduledFuture> scheduledTasks;
//
//    @Value("${csv.to.database.job.cron}") // this doesn't work 
//    private String cronExpression;
//    
//    @Autowired
//    public DynamicallyScheduledJobLauncher(JobLauncher jobLauncher, @Qualifier("billedBookingAggregateJob") Job jobToBeExecuted, TaskScheduler jobScheduler, DynaScheduleJob jobRunner) throws Exception {
//        this.jobLauncher = jobLauncher;
//        this.jobRunner = jobRunner;
//        Trigger trigger = new CronTrigger("0 * * * * *");
//        this.jobRunner.setJob(jobToBeExecuted);
//        this.scheduledTasks = new ArrayList();
//        this.scheduledTasks.add(jobScheduler.schedule((Runnable) jobRunner, trigger)); 
//        
//        // scheduler works but the job is not properly configured    
//        // No job configuration with the name [billedBookingAggregateJob] was registered 
//    }
}
