package com.example.hellobatchstudy;

import  org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

//Bean으로 등록해도 무관
//@Component
@StepScope
public class CustomTasklet implements Tasklet {


    @Value("#{jobParameters['name']}")
    private String name;

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        //비즈니스 로직
        System.out.println("step 2 was executed");

        return RepeatStatus.FINISHED;
    }
}
