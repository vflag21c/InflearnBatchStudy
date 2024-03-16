package com.example.hellobatchstudy.joboperator;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobInstance;
import org.springframework.batch.core.configuration.JobRegistry;
import org.springframework.batch.core.explore.JobExplorer;
import org.springframework.batch.core.job.SimpleJob;
import org.springframework.batch.core.launch.JobExecutionNotRunningException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.NoSuchJobException;
import org.springframework.batch.core.launch.NoSuchJobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Iterator;
import java.util.Set;

@RequiredArgsConstructor
@Controller
public class JobController {

    private final JobRegistry jobRegistry;
    private final JobOperator jobOperator;
    private final JobExplorer jobExplorer;

    @PostMapping(value = "/batch/start")
    public String start(@RequestBody JobInfo jobInfo) throws Exception {

        for(Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext();) {
            SimpleJob job = (SimpleJob)jobRegistry.getJob(iterator.next());
            System.out.println("jobName = " + job.getName());

            jobOperator.start(job.getName(), "id" + jobInfo.getId());
        }

        return "batch is started";
    }

    @PostMapping(name = "/batch/restart")
    public String restart() throws Exception {

        for(Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext();) {
            SimpleJob job = (SimpleJob)jobRegistry.getJob(iterator.next());
            System.out.println("jobName = " + job.getName());

            JobInstance lastJobInstance = jobExplorer.getLastJobInstance(job.getName());
            JobExecution lastJobExecution = jobExplorer.getLastJobExecution(lastJobInstance);
            jobOperator.restart(lastJobExecution.getId());
        }

        return "batch is restarted";
    }

    @PostMapping(name = "/batch/stop")
    public String stop() throws NoSuchJobException, NoSuchJobExecutionException, JobExecutionNotRunningException {
        for(Iterator<String> iterator = jobRegistry.getJobNames().iterator(); iterator.hasNext();) {
            SimpleJob job = (SimpleJob)jobRegistry.getJob(iterator.next());
            System.out.println("jobName = " + job.getName());

            Set<JobExecution> runningJobExecutions = jobExplorer.findRunningJobExecutions(job.getName());
            JobExecution jobExecution =  runningJobExecutions.iterator().next();

            jobOperator.stop(jobExecution.getId());
        }

        return "batch is stoped";
    }

}
