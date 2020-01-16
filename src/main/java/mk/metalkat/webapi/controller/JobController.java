package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.Job;
import mk.metalkat.webapi.service.JobService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/jobs")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @PostMapping
    public Job createJob(@RequestBody Job job) {
        return jobService.saveJob(job);
    }

    @GetMapping(value = "/{jobId}")
    public Job getJobById(@PathVariable("jobId") Long jobId) {
        return jobService.getJob(jobId);
    }

    @PutMapping(value = "/{jobId}")
    public Job updateJob(@PathVariable("jobId") Long jobId, @RequestBody Job job) {
        return jobService.updateJob(jobId, job);
    }

    @DeleteMapping(value = "/{jobId}")
    public Job deleteJob(@PathVariable("jobId") Long jobId) {
        return jobService.deleteJob(jobId);
    }

    @PutMapping(value = "/{jobId}/addTask/{taskId}")
    public Job addTaskToJob(@PathVariable("jobId") Long jobId, @PathVariable("taskId") Long taskId) {
        return jobService.addTask(jobId, taskId);
    }
}
