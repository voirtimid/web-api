package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.Job;
import mk.metalkat.webapi.models.Task;
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
}
