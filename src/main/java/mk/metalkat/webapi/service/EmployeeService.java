package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.jpa.Employee;
import mk.metalkat.webapi.models.jpa.Task;

import java.util.List;

public interface EmployeeService {

    Employee getEmployee(Long employeeId);

    Employee updateEmployee(Long employeeId, Employee employee);

    Employee createEmployee(Employee employee);

    Employee deleteEmployee(Long employeeId);

    List<Employee> getAllEmployees();

    List<Task> getTasksForEmployee(Long employeeId);



}
