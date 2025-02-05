package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.exceptions.ModelNotFoundException;
import mk.metalkat.webapi.models.dto.FilterJobDTO;
import mk.metalkat.webapi.models.enums.Status;
import mk.metalkat.webapi.models.jpa.Job;
import mk.metalkat.webapi.models.jpa.Sketch;
import mk.metalkat.webapi.models.jpa.Task;
import mk.metalkat.webapi.models.dto.JobDTO;
import mk.metalkat.webapi.repository.JobRepository;
import mk.metalkat.webapi.repository.SketchRepository;
import mk.metalkat.webapi.repository.TaskRepository;
import mk.metalkat.webapi.service.JobService;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;

    private final TaskRepository taskRepository;

    private final SketchRepository sketchRepository;

    @Override
    public Job getJob(Long jobId) {
        return jobRepository.findById(jobId).orElse(null);
    }

    @Override
    public Job saveJob(JobDTO jobDTO) {
        Job job = jobDTO.getJob();
        if (job.getJobId() != null) {
            throw new ModelNotFoundException("Job already exists");
        }

        Sketch sketch = sketchRepository.findById(jobDTO.getSketchId()).orElseThrow(() -> new ModelNotFoundException("Sketch does not exist"));
        job.setSketch(sketch);
        job.setJobCreated(LocalDate.now());
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
    public Page<Job> getAllJobsPaged(int page, int size) {
        return jobRepository.findAll(PageRequest.of(page, size, Sort.by("plannedStartDate").ascending()));
    }

    @Override
    public Page<Job> getAllInProgressJobsPaged(int page, int size) {
        return jobRepository.findAllByIsFinishedIs(false, PageRequest.of(page, size, Sort.by("plannedStartDate").ascending()));
    }

    @Override
    public Page<Job> getAllJobsHistoryPaged(int page, int size) {
        return jobRepository.findAllByIsFinishedIs(true, PageRequest.of(page, size, Sort.by("jobFinished").descending()));
    }

    @Override
    public Job addTask(Long jobId, Task task) {
        Optional<Job> optionJob = jobRepository.findById(jobId);
        Optional<Task> optionTask = taskRepository.findById(task.getTaskId());

        if (optionJob.isPresent() && optionTask.isPresent()) {
            Job job = optionJob.get();
            Task newTask = optionTask.get();
            if (job.addTask(newTask)) {
                job.setPlannedStartDate(task.getPlannedStartDate());
                job.setPlannedEndDate(task.getPlannedEndDate());

                Double jobTotalMinutesForPiece = job.getTasks().stream().filter(task1 -> !task1.getTaskId().equals(task.getTaskId())).map(Task::getMinutesForPiece).reduce((Double::sum)).orElse(0d);
                Double jobTotalWorkTime = job.getTasks().stream().filter(task1 -> !task1.getTaskId().equals(task.getTaskId())).map(Task::getPlannedHours).reduce(Double::sum).orElse(0d);

                job.setPlannedTimeForPiece(jobTotalMinutesForPiece + task.getMinutesForPiece());
                job.setPlannedHours(jobTotalWorkTime + task.getPlannedHours());

                if (job.getTasks().stream().anyMatch(task1 -> task1.getStatus().equals(Status.BEHIND))) {
                    job.setStatus(Status.BEHIND);
                } else if (job.getTasks().stream().anyMatch(task1 -> task1.getStatus().equals(Status.TODAY))) {
                    job.setStatus(Status.TODAY);
                }

                return jobRepository.save(job);
            }
        }
        return null;
    }

    @Override
    public List<Task> getTaskForJob(Long jobId) {
        return taskRepository.findAll().stream()
                .filter(task -> task.getJob().getJobId().equals(jobId)).collect(Collectors.toList());
    }

    @Override
    public List<Job> getAllFinishedJobs() {
        return jobRepository.findAll().stream().filter(Job::isFinished).collect(Collectors.toList());
    }

    @Override
    public List<Job> getAllWaitingJobs() {
        return jobRepository.findAll().stream().filter(job -> !job.isFinished()).collect(Collectors.toList());
    }

    @Override
    public Job updateStartAndEndDate(Long jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow(() ->  new ModelNotFoundException("This job does not exist!"));
        List<Task> tasks = job.getTasks();
        if (tasks.isEmpty()) {
            return job;
        }

        LocalDate startDate = tasks.stream()
                .map(Task::getPlannedStartDate)
                .min(Comparator.naturalOrder()).orElse(LocalDate.now());

        LocalDate endDate = tasks.stream()
                .map(Task::getPlannedEndDate)
                .max(Comparator.naturalOrder()).orElse(LocalDate.now());

        job.setPlannedStartDate(startDate);
        job.setPlannedEndDate(endDate);

        Job updatedJob = Job.updateJobStatus(job);
        return jobRepository.save(updatedJob);
    }

    @Override
    public Job updateActualDates(Long jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow(() ->  new ModelNotFoundException("This job does not exist!"));
        List<Task> tasks = job.getTasks();
        if (tasks.isEmpty()) {
            return job;
        }

        LocalDate startDate = tasks.stream()
                .filter(task -> task.getRealStartDate() != null)
                .map(Task::getRealStartDate)
                .min(Comparator.naturalOrder()).orElse(null);

        LocalDate endDate = tasks.stream()
                .filter(task -> task.getRealEndDate() != null)
                .map(Task::getRealEndDate)
                .max(Comparator.naturalOrder()).orElse(null);

        job.setRealStartDate(startDate);
        job.setRealEndDate(endDate);


        Job updatedJob = Job.updateJobStatus(job);
        return jobRepository.saveAndFlush(updatedJob);
    }

    @Override
    public List<Job> getJobsWithSketch(String drawing) {
        return jobRepository.findAll().stream()
                .filter(Job::isFinished)
                .filter(job -> job.getSketch().getDrawing().equals(drawing))
                .limit(5)
                .sorted(Comparator.comparing(Job::getJobCreated).reversed())
                .collect(Collectors.toList());
    }

    @Override
    public Job completeJob(Long jobId) {
        Job job = jobRepository.findById(jobId).orElseThrow(() ->  new ModelNotFoundException("This job does not exist!"));
        if (job.getTasks().stream().allMatch(Task::isFinished)) {
            job.setJobFinished(LocalDate.now());
            job.setFinished(true);
            job.setStatus(Status.FINISHED);
            return jobRepository.saveAndFlush(job);
        }
        return job;
    }

    @Override
    public List<Job> getAllFilteredJobs(FilterJobDTO filterJobDTO) {
        LocalDate from = filterJobDTO.getStartDate();
        LocalDate to = filterJobDTO.getEndDate();
        String attribute = filterJobDTO.getForWhat();
        String sketchName = filterJobDTO.getSketchName();

        List<Job> result = new ArrayList<>();
        if (attribute.equals("created")) {
            if (!sketchName.equals("")) {
                result = getAllJobsCreatedBetween(from, to).stream()
                        .filter(job -> job.getSketch().getSketchName().toLowerCase().contains(sketchName.toLowerCase()))
                        .collect(Collectors.toList());
            } else {
                result = getAllJobsCreatedBetween(from, to);
            }
        } else if (attribute.equals("finished")) {
            if (!sketchName.equals("")) {
                result = getAllJobsFinishedBetween(from, to).stream()
                        .filter(job -> job.getSketch().getSketchName().toLowerCase().contains(sketchName.toLowerCase()))
                        .collect(Collectors.toList());
            } else {
                result = getAllJobsFinishedBetween(from, to);
            }
        }
        return result;
    }

    @Override
    public List<Job> getAllJobsCreatedBetween(LocalDate from, LocalDate to) {
        return jobRepository.findAll().stream()
                .filter(Job::isFinished)
                .filter(job -> (job.getJobCreated().isAfter(from) || job.getJobCreated().isEqual(from)) && (job.getJobCreated().isBefore(to) || job.getJobCreated().isEqual(to)))
                .collect(Collectors.toList());
    }

    @Override
    public List<Job> getAllJobsFinishedBetween(LocalDate from, LocalDate to) {
        return jobRepository.findAll().stream()
                .filter(Job::isFinished)
                .filter(job -> (job.getJobFinished().isAfter(from) || job.getJobFinished().isEqual(from)) && (job.getJobFinished().isBefore(to) || job.getJobFinished().isEqual(to)))
                .collect(Collectors.toList());
    }
}
