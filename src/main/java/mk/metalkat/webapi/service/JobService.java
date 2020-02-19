package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.Job;
import mk.metalkat.webapi.models.Task;

import java.util.List;

public interface JobService {

    Job getJob(Long jobId);

    Job saveJob(Job job);

    Job updateJob(Long jobId, Job job);

    Job deleteJob(Long jobId);

    List<Job> getAllJobs();

    Job addTask(Long jobId, Task task);
}
