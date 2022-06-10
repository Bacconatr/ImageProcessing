package imageprocessing.model.ComponentBiFunctions;

import java.util.function.BiFunction;

import imageprocessing.model.Pixel;
import imageprocessing.model.Posn;

public class LumaBiFunction implements BiFunction<Posn, Pixel[][], Pixel> {

  /**
   * Applies this function to the given arguments.
   *
   * @param posn   the first function argument
   * @param image the second function argument
   * @return the function result
   */
  @Override
  public Pixel apply(Posn posn, Pixel[][] image) {
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
