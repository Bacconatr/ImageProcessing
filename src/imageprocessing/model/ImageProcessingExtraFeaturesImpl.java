package imageprocessing.model;

import java.util.function.BiFunction;

/**
 *
 */
public class ImageProcessingExtraFeaturesImpl extends ImageProcessingModelImpl
        implements ImageProcessingExtraFeatures {

  /**
   *
   */
  public ImageProcessingExtraFeaturesImpl() {
    super();
  }

  /**
   * Changes the RGB values of every pixel in the board based on a BiFunction that is passed. This
   * allows us to create representations of our choosing as long as a BiFunction is passed that
   * modifies a single pixel's state.
   *
   * @param imageName      the name of the image that is going to be adjusted.
   * @param newImageName   the new name of the image after the original image is represented.
   * @param representation the BiFunction that will perform work on each pixel in the image and will
   *                       convert them to the appropriate color values.
   * @param maskName
   */
  @Override
  public void createRepresentation(String imageName, String maskName, String newImageName,
                                   BiFunction<Posn, Pixel[][], Pixel> representation) {
    Pixel[][] maskImage = mapOfImages.getOrDefault(imageName, null);
    Pixel[][] originalImage = imageDeepCopy(mapOfImages.getOrDefault(imageName, null));
    if (originalImage == null || maskImage == null) {
      throw new IllegalArgumentException("File(s) not found.");
    }

    Pixel blackPixel = new Pixel(0, 0, 0);
    Pixel[][] newImage = new Pixel[originalImage.length][originalImage[0].length];
    for (int i = 0; i < originalImage.length; i++) {
      for (int j = 0; j < originalImage[0].length; j++) {
        if (maskImage[i][j].equals(blackPixel)) {
          newImage[i][j] = representation.apply(new Posn(i, j), originalImage);
        }
      }
    }
    mapOfImages.put(newImageName, newImage);
  }

  /**
   * Adjusts the light of an image. Can either brighten or darken depending on the value argument.
   *
   * @param value        the amount of brightness/darkness that will be applied (negative values is
   *                     equivalent to darkening while positive values brighten the image).
   * @param imageName    the name of the image to be brightened.
   * @param newImageName the name of the new image that is produced after the image is brightened.
   * @param maskName
   */
  @Override
  public void adjustLight(int value, String imageName, String newImageName, String maskName) {

  }

  @Override
  public void downScale(int newHeight, int newWidth, String imageName, String newImageName) {
    // first case: 200 400 -> 100 200
    Pixel[][] image = imageDeepCopy(mapOfImages.getOrDefault(imageName, null));

    if (image == null) {
      throw new IllegalArgumentException("sorry the file you are trying to flip does not exist");
    }



    int height = image.length;
    int width = image[0].length;

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {

      }
    }

  }
}
