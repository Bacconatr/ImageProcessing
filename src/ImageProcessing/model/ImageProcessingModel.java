package ImageProcessing.model;

/**
 * Represents an object that can take in images and perform processes on them.
 */
public interface ImageProcessingModel {
  /**
   *
   * @param type
   * @param filename
   * @param newFileName
   */
  void createRepresentation(VisualizationType type, String filename, String newFileName);

  /**
   *
   * @param flip
   * @param filename
   * @param newFileName
   */
  void flip(FlipType flip, String filename, String newFileName);

  /**
   *
   * @param value
   * @param filename
   * @param newFileName
   */
  void adjustLight(int value, String filename, String newFileName);

  /**
   *
   * @param filename
   * @param fileName
   */
  void readPPM(String filename, String fileName);

  /**
   *
   * @param fileName
   * @param filePath
   */
  void savePPM(String fileName, String filePath);

}




