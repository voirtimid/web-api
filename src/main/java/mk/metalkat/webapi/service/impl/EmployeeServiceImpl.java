package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.jpa.Employee;
import mk.metalkat.webapi.models.jpa.Task;
import mk.metalkat.webapi.repository.EmployeeRepository;
import mk.metalkat.webapi.repository.TaskRepository;
import mk.metalkat.webapi.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final TaskRepository taskRepository;


    @Override
    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId).orElse(null);
    }

    @Override
    public Employee updateEmployee(Long employeeId, Employee employee) {
        if (!employeeRepository.findById(employeeId).isPresent() || !employeeId.equals(employee.getEmployeeId())) {
            return null;
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        if (employee.getEmployeeId() != null) {
            return null;
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee deleteEmployee(Long employeeId) {
        if (!employeeRepository.findById(employeeId).isPresent()) {
            return null;
        }
        Employee employee = employeeRepository.findById(employeeId).get();
        employee.setDeleted(true);
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public List<Employee> getCurrentEmployees() {
        return employeeRepository.findAllByIsDeletedIsFalse();
    }

    @Override
    public List<Task> getTasksForEmployee(Long employeeId) {
        return taskRepository.findAll().stream()
                .filter(task -> !task.isFinished())
                .filter(task -> task.getEmployee().getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }
}
