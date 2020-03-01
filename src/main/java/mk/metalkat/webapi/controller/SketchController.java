package mk.metalkat.webapi.controller;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.models.jpa.Sketch;
import mk.metalkat.webapi.service.SketchService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api/sketches")
@RequiredArgsConstructor
@CrossOrigin("http://localhost:3000")
public class SketchController {

    private final SketchService sketchService;

    @GetMapping("/{sketchId}")
    public ResponseEntity<Sketch> getSketchById(@PathVariable("sketchId") Long sketchId) {
        Sketch sketchById = sketchService.getSketchById(sketchId);
        return ResponseEntity.ok(sketchById);
    }

    @GetMapping("/name/{drawing}")
    public ResponseEntity<Sketch> getSketchByName(@PathVariable("drawing") String drawing) {
        Sketch sketchByName = sketchService.getSketchByName(drawing);
        return ResponseEntity.ok(sketchByName);
    }

    @PostMapping
    public ResponseEntity<Sketch> createNewSketch(@RequestBody Sketch sketch) {
        Sketch newSketch = sketchService.createNewSketch(sketch);
        return ResponseEntity.ok(newSketch);
    }

    @PutMapping("/{sketchId}")
    public ResponseEntity<Sketch> updateSketch(@PathVariable("sketchId") Long sketchId, @RequestBody Sketch updatedSketch) {
        Sketch sketch = sketchService.updateSketch(sketchId, updatedSketch);
        return ResponseEntity.ok(sketch);
    }

    @DeleteMapping("/{sketchId}")
    public ResponseEntity<Sketch> deleteSketch(@PathVariable("sketchId") Long sketchId) {
        Sketch sketch = sketchService.deleteSketch(sketchId);
        return ResponseEntity.ok(sketch);
    }

    @GetMapping
    public ResponseEntity<List<Sketch>> getAllSketches() {
        List<Sketch> allSketches = sketchService.getAllSketches();
        return ResponseEntity.ok(allSketches);
    }
}
