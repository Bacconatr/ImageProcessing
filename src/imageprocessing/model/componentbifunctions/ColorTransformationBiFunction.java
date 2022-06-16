package imageprocessing.model.componentbifunctions;

import java.awt.*;
import java.util.function.BiFunction;

import imageprocessing.model.ImageProcessingModelImpl;

/**
 *
 */
public class ColorTransformationBiFunction implements BiFunction<ImageProcessingModelImpl.Posn, ImageProcessingModelImpl.Pixel[][],
        ImageProcessingModelImpl.Pixel> {
  private final double[][] transformation;

  /**
   *
   * @param transformation
   */
  public ColorTransformationBiFunction(double[][] transformation) {
    if (transformation.length != 3 || transformation[0].length != 3) {
      throw new IllegalArgumentException("The color transformation matrix must be a 3 by 3.");
    }
    this.transformation = transformation;
  }

  /**
   * Applies this function to the given arguments.
   *
   * @param posn   the first function argument
   * @param pixels the second function argument
   * @return the function result
   */
  @Override
  public ImageProcessingModelImpl.Pixel apply(ImageProcessingModelImpl.Posn posn,
                                              ImageProcessingModelImpl.Pixel[][] pixels) {
    // transformation.length and transformation[0].length will always be 3 and 3 respectively, as
    // ensured by the constructor.
    // We also know that the rgb values will be a 3x1 matrix (three rows 1 column)
    int x = posn.getX();
    int y = posn.getY();
    ImageProcessingModelImpl.Pixel currentPixel = pixels[y][x];

    int r = 0;
    int g = 0;
    int b = 0;
    int[] rgb = {currentPixel.getRed(), currentPixel.getGreen(), currentPixel.getBlue()};
    for (int i = 0; i < transformation.length; i++) {
      for (int j = 0; j < transformation[0].length; j++) {
        if (i == 0) {
          r += rgb[j] * transformation[i][j];
        }
        if (i == 1) {
          g += rgb[j] * transformation[i][j];
        }
        if (i == 2) {
          b += rgb[j] * transformation[i][j];
        }
      }
    }
    return new ImageProcessingModelImpl.Pixel(r, g, b);
  }
}
