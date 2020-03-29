package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.jpa.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface JobRepository extends JpaRepository<Job, Long> {
}
