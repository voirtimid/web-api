package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.enums.Status;
import mk.metalkat.webapi.models.jpa.Job;
import mk.metalkat.webapi.models.jpa.Task;
import mk.metalkat.webapi.repository.JobRepository;
import mk.metalkat.webapi.repository.TaskRepository;
import mk.metalkat.webapi.service.UpdaterScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class UpdaterSchedulerImpl implements UpdaterScheduler {

    private final TaskRepository taskRepository;

    private final JobRepository jobRepository;

    @Scheduled(cron = "0 10 0 * * *")
    @PostConstruct
    public void updateStatus() {

        List<Task> tasks = taskRepository.findAll().stream()
                .filter(task -> !task.isFinished())
                .collect(Collectors.toList());

        tasks.stream()
                .map(this::updateTaskStatus).forEach(taskRepository::save);

        System.out.println("Updating tasks");

        jobRepository.findAll().stream()
                .filter(job -> !job.isFinished())
                .map(this::updateJobStatus)
                .forEach(jobRepository::save);

        System.out.println("Updating orders");

    }

    private Job updateJobStatus(Job job) {
        if (job.getTasks().stream().anyMatch(task1 -> task1.getStatus().equals(Status.BEHIND))) {
            job.setStatus(Status.BEHIND);
        } else if (job.getTasks().stream().anyMatch(task1 -> task1.getStatus().equals(Status.TODAY))) {
            job.setStatus(Status.TODAY);
        } else {
            job.setStatus(Status.NORMAL);
        }
        return job;
    }

    private Task updateTaskStatus(Task task) {
        LocalDate plannedStartDate = task.getPlannedStartDate();
        LocalDate now = LocalDate.now();

        if (task.getRealEndDate() == null) {
            if (task.getRealStartDate() != null) {
                LocalDate plannedEndDate = task.getPlannedEndDate();
                if (!plannedEndDate.isBefore(now)) {
                    task.setStatus(Status.NORMAL);
                } else {
                    task.setStatus(Status.BEHIND);
                }
            } else {
                if (plannedStartDate.isBefore(now)) {
                    task.setStatus(Status.BEHIND);
                } else {
                    task.setStatus(Status.NORMAL);
                }
            }
        } else {
            task.setStatus(Status.NORMAL);
        }
        return task;
    }
}
