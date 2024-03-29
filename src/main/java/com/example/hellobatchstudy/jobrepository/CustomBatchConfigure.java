package com.example.hellobatchstudy.jobrepository;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.repository.support.JobRepositoryFactoryBean;
import org.springframework.batch.core.repository.support.MapJobRepositoryFactoryBean;
import org.springframework.batch.support.transaction.ResourcelessTransactionManager;
import org.springframework.boot.autoconfigure.batch.BasicBatchConfigurer;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.boot.autoconfigure.transaction.TransactionManagerCustomizers;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

//@Configuration
public class CustomBatchConfigure extends BasicBatchConfigurer {

    private final DataSource dataSource;

    protected CustomBatchConfigure(BatchProperties properties, DataSource dataSource, TransactionManagerCustomizers transactionManagerCustomizers) {
        super(properties, dataSource, transactionManagerCustomizers);
        this.dataSource = dataSource;
    }

    @Override
    protected JobRepository createJobRepository() throws Exception {
        JobRepositoryFactoryBean factory = new JobRepositoryFactoryBean();
        factory.setDataSource(dataSource);
        factory.setTransactionManager(getTransactionManager());
        factory.setIsolationLevelForCreate("ISOLATION_SERIALIZABLE"); // isolation 수준, 기본값은 “ISOLATION_SERIALIZABLE”
        factory.setTablePrefix("SYSTEM_");  // 테이블 Prefix, 기본값은 “BATCH_”,
        // BATCH_JOB_EXECUTION 가 SYSTEM_JOB_EXECUTION 으로 변경됨
        // 실제 테이블명이 변경되는 것은 아니다
        factory.setMaxVarCharLength(1000); // varchar 최대 길이(기본값 2500)

        return factory.getObject(); // Proxy 객체가 생성됨 (트랜잭션 Advice 적용 등을 위해 AOP 기술 적용)
    }

    //InMemory DB 사용
//    @Override
//    protected JobRepository createJobRepository(ResourcelessTransactionManager transactionManager) throws Exception {
//        MapJobRepositoryFactoryBean factory = new MapJobRepositoryFactoryBean();
//        factory.setTransactionManager(transactionManager); // ResourcelessTransactionManager 사용
//        return factory.getObject();
//    }

}
