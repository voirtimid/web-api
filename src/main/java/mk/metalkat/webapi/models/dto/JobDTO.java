package mk.metalkat.webapi.models.dto;

import lombok.Data;
import mk.metalkat.webapi.models.jpa.Job;

@Data
public class JobDTO {

    private Job job;

    private Long sketchId;
}
