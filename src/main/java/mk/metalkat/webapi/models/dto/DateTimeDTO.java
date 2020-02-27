package mk.metalkat.webapi.models.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class DateTimeDTO {

    private LocalDateTime startDateTime;

    private LocalDateTime endDateTime;
}
