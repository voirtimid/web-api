package mk.metalkat.webapi.models.dto;

import lombok.Data;
import mk.metalkat.webapi.models.Task;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class TaskDTO {

    private Task task;

    private Long jobId;

    private Long employeeId;

    private Long machineId;

    private Long cncCodeId;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;

}
