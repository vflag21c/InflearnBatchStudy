package com.example.hellobatchstudy.listener;

import org.springframework.batch.core.annotation.AfterChunkError;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.core.annotation.BeforeChunk;
import org.springframework.batch.core.annotation.BeforeJob;

public class TotalListener {

    @BeforeJob
    public void beforeJob() {
        System.out.println("job 실행 전");
    }

    @AfterStep
    public void afterStep() {
        System.out.println("Step 실행 후");
    }

//    @AfterChunk //Chunk 주기로 실행 ( 롤백 시 실행 안됨->  @AfterChunkError )
}
