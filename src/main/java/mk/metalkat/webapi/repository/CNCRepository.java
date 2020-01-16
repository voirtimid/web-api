package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.CNC;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CNCRepository extends JpaRepository<CNC, Long> {
}
