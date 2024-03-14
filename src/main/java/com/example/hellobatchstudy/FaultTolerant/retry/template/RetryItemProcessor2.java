package com.example.hellobatchstudy.FaultTolerant.retry.template;

import com.example.hellobatchstudy.FaultTolerant.retry.RetryableException;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.classify.BinaryExceptionClassifier;
import org.springframework.classify.Classifier;
import org.springframework.retry.RecoveryCallback;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.RetryContext;
import org.springframework.retry.support.DefaultRetryState;
import org.springframework.retry.support.RetryTemplate;

public class RetryItemProcessor2 implements ItemProcessor<String, Customer> {

    @Autowired
    private RetryTemplate retryTemplate;

    @Override
    public Customer process(String item) throws Exception {

        Customer result = retryTemplate.execute(new RetryCallback<Customer, RuntimeException>() {
            @Override
            public Customer doWithRetry(RetryContext context) throws RuntimeException {
                // 설정된 조건 및 횟수만큼 재시도 수행
                if (item.equals("1") || item.equals("2")) {
                    throw new RetryableException("failed");
                }
                return new Customer(item);
            }
        }, new RecoveryCallback<Customer>() {
            @Override
            public Customer recover(RetryContext context) throws Exception {
                // 재시도가 모두 소진되었을 때 수행
                return new Customer(item);
            }
        });
        //template - state 추가, skip 추가, backoff 추가,
        return result;
    }
}
