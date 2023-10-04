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
    // a pixel that will be updated to be the correct pixel that has a kernel applied to it
    ImageProcessingModelImpl.Pixel centerPixel = new ImageProcessingModelImpl.Pixel(0, 0, 0);
    // A Kernel will always be a square matrix with odd dimensions
    int kSize = kernel.length;
    int yInitial = posn.getY() - kSize / 2;
    int xInitial = posn.getX() - kSize / 2;

    // for loop that iterates three times (once for each channel: R, G, and B)
    // the total will be the new value of the pixel's channel
    double centerPixelRedSum = 0;
    double centerPixelGreenSum = 0;
    double centerPixelBlueSum = 0;
    // a nested for loop that iterates as many times as the kernel's width times the kernel's size
    for (int row = yInitial; row <= kSize / 2 + posn.getY(); row++) {
      for (int col = xInitial; col <= kSize / 2 + posn.getX(); col++) {
        if (!((row < 0 || row >= image.length) || (col < 0 || col >= image[0].length))) {
          ImageProcessingModelImpl.Pixel workingPixel =
                  image[row][col];
          double multiplyPixel = kernel[row - yInitial][col - xInitial];

          centerPixelRedSum += multiplyPixel * workingPixel.getRed();
          centerPixelGreenSum += multiplyPixel * workingPixel.getGreen();
          centerPixelBlueSum += multiplyPixel * workingPixel.getBlue();
        }
      }
    }

    centerPixel.setRed((int) centerPixelRedSum);
    centerPixel.setGreen((int) centerPixelGreenSum);
    centerPixel.setBlue((int) centerPixelBlueSum);

    return centerPixel;
  }


}
