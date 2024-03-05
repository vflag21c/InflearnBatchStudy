package com.example.hellobatchstudy.thread;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JdbcPagingItemReader;
import org.springframework.batch.item.database.Order;
import org.springframework.batch.item.database.PagingQueryProvider;
import org.springframework.batch.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.TaskExecutor;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

//@Configuration
@RequiredArgsConstructor
public class MultiThreadConfiguration {

    private final JobBuilderFactory jobBuilderFactory;
    private final StepBuilderFactory stepBuilderFactory;
    private final DataSource dataSource;

    @Bean
    public Job job() throws Exception {
        return jobBuilderFactory.get("ThreadBatchJob")
                .incrementer(new RunIdIncrementer())
                .start(threadStep())
                .build();
    }

    @Bean
    public Step threadStep() throws Exception {
        return stepBuilderFactory.get("threadStep")
                .chunk(100)
                .reader(customItemReader())
                .writer(list -> System.out.println("Thread : " + Thread.currentThread().getName() + ", write items : " + list.size()))
                .taskExecutor(taskExecutor())
                .build();
    }

    @Bean
    public JdbcPagingItemReader<Customer> customItemReader() throws Exception {

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("firstName", "A%");

        return new JdbcPagingItemReaderBuilder<Customer>()
                .name("jdbcPagingItemReader")
                .pageSize(10)
                .fetchSize(10)
                .dataSource(dataSource)
                .rowMapper(new BeanPropertyRowMapper<>(Customer.class))
                .queryProvider(queryProvider())
                .parameterValues(parameters)
                .build();
    }

    @Bean
    public PagingQueryProvider queryProvider() throws Exception {

        SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();

        provider.setDataSource(dataSource);
        provider.setSelectClause("id, firstName, lastName, birthdate");
        provider.setFromClause("from customer");
        provider.setWhereClause("where firstName like :firstName");

        Map<String, Order> sortKeys = new HashMap<>();
        sortKeys.put("id", Order.ASCENDING);

        provider.setSortKeys(sortKeys);

        return provider.getObject();
    }


    @Bean
    public ItemProcessor customItemProcessor() {
        return (ItemProcessor<String, String>) item -> {
            Thread.sleep(100);
            return item;
        };
    }


    @Bean
    public TaskExecutor taskExecutor(){
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(4);
        executor.setMaxPoolSize(8);
        executor.setThreadNamePrefix("multi-thread-");
        return executor;
    }
}
