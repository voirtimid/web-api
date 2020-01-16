package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.Job;
import mk.metalkat.webapi.models.Task;
import mk.metalkat.webapi.repository.JobRepository;
import mk.metalkat.webapi.repository.TaskRepository;
import mk.metalkat.webapi.service.JobService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final TaskRepository taskRepository;

    @Override
    public Job getJob(Long jobId) {
        return jobRepository.findById(jobId).orElse(null);
    }

    @Override
    public Job saveJob(Job job) {
        if (job.getJobId() != null) {
            return null;
        }
        return jobRepository.save(job);
    }

    @Override
    public Job updateJob(Long jobId, Job job) {
        if (!jobRepository.findById(jobId).isPresent() || !jobId.equals(job.getJobId())) {
            return null;
        }
        return jobRepository.save(job);
    }

    @Override
    public Job deleteJob(Long jobId) {
        if (!jobRepository.findById(jobId).isPresent()) {
            return null;
        }
        Job job = jobRepository.findById(jobId).get();
        jobRepository.delete(job);
        return job;
    }

    @Override
    public List<Job> getAllJobs() {
        return jobRepository.findAll();
    }

    @Override
    public Job addTask(Long jobId, Long taskId) {
        Optional<Job> optionJob = jobRepository.findById(jobId);
        Optional<Task> optionTask = taskRepository.findById(taskId);

        if (optionJob.isPresent() && optionTask.isPresent()) {
            Job job = optionJob.get();
            Task task = optionTask.get();
            if (job.addTask(task)) {
                task.setJob(job);
                taskRepository.save(task);
                jobRepository.save(job);
                return job;
            }
        }
        return null;
    }
}
