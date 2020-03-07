package mk.metalkat.webapi.models.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class FilterJobDTO {

    private LocalDate startDate;

    private LocalDate endDate;

    private String forWhat;
}
