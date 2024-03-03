package com.example.hellobatchstudy.itemwriter.json;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.json.JacksonJsonObjectMarshaller;
import org.springframework.batch.item.json.JacksonJsonObjectReader;
import org.springframework.batch.item.json.JsonFileItemWriter;
import org.springframework.batch.item.json.builder.JsonFileItemWriterBuilder;
import org.springframework.batch.item.json.builder.JsonItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

//@Configuration
@RequiredArgsConstructor
public class JsonWriterConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final EntityManagerFactory entityManagerFactory;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("JsonWriterBatchJob")
                .incrementer(new RunIdIncrementer())
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() throws Exception {
        return stepBuilderFactory.get("step1")
                .<Customer, Customer>chunk(10)
                .reader(jsonItemReader())
                .processor(new ItemProcessor<Customer, Customer>() {
                    @Override
                    public Customer process(Customer customer) throws Exception {
                        System.out.println("customer = " + customer.toString());

                        return customer;
                    }
                })
                .writer(customItemWriter())
                .build();
    }


    @Bean
    public JsonFileItemWriter<Customer> customItemWriter() {
        return new JsonFileItemWriterBuilder<Customer>()
                .name("customerJsonFileItemWriter")
                .jsonObjectMarshaller(new JacksonJsonObjectMarshaller<>())
                .resource(new FileSystemResource("C:\\Users\\vflag\\IdeaProjects\\HelloBatchStudy\\src\\main\\resources\\output.json"))
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
