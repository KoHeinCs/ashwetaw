package com.ashwetaw.config.listener;

import com.ashwetaw.config.scheduler.EmailJob;
import com.ashwetaw.config.scheduler.SchedulerService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class SpringAngularJWTApplicationDeployListener implements ApplicationListener<ApplicationReadyEvent> {
    private final SchedulerService schedulerService;
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {
        log.info("****************** loading every  application start ************** ");
        schedulerService.schedule(EmailJob.class);
    }


}
