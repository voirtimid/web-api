package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.jpa.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface JobRepository extends JpaRepository<Job, Long> {

//    Page<Job> findAllByJobCreatedBetween(LocalDate from, LocalDate to);
}
