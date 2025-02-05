package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.dto.EmployeeDTO;
import mk.metalkat.webapi.models.jpa.Employee;
import mk.metalkat.webapi.models.jpa.Task;
import mk.metalkat.webapi.service.EmployeeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:3000", "*"})
public class EmployeeController {

    private final EmployeeService employeeService;

    @GetMapping("/all")
    public List<Employee> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @GetMapping()
    public List<Employee> getCurrentEmployees() {
        return employeeService.getCurrentEmployees();
    }

    @PostMapping("/user")
    public Employee createEmployeeWithUser(@RequestBody EmployeeDTO employeeDTO) {
        return employeeService.createEmployeeWithUser(employeeDTO);
    }

    @GetMapping("/user/{userId}")
    public Employee getEmployeeByUserId(@PathVariable("userId") Long userId) {
        return employeeService.getEmployeeByUserId(userId);
    }

    @PostMapping
    public Employee createEmployee(@RequestBody Employee employee) {
        return employeeService.createEmployee(employee);
    }

    @GetMapping(value = "/{employeeId}")
    public Employee getEmployeeById(@PathVariable("employeeId") Long employeeId) {
        return employeeService.getEmployee(employeeId);
    }

    @PutMapping(value = "/{employeeId}")
    public Employee updateEmployee(@PathVariable("employeeId") Long employeeId, @RequestBody Employee employee) {
        return employeeService.updateEmployee(employeeId, employee);
    }

    @DeleteMapping(value = "/{employeeId}")
    public Employee deleteEmployee(@PathVariable("employeeId") Long employeeId) {
        return employeeService.deleteEmployee(employeeId);
    }

    @GetMapping(value = "/{employeeId}/tasks")
    public List<Task> getAllTasksForEmployee(@PathVariable("employeeId") Long employeeId) {
        return employeeService.getTasksForEmployee(employeeId);
    }

}
