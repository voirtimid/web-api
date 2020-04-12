package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.exceptions.ModelNotFoundException;
import mk.metalkat.webapi.models.dto.EmployeeDTO;
import mk.metalkat.webapi.models.jpa.Employee;
import mk.metalkat.webapi.models.jpa.Task;
import mk.metalkat.webapi.models.jpa.User;
import mk.metalkat.webapi.repository.EmployeeRepository;
import mk.metalkat.webapi.repository.TaskRepository;
import mk.metalkat.webapi.repository.UserRepository;
import mk.metalkat.webapi.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    private final TaskRepository taskRepository;

    private final UserRepository userRepository;


    @Override
    public Employee getEmployee(Long employeeId) {
        return employeeRepository.findById(employeeId).orElseThrow(() -> new ModelNotFoundException("Employee doesn't exist!"));
    }

    @Override
    public Employee getEmployeeByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ModelNotFoundException("User doesn't exist!"));
        return employeeRepository.findByUser(user);
    }

    @Override
    public Employee updateEmployee(Long employeeId, Employee employee) {
        if (!employeeRepository.findById(employeeId).isPresent() || !employeeId.equals(employee.getEmployeeId())) {
            throw new ModelNotFoundException("Employee doesn't exist!");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee createEmployee(Employee employee) {
        if (employee.getEmployeeId() != null) {
            throw new ModelNotFoundException("Employee doesn't exist!");
        }
        return employeeRepository.save(employee);
    }

    @Override
    public Employee createEmployeeWithUser(EmployeeDTO employeeDTO) {
        Employee employee = employeeDTO.getEmployee();
        if (employee.getEmployeeId() != null) {
            throw new ModelNotFoundException("Employee doesn't exist!");
        }
        User user = userRepository.findById(employeeDTO.getUserId()).orElseThrow(() -> new ModelNotFoundException("User doesn't exist!"));
        employee.setUser(user);
        return employeeRepository.saveAndFlush(employee);
    }

    @Override
    public Employee deleteEmployee(Long employeeId) {
        if (!employeeRepository.findById(employeeId).isPresent()) {
            throw new ModelNotFoundException("Employee doesn't exist!");
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
        return employeeRepository.findAllByIsDeletedIsFalse()
                .stream()
                .filter(employee -> employee.getUser() == null || employee.getUser().getRole().equals("Employee"))
                .collect(Collectors.toList());
    }

    @Override
    public List<Task> getTasksForEmployee(Long employeeId) {
        return taskRepository.findAll().stream()
                .filter(task -> !task.isFinished())
                .filter(task -> task.getEmployee().getEmployeeId().equals(employeeId))
                .collect(Collectors.toList());
    }
}
