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
        SimpleScheduleBuilder builder = SimpleScheduleBuilder.simpleSchedule().withIntervalInHours(1);
        builder.repeatForever();

        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobClass.getSimpleName())
                .withSchedule(builder)
                .startAt(new Date())
                .build();
    }


}
