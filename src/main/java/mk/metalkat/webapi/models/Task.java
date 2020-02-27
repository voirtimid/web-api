package mk.metalkat.webapi.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
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

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;

    private LocalTime startWorkTime;

    private LocalTime endWorkTime;

    private Long totalWorkTime;

    private String measuringList;

    private String usedTools;

    private Double pieceByMinute;

    private Double priceByPiece;

    private Double totalGain;

    private boolean isFinished;

    private boolean workInProgress;
}
