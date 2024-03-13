package com.example.hellobatchstudy.FaultTolerant.retry;

public class RetryableException extends RuntimeException {

    public RetryableException() {
        super();
    }

    public RetryableException(String message) {
        super(message);
    }

}
