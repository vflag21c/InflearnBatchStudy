package com.example.hellobatchstudy;

import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;

//Bean으로 등록해도 무관
public class CustomTasklet implements Tasklet {

    @Override
    public RepeatStatus execute(StepContribution stepContribution, ChunkContext chunkContext) throws Exception {

        //비즈니스 로직
        System.out.println("step 2 was executed");

        return RepeatStatus.FINISHED;
    }
}
