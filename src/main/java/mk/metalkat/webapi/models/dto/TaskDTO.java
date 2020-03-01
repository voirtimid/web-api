package mk.metalkat.webapi.models.dto;

import lombok.Data;
import mk.metalkat.webapi.models.jpa.Task;

@Data
public class TaskDTO {

    private Task task;

    private Long jobId;

    private Long employeeId;

    private Long machineId;

    private Long cncCodeId;

    private Double plannedMinutesForPiece;

}
