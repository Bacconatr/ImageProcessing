package imageprocessing.model.componentbifunctions;

import java.util.function.BiFunction;

import imageprocessing.model.ImageProcessingModelImpl;

/**
 * A red bi function that acts on a given pixel at a given position and outputs a new version of
 * that pixel in a way that's grey-scaled in a red-component style.
 */
public class RedBiFunction implements BiFunction<ImageProcessingModelImpl.Posn,
        ImageProcessingModelImpl.Pixel[][], ImageProcessingModelImpl.Pixel> {

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
    image[i][j].setGreen(image[i][j].getRed());
    image[i][j].setBlue(image[i][j].getRed());
    return image[i][j];
  }
}
