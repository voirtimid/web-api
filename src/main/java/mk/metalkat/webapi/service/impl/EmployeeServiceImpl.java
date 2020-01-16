package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.Employee;
import mk.metalkat.webapi.repository.EmployeeRepository;
import mk.metalkat.webapi.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;


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
        employeeRepository.delete(employee);
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }
}
