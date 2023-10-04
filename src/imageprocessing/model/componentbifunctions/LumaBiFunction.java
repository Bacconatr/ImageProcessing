package imageprocessing.model.componentbifunctions;

import java.util.function.BiFunction;

import imageprocessing.model.ImageProcessingModelImpl;

/**
 * A luma bi function that acts on a given pixel at a given position and outputs a new version of
 * that pixel in a way that's grey-scaled in a luma-component style.
 */
public class LumaBiFunction implements
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
    double luma =
            (.2126 * image[i][j].getRed() + .7152 * image[i][j].getGreen()
                    + .0722 * image[i][j].getBlue());
    image[i][j].setRed((int) luma);
    image[i][j].setGreen((int) luma);
    image[i][j].setBlue((int) luma);
    return image[i][j];
  }
}
