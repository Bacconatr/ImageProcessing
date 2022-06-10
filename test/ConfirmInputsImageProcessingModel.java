import java.util.function.BiFunction;

import imageprocessing.model.FlipType;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.Pixel;
import imageprocessing.model.Posn;

/**
 * A mock used to test the inputs that pass through an ImageProcessingModel from the controller.
 * Takes in a String Builder which logs the inputs that pass through.
 */
public class ConfirmInputsImageProcessingModel implements ImageProcessingModel {

  private StringBuilder log;

  public ConfirmInputsImageProcessingModel(StringBuilder log) {
    this.log = log;
  }

  /**
   * Creates a grey scale representation of the image that has the same image name that the user
   * provides. A new image will be created and stored in this object with a new image name that the
   * user also provides. This method takes in a BiFunction to determine what kind of representation
   * will be made.
   *
   * @param imageName      the name of the image that is going to be grey-scaled.
   * @param newImageName   the new name of the image after the original image is represented.
   * @param representation the BiFunction that will perform work on each pixel in the image and will
   *                       convert them to the appropriate color values. This determines how the
   *                       grey-scaling will take place.
   */
  @Override
  public void createRepresentation(String imageName, String newImageName,
                                   BiFunction<Posn, Pixel[][], Pixel> representation) {

  }

  /**
   * @param flip         the type of flipping that will be performed (currently horizontal or
   *                     vertical)
   * @param imageName    the name of the image to be flipped.
   * @param newImageName the name of the new image once the flipping has been performed.
   */
  @Override
  public void flip(FlipType flip, String imageName, String newImageName) {

  }

  /**
   * @param value        the amount of brightness/darkness that will be applied (negative values is
   *                     equivalent to darkening while positive values brighten the image).
   * @param imageName    the name of the image to be brightened.
   * @param newImageName the name of the new image that is produced after the image is brightened.
   */
  @Override
  public void adjustLight(int value, String imageName, String newImageName) {

  }

  /**
   * Saves the image associated with the given image name as a file to the path that the user
   * provides.
   *
   * @param filePath  the path that the image will be saved to. This path should include the name of
   *                  the new file that will be created when it is stored locally. (e.g. C:/file.ppm
   *                  is the required path to save the file to the C drive).
   * @param imageName the name of the image that should be saved.
   * @throws IllegalArgumentException if the image of the
   */
  @Override
  public void savePPM(String filePath, String imageName) throws IllegalArgumentException {

  }

  /**
   * Reads a PPM file at the given file path and converts it to an image that is represented in the
   * implementation (and is associated with the given image name).
   *
   * @param filePath  the path of the PPM file that will be read.
   * @param imageName the name of the image that will be loaded and represented as data.
   */
  @Override
  public void readPPM(String filePath, String imageName) {

  }

  /**
   * Given an imageName, return the state of the image. This method will be likely used by future
   * views to appropriately display the state of images.
   *
   * @param imageName the name of the image whose state will be returned.
   * @return the state of the image.
   */
  @Override
  public Pixel[][] imageState(String imageName) throws IllegalArgumentException {
    return new Pixel[0][];
  }

  /**
   * Determines the number of images there are. Includes images that have been loaded by the user or
   * new images that have been created by the user.
   *
   * @return the number of images that currently exist.
   */
  @Override
  public int numImages() {
    return 0;
  }
}
