package imageprocessing.model.componentbifunctions;

import java.util.function.BiFunction;

import imageprocessing.model.ImageProcessingModelImpl;

/**
 * A value bi function that acts on a given pixel at a given position and outputs a new version of
 * that pixel in a way that's grey-scaled in a value-component style.
 */
public class ValueBiFunction implements BiFunction<ImageProcessingModelImpl.Posn,
        ImageProcessingModelImpl.Pixel[][], ImageProcessingModelImpl.Pixel> {

  /**
   * Applies this function to the given arguments.
   *
   * @param posn   the first function argument
   * @param image the second function argument
   * @return the function result
   */
  @Override
  public ImageProcessingModelImpl.Pixel apply(ImageProcessingModelImpl.Posn posn,
                                              ImageProcessingModelImpl.Pixel[][] image) {
    int i = posn.getY();
    int j = posn.getX();
    int greatestRGBChannel = Math.max(image[i][j].getRed(), Math.max(image[i][j]
                    .getBlue(),
            image[i][j].getGreen()));
    image[i][j].setRed(greatestRGBChannel);
    image[i][j].setGreen(greatestRGBChannel);
    image[i][j].setBlue(greatestRGBChannel);
    return image[i][j];
  }
}
