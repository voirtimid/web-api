package mk.metalkat.webapi.models.dto;

import lombok.Data;
import mk.metalkat.webapi.models.jpa.Employee;

@Data
public class EmployeeDTO {
    private Employee employee;

    private Long userId;
}
