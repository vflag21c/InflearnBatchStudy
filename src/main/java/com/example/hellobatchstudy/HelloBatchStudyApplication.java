package com.example.hellobatchstudy;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.task.configuration.EnableTask;

@EnableBatchProcessing
@SpringBootApplication
@EnableTask
public class HelloBatchStudyApplication {

    public static void main(String[] args) {
        SpringApplication.run(HelloBatchStudyApplication.class, args);
    }

}
