package imageprocessing.model.ComponentBiFunctions;

import java.util.function.BiFunction;

import imageprocessing.model.Pixel;
import imageprocessing.model.Posn;

public class ValueBiFunction implements BiFunction<Posn, Pixel[][], Pixel> {

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
    int greatestRGBChannel = Math.max(image[i][j].getRed(), Math.max(image[i][j]
                    .getBlue(),
            image[i][j].getGreen()));
    image[i][j].setRed(greatestRGBChannel);
    image[i][j].setGreen(greatestRGBChannel);
    image[i][j].setBlue(greatestRGBChannel);
    return image[i][j];
  }
}
