## 기본개념

- JobParameters 에서 필요한 값을 증가시켜 다음에 사용될 JobParameters 리턴
- 기존의 JobParameter 변경없이 Job 을 여러번 시작 하고자 함
- **RunIdIncrementer** 구현체를 지원하며 직접 인터페이스를 구현 할 수 잇음

```java
public Job batchJob() {
    return jobBuilderFactory.get("batchjob")
            .start()
            .next()
            .incrementer(JobParametersIncrementer)
            .validator()
            .preventRestart()
            .listener()
            .build();
}
```