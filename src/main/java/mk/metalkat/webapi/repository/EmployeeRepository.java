package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

}
