package mk.metalkat.webapi.repository;

import mk.metalkat.webapi.models.Sketch;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SketchRepository extends JpaRepository<Sketch, Long> {

    Sketch findBySketchNameContaining(String sketchName);

}
