package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.dto.EmployeeDTO;
import mk.metalkat.webapi.models.jpa.Employee;
import mk.metalkat.webapi.models.jpa.Task;

import java.util.List;

public interface EmployeeService {

    Employee getEmployee(Long employeeId);

    Employee updateEmployee(Long employeeId, Employee employee);

    Employee createEmployee(Employee employee);

    Employee deleteEmployee(Long employeeId);

    List<Employee> getAllEmployees();

    List<Employee> getCurrentEmployees();

    List<Task> getTasksForEmployee(Long employeeId);


    Employee createEmployeeWithUser(EmployeeDTO employeeDTO);

    Employee getEmployeeByUserId(Long userId);
}
