package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.repository.EmployeeRepository;
import mk.metalkat.webapi.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;


}
