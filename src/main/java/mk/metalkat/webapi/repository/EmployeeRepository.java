package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.jpa.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    List<Employee> findAllByIsDeletedIsFalse();
}
