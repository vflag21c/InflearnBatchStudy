package com.example.hellobatchstudy.listener;


import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class CustomJobListener implements JobExecutionListener {
    @Override
    public void beforeJob(JobExecution jobExecution) {
        System.out.println("before Job");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {
        System.out.println("after Job");
    }
}
