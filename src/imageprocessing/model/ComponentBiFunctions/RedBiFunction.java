package imageprocessing.model.ComponentBiFunctions;

import java.util.function.BiFunction;

import imageprocessing.model.Pixel;
import imageprocessing.model.Posn;

public class RedBiFunction implements BiFunction<Posn, Pixel[][], Pixel> {

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
    image[i][j].setGreen(image[i][j].getRed());
    image[i][j].setBlue(image[i][j].getRed());
    return image[i][j];
  }
}
