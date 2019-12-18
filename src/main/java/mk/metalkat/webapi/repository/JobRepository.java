package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
