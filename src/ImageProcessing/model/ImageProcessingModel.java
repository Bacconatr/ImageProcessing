package ImageProcessing.model;

import java.util.function.BiFunction;

/**
 * Represents an object that can take in images and perform processes on them.
 */
public interface ImageProcessingModel extends ImageProcessingState  {
  // in interface segregation: will the view ever call this method? if no, then you probably
  // should split these methods since the view won't ever need it
  /**
   *
   * @param filename
   * @param newFileName
   */
  void createRepresentation(String filename, String newFileName,
                            BiFunction<Posn, ImageProcessing.model.Pixel[][], Pixel> bifunction);

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

  // these will likely fit in the model better, but there are arguments in both ways
  // but this is likely the models job because THE MODELS STATE is changing (for load)
  // save is a bit different since the model state is changing

  /**
   *
   * @param filePath
   * @param fileName
   * @throws IllegalArgumentException
   */
  void savePPM(String filePath, String fileName) throws IllegalArgumentException;

  /**
   *
   * @param filePath
   * @param fileName
   */
  void readPPM(String filePath, String fileName);

  // we can add methods that the view will likely need in the future
  // future-proofing is a valid justification (as long as it is not doing a future view's jobs
  // for it, if it's just giving the view what it has then this is fine)
  // (can also use in the tests)
}




