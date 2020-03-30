package mk.metalkat.webapi.models.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import mk.metalkat.webapi.models.enums.Status;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tasks")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long taskId;

    private String taskName;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "job_id")
    private Job job;

    @OneToOne
    @JoinColumn(name = "machine_id")
    private Machine machine;

    @OneToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @OneToOne
    @JoinColumn(name = "cnc_code_id")
    private Cnc cncCode;

    private Status status = Status.NORMAL;

    private String comment;

    private LocalDate plannedStartDate;

    private LocalDate plannedEndDate;

    private LocalDate realStartDate;

    private LocalDate realEndDate;

    private Double plannedHours = 0D;

    private Double totalWorkTime = 0D;

    private Double trackedWorkTime = 0D;

    private Double tempTrackedWorkTime = 0D;

    private Double minutesForPiece = 0D;

    private Double realMinutesForPiece = 0D;

    private boolean isFinished = false;

    private boolean workInProgress = false;

    public static Task updateTaskStatus(Task task) {
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
