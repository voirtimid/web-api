package mk.metalkat.webapi.models.jpa;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.metalkat.webapi.models.enums.Status;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs")
public class Job {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long jobId;

    private String jobName;

    @OneToOne
    @JoinColumn(name = "sketch_id")
    private Sketch sketch;

    @OneToMany(mappedBy = "job", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Task> tasks = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Status status = Status.NORMAL;

    private LocalDate jobCreated;

    private LocalDate jobFinished;

    private LocalDate plannedStartDate;

    private LocalDate plannedEndDate;

    private LocalDate realStartDate;

    private LocalDate realEndDate;

    private Double plannedHours = 0D;

    private Double realHours = 0D;

    private Double plannedTimeForPiece = 0D;

    private Double realTimeForPiece = 0D;

    private Long numberOfPieces;

    private boolean isFinished;

    public boolean addTask(Task task) {
        return tasks.add(task);
    }

    public void removeTask(Task task) {
        tasks.remove(task);
    }

    public static Job updateJobStatus(Job job) {
        if (job.getTasks().stream().anyMatch(task1 -> task1.getStatus().equals(Status.BEHIND))) {
            job.setStatus(Status.BEHIND);
        } else if (job.getTasks().stream().anyMatch(task1 -> task1.getStatus().equals(Status.TODAY))) {
            job.setStatus(Status.TODAY);
        } else {
            job.setStatus(Status.NORMAL);
        }
        return job;
    }
}
