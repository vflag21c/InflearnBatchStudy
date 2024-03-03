package com.example.hellobatchstudy.itemstream;

import com.example.hellobatchstudy.itemreader_itemprocessor_itemwriter.CustomItemWriter;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemStreamReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

//@Configuration
@RequiredArgsConstructor
public class ItemStreamConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("StreamBatchJob")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<String, String>chunk(5)
                .reader(itemReader())
                .writer(itemWriter())
                .build();
    }

    @Bean
    public ItemStreamReader itemReader() {
        List<String> items = new ArrayList<>(10);
        for(int i=0; i<=10; i++) {
            items.add(String.valueOf(i));
        }
        return new CustomItemStreamReader(items);
    }

    public ItemWriter itemWriter() {
        return new CustomItemWriter();
    }

}
