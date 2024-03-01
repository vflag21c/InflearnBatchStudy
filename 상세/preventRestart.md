## 기본개념

- Job 의 재시작 여부를 설정
- 기본값은 true 이며 false 로 설정 시, **Job 재시작을 지원하지 않음**
- Job 이 실패해도 재 시작이 안되며, 재시작하려 하면 JobRestartException 발생

```java
public Job batchJob(){
    return jobBuilderFactory.get("batchjob")
            .start()
            .next()
            .incrementer()
            .validator()
            .preventRestart() // 재시작 하지 않음
            .listener()
            .build();
}

```