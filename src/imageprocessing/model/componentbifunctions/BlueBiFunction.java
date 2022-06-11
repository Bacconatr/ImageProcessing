package imageprocessing.model.componentbifunctions;

import java.util.function.BiFunction;

import imageprocessing.model.ImageProcessingModelImpl;

/**
 * A blue bi function that acts on a given pixel at a given position and outputs a new version of
 * that pixel in a way that's grey-scaled in a blue-component style.
 */
public class BlueBiFunction implements
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
    image[i][j].setRed(image[i][j].getBlue());
    image[i][j].setGreen(image[i][j].getBlue());
    return image[i][j];
  }
}
