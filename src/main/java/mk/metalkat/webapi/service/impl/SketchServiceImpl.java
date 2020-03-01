package mk.metalkat.webapi.service.impl;

import lombok.RequiredArgsConstructor;
import mk.metalkat.webapi.exceptions.ModelNotFoundException;
import mk.metalkat.webapi.models.jpa.Sketch;
import mk.metalkat.webapi.repository.SketchRepository;
import mk.metalkat.webapi.service.SketchService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SketchServiceImpl implements SketchService {

    private final SketchRepository sketchRepository;

    @Override
    public Sketch getSketchById(Long sketchId) {
        return sketchRepository.findById(sketchId).orElseThrow(() -> new ModelNotFoundException("Sketch does not exist"));
    }

    @Override
    public Sketch getSketchByName(String drawing) {
        return sketchRepository.findByDrawingContaining(drawing);
    }

    @Override
    public Sketch createNewSketch(Sketch sketch) {
        Sketch firstBySketchName = sketchRepository.findByDrawingContaining(sketch.getDrawing());
        if (firstBySketchName != null) {
            throw new ModelNotFoundException("Sketch with this name already exist");
        }
        return sketchRepository.save(sketch);
    }

    @Override
    public Sketch updateSketch(Long sketchId, Sketch updatedSketch) {
        if (!sketchRepository.findById(sketchId).isPresent()) {
            throw new ModelNotFoundException("Sketch with this name does not exist");
        }
        return sketchRepository.saveAndFlush(updatedSketch);
    }

    @Override
    public Sketch deleteSketch(Long sketchId) {
        Optional<Sketch> optionalSketch = sketchRepository.findById(sketchId);
        if (!optionalSketch.isPresent()) {
            throw new ModelNotFoundException("Sketch with this name does not exist");
        }
        sketchRepository.delete(optionalSketch.get());
        return optionalSketch.get();
    }

    @Override
    public List<Sketch> getAllSketches() {
        return sketchRepository.findAll();
    }
}
