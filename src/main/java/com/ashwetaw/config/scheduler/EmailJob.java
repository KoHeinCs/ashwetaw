package com.ashwetaw.config.scheduler;

import com.ashwetaw.email.EmailService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailJob implements Job {
    private final EmailService emailService;


    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        log.info("Sending student email every hour");
        emailService.sendStudentsEmail("heinhtetaungcu@gmail.com");

    }
}
