package ImageProcessing.model;

import java.io.File;

/**
 * Represents an object that can take in images and perform processes on them.
 */
public interface ImageProcessing {

 // void createColorVisualization(Colors color, Color visualization);

  void createRepresentation(VisualizationType type, String filename);

  void flip(FlipType flip, String filename);

  void adjustLight(int value, String filename);

  void readPPM(String filename);






}
