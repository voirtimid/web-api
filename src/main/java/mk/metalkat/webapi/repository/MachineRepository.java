package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MachineRepository extends JpaRepository<Machine, Long> {
}
