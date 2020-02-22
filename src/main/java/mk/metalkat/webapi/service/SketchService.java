package mk.metalkat.webapi.service;

import mk.metalkat.webapi.models.Sketch;

import java.util.List;

public interface SketchService {

    Sketch getSketchById(Long sketchId);

    Sketch getSketchByName(String sketchName);

    Sketch createNewSketch(Sketch sketch);

    Sketch updateSketch(Long sketchId, Sketch updatedSketch);

    Sketch deleteSketch(Long sketchId);

    List<Sketch> getAllSketches();
}
