package imageprocessing.model.componentbifunctions;

import java.util.function.BiFunction;

import imageprocessing.model.ImageProcessingModelImpl;

/**
 * A green bi function that acts on a given pixel at a given position and outputs a new version of
 * that pixel in a way that's grey-scaled in a green-component style.
 */
public class GreenBiFunction implements
        BiFunction<ImageProcessingModelImpl.Posn, ImageProcessingModelImpl.Pixel[][],
                ImageProcessingModelImpl.Pixel> {

  /**
   * Applies this function to the given arguments.
   *
   * @param posn  the first function argument
   * @param image the second function argument
   * @return the function result
   */
  @Override
  public ImageProcessingModelImpl.Pixel apply(ImageProcessingModelImpl.Posn posn,
                                              ImageProcessingModelImpl.Pixel[][] image) {
    int i = posn.getY();
    int j = posn.getX();
    image[i][j].setRed(image[i][j].getGreen());
    image[i][j].setBlue(image[i][j].getGreen());
    return image[i][j];
  }
}
