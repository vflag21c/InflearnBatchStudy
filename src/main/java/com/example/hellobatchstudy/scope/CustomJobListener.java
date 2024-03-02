package com.example.hellobatchstudy.scope;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

public class CustomJobListener implements JobExecutionListener {

    @Override
    public void beforeJob(JobExecution jobExecution) {
        jobExecution.getExecutionContext().put("name", "user");
    }

    @Override
    public void afterJob(JobExecution jobExecution) {

    }
}
