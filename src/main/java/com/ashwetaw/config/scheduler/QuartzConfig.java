package com.ashwetaw.config.scheduler;

import org.quartz.*;

import java.util.Date;

public class QuartzConfig {


    public static JobDetail jobDetail(final Class jobClass) {
        final JobDataMap jobDataMap = new JobDataMap();

        return JobBuilder
                .newJob(jobClass)
                .withIdentity(jobClass.getSimpleName())
                .setJobData(jobDataMap)
                .build();

    }


    public static Trigger trigger(final Class jobClass) {
        /** https://www.freeformatter.com/cron-expression-generator-quartz.html **/
        CronScheduleBuilder cron = CronScheduleBuilder.cronSchedule("0 0 * ? * *");

        /**
        SimpleScheduleBuilder builder = SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInHours(1)
                .repeatForever();
         **/

        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(cron)
                //.withSchedule(builder)
                .startAt(new Date())
                .build();
    }


}
