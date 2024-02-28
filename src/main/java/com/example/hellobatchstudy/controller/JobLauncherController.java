package com.example.hellobatchstudy.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.support.SimpleJobLauncher;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.core.task.SimpleAsyncTaskExecutor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequiredArgsConstructor
public class JobLauncherController {

    private final Job job;
    private final BasicBatchConfigurer basicBatchConfigurer;

    @PostMapping("/batch")
    public String launch(@RequestBody Member member) throws Exception{

        JobParameters jobParameters = new JobParametersBuilder()
                .addString("id", member.getId())
                .addDate("date", new Date())
                .toJobParameters();

        SimpleJobLauncher simpleJobLauncher = (SimpleJobLauncher) basicBatchConfigurer.getJobLauncher(); // 동기적 실행
        simpleJobLauncher.setTaskExecutor(new SimpleAsyncTaskExecutor()); // 비동기적 실행

        simpleJobLauncher.run(job, jobParameters);
        System.out.println("Job 실행 완료");

        return "batch completed";
    }
}
