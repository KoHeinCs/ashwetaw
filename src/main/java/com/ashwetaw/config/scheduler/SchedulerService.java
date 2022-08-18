package com.ashwetaw.config.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class SchedulerService {
    private final Scheduler scheduler;

    public void schedule(final Class jobClass) {
        final JobDetail jobDetail = QuartzConfig.jobDetail(jobClass);
        final Trigger trigger = QuartzConfig.trigger(jobClass);

        try {
            scheduler.scheduleJob(jobDetail, trigger);
        } catch (SchedulerException exception) {
            log.info(exception.getMessage());
        }
    }

}
