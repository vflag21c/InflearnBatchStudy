package com.example.hellobatchstudy.itemwriter.jdbc;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.sql.DataSource;

//@Configuration
@RequiredArgsConstructor
public class JdbcBatchWriterConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("JdbcBatchWriterJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<Customer, Customer>chunk(100)
                .reader(jsonItemReader())
                .writer(customItemWriter())
                .build();
    }

    @Bean
    public JdbcBatchItemWriter<Customer> customItemWriter() {
        return new JdbcBatchItemWriterBuilder<Customer>()
                .dataSource(dataSource)
                .sql("insert into customer2 values (:id, :age, :name)")
                .beanMapped()
                .build();
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
