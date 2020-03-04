package mk.metalkat.webapi.models.jpa;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

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

    private String comment;

    private LocalDate plannedStartDate;

    private LocalDate plannedEndDate;

    private LocalDate realStartDate;

    private LocalDate realEndDate;

    private Double plannedHours = 0D;

    private Double totalWorkTime = 0D;

    private Double minutesForPiece = 0D;

    private Double realMinutesForPiece = 0D;

    private boolean isFinished;

    private boolean workInProgress;
}
