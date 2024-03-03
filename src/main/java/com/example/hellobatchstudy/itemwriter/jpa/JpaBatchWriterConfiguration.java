package com.example.hellobatchstudy.itemwriter.jpa;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;

//@Configuration
@RequiredArgsConstructor
public class JpaBatchWriterConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("jpaBatchWriterJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<Customer, Customer2>chunk(10)
                .reader(jsonItemReader())
                .processor(customItemProcess())
                .writer(customItemWriter())
                .build();
    }

    @Bean
    public JpaItemWriter<Customer2> customItemWriter() {
        return new JpaItemWriterBuilder<Customer2>()
                .entityManagerFactory(entityManagerFactory)
                .usePersist(true)
                .build();
    }

    @Bean
    public ItemProcessor<? super Customer, ? extends Customer2> customItemProcess() {
        return new CustomItemProcess();
    }

    @Bean
    public ItemReader<? extends Customer> jsonItemReader() {
        return new JsonItemReaderBuilder<Customer>()
                .name("jsonReader")
                .jsonObjectReader(new JacksonJsonObjectReader<>(Customer.class))
                .resource(new ClassPathResource("customer.json"))
                .build();
    }
}
