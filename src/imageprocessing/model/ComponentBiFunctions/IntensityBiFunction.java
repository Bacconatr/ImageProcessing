package imageprocessing.model.ComponentBiFunctions;

import java.util.function.BiFunction;

import imageprocessing.model.Pixel;
import imageprocessing.model.Posn;

public class IntensityBiFunction implements BiFunction<Posn, Pixel[][], Pixel> {

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
                int intensity =
                        (image[i][j].getRed() + image[i][j].getBlue() + image[i][j].getGreen())
                        / 3;
                image[i][j].setRed(intensity);
                image[i][j].setGreen(intensity);
                image[i][j].setBlue(intensity);
    return image[i][j];
  }
}
