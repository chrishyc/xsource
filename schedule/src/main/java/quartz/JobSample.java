package quartz;

import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.impl.StdSchedulerFactory;
import org.quartz.impl.calendar.HolidayCalendar;

import java.util.Date;

import static org.quartz.CronScheduleBuilder.dailyAtHourAndMinute;
import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class JobSample {
    /**
     * 1.jobDetail map
     * 2.trigger map
     * 3.merge map
     * 4.job state
     * 5.triggers
     * 6.Job Stores
     * 7.listener
     */
    public static void main(String[] args) throws InterruptedException {
        try {
            // Grab the Scheduler instance from the Factory
            Scheduler scheduler = StdSchedulerFactory.getDefaultScheduler();

            // and start it off
            scheduler.start();

            // define the job and tie it to our HelloJob class
            JobDetail job = newJob(HelloJob.class)
                    .withIdentity("job1", "group1")
                    .usingJobData("jobSays", "Hello World!")
                    .usingJobData("myFloatValue", 3.141f)
                    .build();

            // Trigger the job to run now, and then repeat every 40 seconds
            Trigger trigger = newTrigger()
                    .withIdentity("trigger1", "group1")
                    .startNow()
                    .withSchedule(simpleSchedule()
                            .withIntervalInSeconds(1)
                            .repeatForever())
                    .build();

            // Tell quartz to schedule the job using our trigger
            scheduler.scheduleJob(job, trigger);
            Thread.sleep(90L * 1000L);
            HolidayCalendar cal = new HolidayCalendar();
            cal.addExcludedDate(new Date());
            cal.addExcludedDate(new Date());

            scheduler.addCalendar("myHolidays", cal, false, false);


            Trigger t = newTrigger()
                    .withIdentity("myTrigger")
                    .forJob("myJob")
                    .withSchedule(dailyAtHourAndMinute(9, 30)) // execute job daily at 9:30
                    .modifiedByCalendar("myHolidays") // but not on holidays
                    .build();

// .. schedule job with trigger

            Trigger t2 = newTrigger()
                    .withIdentity("myTrigger2")
                    .forJob("myJob2")
                    .withSchedule(dailyAtHourAndMinute(11, 30)) // execute job daily at 11:30
                    .modifiedByCalendar("myHolidays") // but not on holidays
                    .build();

            scheduler.shutdown(true);

        } catch (SchedulerException se) {
            se.printStackTrace();
        }

    }
}
