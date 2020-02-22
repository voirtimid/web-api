package mk.metalkat.webapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @OneToMany
    private List<Task> tasks = new ArrayList<>();

    private LocalDate startDate;

    private LocalDate endDate;

    private Double estimation;

    private Double numberOfPieces;

    private boolean isFinished;

    public boolean addTask(Task task) {
        return tasks.add(task);
    }

    public boolean removeTask(Task task) {
        return tasks.remove(task);
    }
}
