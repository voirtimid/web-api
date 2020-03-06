package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.jpa.Job;
import mk.metalkat.webapi.models.jpa.Task;
import mk.metalkat.webapi.models.dto.JobDTO;
import mk.metalkat.webapi.service.JobService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/jobs")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "http://192.168.43.211:3000"})
public class JobController {

    private final JobService jobService;

    @GetMapping
    public List<Job> getAllJobs() {
        return jobService.getAllJobs();
    }

    @GetMapping("/paged")
    public Page<Job> getAllJobsPaged(@RequestHeader(name = "page", defaultValue = "0", required = false) int page,
                                     @RequestHeader(name = "size", defaultValue = "10", required = false) int size) {
        return jobService.getAllJobsPaged(page, size);
    }

    @GetMapping("/history/paged")
    public Page<Job> getAllJobsHistoryPaged(@RequestHeader(name = "page", defaultValue = "0", required = false) int page,
                                     @RequestHeader(name = "size", defaultValue = "10", required = false) int size) {
        return jobService.getAllJobsHistoryPaged(page, size);
    }

    @GetMapping("/status/{isFinished}")
    public List<Job> getJobsFilteredByStatus(@PathVariable("isFinished") boolean isFinished) {
        if (isFinished) {
            return jobService.getAllFinishedJobs();
        } else {
            return jobService.getAllWaitingJobs();
        }
    }

    @PostMapping
    public Job createJob(@RequestBody JobDTO jobDTO) {
        return jobService.saveJob(jobDTO);
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

    @PutMapping(value = "/{jobId}/addTask")
    public Job addTaskToJob(@PathVariable("jobId") Long jobId, @RequestBody Task task) {
        return jobService.addTask(jobId, task);
    }

    @GetMapping(value = "/{jobId}/tasks")
    public List<Task> getAllTaskForJob(@PathVariable("jobId") Long jobId) {
        return jobService.getTaskForJob(jobId);
    }

    @GetMapping(value = "/updateDates/{jobId}")
    public Job updateDates(@PathVariable("jobId") Long jobId) {
        return jobService.updateStartAndEndDate(jobId);
    }

    @GetMapping(value = "/updateRealDates/{jobId}")
    public Job updateRealDates(@PathVariable("jobId") Long jobId) {
        return jobService.updateActualDates(jobId);
    }

    @GetMapping(value = "/getJobsFor/{drawing}")
    public List<Job> getJobsWithSketch(@PathVariable("drawing") String drawing) {
        return jobService.getJobsWithSketch(drawing);
    }

    @GetMapping(value = "/{jobId}/complete")
    public Job completeJob(@PathVariable("jobId") Long jobId) {
        return jobService.completeJob(jobId);
    }

}
