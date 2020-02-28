package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.jpa.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Long> {
}
