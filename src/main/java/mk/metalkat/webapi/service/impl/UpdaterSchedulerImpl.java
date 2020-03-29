package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.jpa.Job;
import mk.metalkat.webapi.models.jpa.Task;
import mk.metalkat.webapi.repository.JobRepository;
import mk.metalkat.webapi.repository.TaskRepository;
import mk.metalkat.webapi.service.UpdaterScheduler;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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
                .map(Task::updateTaskStatus).forEach(taskRepository::save);

        System.out.println("Updating tasks");

        jobRepository.findAll().stream()
                .filter(job -> !job.isFinished())
                .map(Job::updateJobStatus)
                .forEach(jobRepository::save);

        System.out.println("Updating orders");

    }
}
