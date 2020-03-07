package mk.metalkat.webapi.models.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LocalDateDTO {

    private LocalDate startDate;

    private LocalDate endDate;
}
