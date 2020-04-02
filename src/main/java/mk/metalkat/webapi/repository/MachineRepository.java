package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.jpa.Machine;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MachineRepository extends JpaRepository<Machine, Long> {

    List<Machine> findAllByIsDeletedIsFalse();
}
