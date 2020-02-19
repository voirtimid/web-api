package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.Cnc;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CNCRepository extends JpaRepository<Cnc, Long> {

    Cnc findByFileNameEquals(String fileName);
}
