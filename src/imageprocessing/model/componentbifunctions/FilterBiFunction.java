package imageprocessing.model.componentbifunctions;

import java.util.function.BiFunction;

import imageprocessing.model.ImageProcessingModelImpl;


/**
 * A Filter bi function that acts on a given pixel at a given position and outputs a new version of
 * that pixel by applying a Kernel to the pixel.
 */
public class FilterBiFunction implements
        BiFunction<ImageProcessingModelImpl.Posn, ImageProcessingModelImpl.Pixel[][],
                ImageProcessingModelImpl.Pixel> {
  private final double[][] kernel;

  /**
   * Constructs a FilterBiFunction.
   *
   * @param kernel a kernel that is a 2D matrix with odd dimensions that are always equal.
   */
  public FilterBiFunction(double[][] kernel) {
    if (kernel.length % 2 == 0 || kernel.length != kernel[0].length) {
      throw new IllegalArgumentException("A kernel must be a square matrix with odd dimensions");
    }
    this.kernel = kernel;
  }

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

    ImageProcessingModelImpl.Pixel centerPixel = new ImageProcessingModelImpl.Pixel(0, 0, 0);
    // A Kernel will always be a square matrix with odd dimensions
    int kSize = kernel.length;

    int yInitial = posn.getY() - kSize / 2;
    int xInitial = posn.getX() - kSize / 2;

    for (int i = 0; i < 3; i++) {
      double centerPixelSum = 0;
      for (int row = yInitial; row <= kSize / 2 + posn.getY(); row++) {

        for (int col = xInitial; col <= kSize / 2 + posn.getX(); col++) {
          if (!((row < 0 || row >= image.length) || (col < 0 || col >= image[0].length))) {
            ImageProcessingModelImpl.Pixel workingPixel =
                    image[row][col];
            double multiplyPixel = kernel[row - yInitial][col - xInitial];

            switch (i) {
              case 0:
                centerPixelSum += multiplyPixel * workingPixel.getRed();
                break;
              case 1:
                centerPixelSum += multiplyPixel * workingPixel.getGreen();
                break;
              case 2:
                centerPixelSum += multiplyPixel * workingPixel.getBlue();
                break;
              default:
                throw new IllegalStateException("Not one of the RGB values");
            }
          }
        }
      }

      switch (i) {
        case 0:
          centerPixel.setRed((int) centerPixelSum);
          break;
        case 1:
          centerPixel.setGreen((int) centerPixelSum);
          break;
        case 2:
          centerPixel.setBlue((int) centerPixelSum);
          break;
        default:
          throw new IllegalStateException("Not one of the RGB values");


      }


    }


    return centerPixel;
  }


}
