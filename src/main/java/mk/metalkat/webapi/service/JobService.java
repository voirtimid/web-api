package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.jpa.Job;
import mk.metalkat.webapi.models.jpa.Task;
import mk.metalkat.webapi.models.dto.JobDTO;

import java.util.List;

public interface JobService {

    Job getJob(Long jobId);

    Job saveJob(JobDTO jobDTO);

    Job updateJob(Long jobId, Job job);

    Job deleteJob(Long jobId);

    List<Job> getAllJobs();

    Job addTask(Long jobId, Task task);

    List<Task> getTaskForJob(Long jobId);

    List<Job> getAllFinishedJobs();

    List<Job> getAllWaitingJobs();

    Job updateStartAndEndDate(Long jobId);
}
