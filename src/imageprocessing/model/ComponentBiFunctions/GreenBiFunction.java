package imageprocessing.model.ComponentBiFunctions;

import java.util.function.BiFunction;

import imageprocessing.model.Pixel;
import imageprocessing.model.Posn;

public class GreenBiFunction implements BiFunction<Posn, Pixel[][], Pixel> {

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
    image[i][j].setRed(image[i][j].getGreen());
    image[i][j].setBlue(image[i][j].getGreen());
    return image[i][j];
  }
}
