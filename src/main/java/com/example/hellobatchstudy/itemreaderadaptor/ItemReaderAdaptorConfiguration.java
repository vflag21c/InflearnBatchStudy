package com.example.hellobatchstudy.itemreaderadaptor;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.adapter.ItemReaderAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


//@Configuration
@RequiredArgsConstructor
public class ItemReaderAdaptorConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("ItemReaderAdaptorBatchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<String,String>chunk(10)
                .reader(customItemReader())
                .writer(customItemWriter())
                .build();
    }

    @Bean
    public ItemReaderAdapter customItemReader() {
        ItemReaderAdapter itemReaderAdapter = new ItemReaderAdapter();

        itemReaderAdapter.setTargetObject(customService()); // 레거시 시스템의 DAO 및 Service 연동
        itemReaderAdapter.setTargetMethod("joinCustomer");

        return itemReaderAdapter;
    }

    private CustomService<String> customService() {
        return new CustomService<>();
    }

    @Bean
    public ItemWriter<String> customItemWriter() {
        return items -> {
            System.out.println(items);
        };
    }
}
