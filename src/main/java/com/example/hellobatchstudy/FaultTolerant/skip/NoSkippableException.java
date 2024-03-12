package com.example.hellobatchstudy.FaultTolerant.skip;

public class NoSkippableException extends Exception {
    public NoSkippableException(String s) {
        super(s);
    }
}
