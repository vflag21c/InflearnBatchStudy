## @SpringBatchTest
> 자동으로 ApplicationContext 에 테스트에 필요한 여러 유틸 Bean 을 등록해 주는 어노테이션
- JobLauncherTestUtils
  - `launchJob()`, `launchStep()` 과 같은 스프링 배치 테스트에 필요한 유틸성 메소드 지원
- JobRepositoryTestUtils
  - JobRepository 를 사용해서 JobExecution 을 생성 및 삭제 기능 메소드 지원
- StepScopeTestExecutionListener
  - `@StepScope` 컨텍스트를 생성해 주며 해당 컨텍스트를 통해 JobParameter 등을 단위 테스트에서 DI 받을 수 있다
- JobScopeTestExecutionListener
  - `@JobScope` 컨텍스트를 생성해 주며 해당 컨텍스트를 통해 JobParameter 등을 단위 테스트에서 DI 받을 수 있다
 





## JobExplorer

- JobRepository 의 readonly 버전
- 실행 중인 Job 의 실행 정보인 JobExecution 또는 Step 의 실행 정보인 StepExecution 을 조회할 수 있다

## JobRegistry

- 생성된 Job 을 자동으로 등록, 추적 및 관리하며 여러 곳에서 Job 을 생성한 경우 ApplicationContext 에서 Job 을 수집해서 사용할 수 있다
- 기본 구현체로 Map 기반의 MapJobRegistry 클래스를 제공
  - JobName 을 Key 로 하고 Job 을 값으로 하여 매핑
- Job 등록
  - JobRegistryBeanPostProcessor - BeanPostProcessor 단계에서 Bean 초기화 시 자동으로 JobRegistry 에 Job 을 등록 시켜준다

## JobOperator

- JobExplorer, JobRepository, JobRegistry, JobLauncher 를 포함하고 있으며, 배치의 중단, 재시작, Job 요약 등의 모니터링이 가능하다
