package ImageProcessing.model.ComponentBiFunctions;

import java.util.function.BiFunction;

import ImageProcessing.model.Pixel;
import ImageProcessing.model.Posn;

public class BlueBiFunction implements BiFunction<Posn, Pixel[][], Pixel> {

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
    image[i][j].setRed(image[i][j].getBlue());
    image[i][j].setGreen(image[i][j].getBlue());
    return image[i][j];
  }
}
