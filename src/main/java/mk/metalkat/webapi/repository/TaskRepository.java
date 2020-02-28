package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.jpa.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
