package imageprocessing.model.componentbifunctions;

import java.awt.*;
import java.util.function.BiFunction;

import imageprocessing.model.ImageProcessingModelImpl;

/**
 * A ColorTransformation bi function that acts on a given pixel at a given position and outputs a
 * new version of that pixel in a way that its color is transformed.
 */

public class ColorTransformationBiFunction
        implements BiFunction<ImageProcessingModelImpl.Posn, ImageProcessingModelImpl.Pixel[][],
        ImageProcessingModelImpl.Pixel> {
  private final double[][] transformation;

  /**
   * Constructor for ColorTransformationBiFunction.
   *
   * @param transformation the specified transformation
   */
  public ColorTransformationBiFunction(double[][] transformation) {
    if (transformation.length != 3 || transformation[0].length != 3) {
      throw new IllegalArgumentException("The color transformation matrix must be a 3 by 3.");
    }
    this.transformation = transformation;
  }

  /**
   * Applies this function to the given arguments. This method will modify the provided pixel
   * according to the 3x3 matrix provided.
   *
   * @param posn   the position of hte pixel
   * @param pixels the image that contains the pixel provided
   * @return the pixel that has been modified
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

    double r = 0;
    double g = 0;
    double b = 0;
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
    return new ImageProcessingModelImpl.Pixel((int) r, (int) g, (int) b);
  }
}
