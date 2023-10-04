package imageprocessing.model.componentbifunctions;

import java.util.function.BiFunction;

import imageprocessing.model.ImageProcessingModelImpl;

/**
 * An intensity bi function that acts on a given pixel at a given position and outputs a new version
 * of that pixel in a way that's grey-scaled in a intensity-component style.
 */
public class IntensityBiFunction implements
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
    int intensity =
            (image[i][j].getRed() + image[i][j].getBlue() + image[i][j].getGreen())
                    / 3;
    image[i][j].setRed(intensity);
    image[i][j].setGreen(intensity);
    image[i][j].setBlue(intensity);
    return image[i][j];
  }
}
