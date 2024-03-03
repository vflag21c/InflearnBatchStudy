package com.example.hellobatchstudy.itemreader.flatfile;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.util.List;

//@Configuration
@RequiredArgsConstructor
public class FlatFilesConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;

    @Bean
    public Job job() {
        return jobBuilderFactory.get("FlatFileBatchJob")
                .start(step1())
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<Customer, Customer>chunk(5)
                .reader(itemReader())
                .writer(new ItemWriter() {
                    @Override
                    public void write(List list) throws Exception {
                        List<Customer> customer = list;
                        System.out.println("list = " + list);
                    }
                })
                .build();
    }


    /**
     * 직접 구현.
     *
     */
//    @Bean
    public ItemReader itemReader_직접구현() {
        FlatFileItemReader<Customer> itemReadr = new FlatFileItemReader<>();
        itemReadr.setResource(new ClassPathResource("/customer.csv"));

        DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();
        lineMapper.setTokenizer(new DelimitedLineTokenizer());
        lineMapper.setFieldSetMapper(new CustomerFieldSetMapper());

        itemReadr.setLineMapper(lineMapper);
        itemReadr.setLinesToSkip(1);

        return itemReadr;
    }

    /**
     * Spring Batch 제공 클래스
     *
     */
//    @Bean
    public ItemReader itemReader_Default() {
        return new FlatFileItemReaderBuilder<Customer>()
                .name("flatFile")
                .resource(new ClassPathResource("/customer.csv"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(Customer.class)
                .linesToSkip(1)
                .delimited().delimiter(",")
                .names("name", "age", "year")
//                .strict(false) // parsing 예외
                .build();
    }

    /**
     * FixedLength
     */
    @Bean
    public ItemReader itemReader() {
        return new FlatFileItemReaderBuilder<>()
                .name("flatFile")
                .resource(new ClassPathResource("/customer.txt"))
                .fieldSetMapper(new BeanWrapperFieldSetMapper<>())
                .targetType(Customer.class)
                .linesToSkip(1)
                .fixedLength()
                .addColumns(new Range(1,5))
                .addColumns(new Range(6,9))
                .addColumns(new Range(10,11))
                .names("name","year","age")
                .build();
    }
}
