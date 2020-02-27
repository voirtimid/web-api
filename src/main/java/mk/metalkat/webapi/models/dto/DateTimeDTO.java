package mk.metalkat.webapi.models.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DateTimeDTO {

    private LocalDate startDate;

    private LocalDate endDate;
}
