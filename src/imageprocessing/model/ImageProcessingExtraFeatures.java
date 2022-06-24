package imageprocessing.model;

import java.util.function.BiFunction;

/**
 *
 */
public interface ImageProcessingExtraFeatures extends ImageProcessingModel {


  /**
   * Changes the RGB values of every pixel in the board based on a BiFunction that is passed. This
   * allows us to create representations of our choosing as long as a BiFunction is passed that
   * modifies a single pixel's state.
   *
   * @param imageName      the name of the image that is going to be adjusted.
   * @param newImageName   the new name of the image after the original image is represented.
   * @param representation the BiFunction that will perform work on each pixel in the image and will
   *                       convert them to the appropriate color values.
   */
  void createRepresentation(String imageName, String maskName, String newImageName,
                            BiFunction<ImageProcessingModelImpl.Posn,
                                    ImageProcessingModelImpl.Pixel[][],
                                    ImageProcessingModelImpl.Pixel> representation);


  /**
   * Adjusts the light of an image. Can either brighten or darken depending on the value argument.
   *
   * @param value        the amount of brightness/darkness that will be applied (negative values is
   *                     equivalent to darkening while positive values brighten the image).
   * @param imageName    the name of the image to be brightened.
   * @param newImageName the name of the new image that is produced after the image is brightened.
   */
  void adjustLight(int value, String imageName, String newImageName, String maskName);

  /**
   *
   * @param newHeight
   * @param newWidth
   * @param imageName
   * @param newImageName
   */
  void downScale(int newHeight, int newWidth, String imageName, String newImageName);
}
