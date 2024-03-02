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

## *주의
 
-  `RunIdIncrementer()` 사용 시, 이전 Job 실행 시 사용한 파라미터 중 하나가 누락되어도 여전히 파라미터는 재사용 됨

### 최초 실행
```
--job.name=batchJob date=20240302 v=1
```
### 파라미터 제거 후 실행
```
job.name=batchJob date=20240302
```
### 결과
> launched with the following parameters: [{date=20240302, run.id=2, v=1}]

