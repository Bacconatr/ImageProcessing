package imageprocessing.model;

import java.util.function.BiFunction;

/**
 * Represents an object that can take in images and perform processes on them. Also supports loading
 * and saving files based on the user input that is provided. The processes that are support are
 * grey-scaling, flipping, and brightening/darkening images.
 */
public interface ImageProcessingModel extends ImageProcessingState {
  // in interface segregation: will the view ever call this method? if no, then you probably
  // should split these methods since the view won't ever need it

  /**
   * Creates a grey scale representation of the image that has the same image name that the user
   * provides. A new image will be created and stored in this object with a new image name that the
   * user also provides. This method takes in a BiFunction to determine what kind of representation
   * will be made.
   *
   * @param imageName the name of the image that is going to be grey-scaled.
   * @param newImageName the new name of the image after the original image is represented.
   * @param representation the BiFunction that will perform work on each pixel in the image and
   *                       will convert them to the appropriate color values. This determines how
   *                       the grey-scaling will take place.
   */
  void createRepresentation(String imageName, String newImageName,
                            BiFunction<Posn, imageprocessing.model.Pixel[][], Pixel> representation);

  /**
   * @param flip the type of flipping that will be performed (currently horizontal or vertical)
   * @param imageName the name of the image to be flipped.
   * @param newImageName the name of the new image once the flipping has been performed.
   */
  void flip(FlipType flip, String imageName, String newImageName);

  /**
   * @param value the amount of brightness/darkness that will be applied (negative values is
   *              equivalent to darkening while positive values brighten the image).
   * @param imageName the name of the image to be brightened.
   * @param newImageName the name of the new image that is produced after the image is brightened.
   */
  void adjustLight(int value, String imageName, String newImageName);

  // these will likely fit in the model better, but there are arguments in both ways
  // but this is likely the models job because THE MODELS STATE is changing (for load)
  // save is a bit different since the model state is changing

  /**
   * Saves the image associated with the given image name as a file to the path that the user
   * provides.
   *
   * @param filePath the path that the image will be saved to. This path should include the name
   *                 of the new file that will be created when it is stored locally. (e.g.
   *                 C:/file.ppm is the required path to save the file to the C drive).
   * @param imageName the name of the image that should be saved.
   * @throws IllegalArgumentException if the image of the
   */
  void savePPM(String filePath, String imageName) throws IllegalArgumentException;

  /**
   * Reads a PPM file at the given file path and converts it to an image that is represented in
   * the implementation (and is associated with the given image name).
   *
   * @param filePath the path of the PPM file that will be read.
   * @param imageName the name of the image that will be loaded and represented as data.
   */
  void readPPM(String filePath, String imageName);

  // we can add methods that the view will likely need in the future
  // future-proofing is a valid justification (as long as it is not doing a future view's jobs
  // for it, if it's just giving the view what it has then this is fine)
  // (can also use in the tests)
}




