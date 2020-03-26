package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.dto.FilterJobDTO;
import mk.metalkat.webapi.models.dto.JobDTO;
import mk.metalkat.webapi.models.dto.LocalDateDTO;
import mk.metalkat.webapi.models.jpa.Job;
import mk.metalkat.webapi.models.jpa.Task;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.List;

public interface JobService {

    Job getJob(Long jobId);

    Job saveJob(JobDTO jobDTO);

    Job updateJob(Long jobId, Job job);

    Job deleteJob(Long jobId);

    List<Job> getAllJobs();

    Page<Job> getAllJobsPaged(int page, int size);

    Page<Job> getAllJobsHistoryPaged(int page, int size);

    Page<Job> getAllJobsBetweenDates(LocalDate from, LocalDate to);

    Job addTask(Long jobId, Task task);

    List<Task> getTaskForJob(Long jobId);

    List<Job> getAllFinishedJobs();

    List<Job> getAllWaitingJobs();

    Job updateStartAndEndDate(Long jobId);

    Job updateActualDates(Long jobId);

    List<Job> getJobsWithSketch(String drawing);

    Job completeJob(Long jobId);

    List<Job> getAllFilteredJobs(FilterJobDTO filterJobDTO);
    List<Job> getAllJobsCreatedBetween(LocalDate from, LocalDate to);

    List<Job> getAllJobsFinishedBetween(LocalDate from, LocalDate to);
}
