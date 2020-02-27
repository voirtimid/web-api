package mk.metalkat.webapi.models.dto;

import lombok.Data;
import mk.metalkat.webapi.models.Job;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class JobDTO {

    private Job job;

    private Long sketchId;

    private LocalDate startDate;

    private LocalDate endDate;

    private LocalTime startTime;

    private LocalTime endTime;
}
