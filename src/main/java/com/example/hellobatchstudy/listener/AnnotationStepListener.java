package com.example.hellobatchstudy.listener;

import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeStep;

public class AnnotationStepListener {

    @BeforeStep
    public void beforeStep(StepExecution stepExecution){
        System.out.println("@stepExecution.getStepName() : " + stepExecution.getStepName());
    }

    @AfterStep
    public void afterStep(StepExecution stepExecution){
        System.out.println("@stepExecution.getStatus() : " + stepExecution.getStatus());
    }

}
