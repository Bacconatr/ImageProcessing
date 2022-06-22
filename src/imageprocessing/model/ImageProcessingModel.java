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
   * Changes the RGB values of every pixel in the board based on a BiFunction that is passed. This
   * allows us to create representations of our choosing as long as a BiFunction is passed that
   * modifies a single pixel's state.
   *
   * @param imageName      the name of the image that is going to be adjusted.
   * @param newImageName   the new name of the image after the original image is represented.
   * @param representation the BiFunction that will perform work on each pixel in the image and will
   *                       convert them to the appropriate color values.
   */
  void createRepresentation(String imageName, String newImageName,
                            BiFunction<ImageProcessingModelImpl.Posn,
                                    ImageProcessingModelImpl.Pixel[][],
                                    ImageProcessingModelImpl.Pixel> representation);

  /**
   * Flips an image. Can be either horizontal or a vertical flip.
   *
   * @param flip         the type of flipping that will be performed (currently horizontal or
   *                     vertical)
   * @param imageName    the name of the image to be flipped.
   * @param newImageName the name of the new image once the flipping has been performed.
   */
  void flip(FlipType flip, String imageName, String newImageName);

  /**
   * Adjusts the light of an image. Can either brighten or darken depending on the value argument.
   *
   * @param value        the amount of brightness/darkness that will be applied (negative values is
   *                     equivalent to darkening while positive values brighten the image).
   * @param imageName    the name of the image to be brightened.
   * @param newImageName the name of the new image that is produced after the image is brightened.
   */
  void adjustLight(int value, String imageName, String newImageName);


  // ##################### REFACTORED #############################
  // removed load and save and moved it to the controller. The model SHOULD NOT have access to this
  // method as it relates to IO and user input

  // ##################### REFACTORED #############################
  // Added a new method called addImage. This is used to add an image to the list of images. This
  // is required for the controller to pass a parsed image data to the model so that the model
  // does not have to do the work can just simply accept an image state that is provided.

  /**
   * Adds an image to the field that handles storing images (this is dependent on the
   * implementation).
   *
   * @param image the image to be added (represented as a 2D Array of Pixels)
   */
  void addImage(ImageProcessingModelImpl.Pixel[][] image, String imageName);

}




