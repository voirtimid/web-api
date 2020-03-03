package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.jpa.Sketch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SketchRepository extends JpaRepository<Sketch, Long> {

    Sketch findFirstByDrawingContaining(String drawing);

}
