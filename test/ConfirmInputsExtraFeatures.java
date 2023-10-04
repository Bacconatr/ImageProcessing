import java.util.function.BiFunction;

import imageprocessing.model.FlipType;
import imageprocessing.model.ImageProcessingExtraFeatures;
import imageprocessing.model.ImageProcessingModelImpl;

/**
 *
 */
public class ConfirmInputsExtraFeatures implements ImageProcessingExtraFeatures {
  // public so it is accessible in tests
  public StringBuilder log;

  public ConfirmInputsExtraFeatures(StringBuilder log) {
    this.log = log;
  }


  @Override
  public void createRepresentation(String imageName, String maskName, String newImageName,
                                   BiFunction<ImageProcessingModelImpl.Posn,
                                           ImageProcessingModelImpl.Pixel[][], ImageProcessingModelImpl.Pixel> representation) {
    // arbitrary
  }

  @Override
  public void adjustLight(int value, String imageName, String newImageName, String maskName) {
    // arbitrary
  }

  @Override
  public void downScale(int newHeight, int newWidth, String imageName, String newImageName) {
    // arbitrary
  }

  @Override
  public void createRepresentation(String imageName, String newImageName,
                                   BiFunction<ImageProcessingModelImpl.Posn,
                                           ImageProcessingModelImpl.Pixel[][],
                                           ImageProcessingModelImpl.Pixel> representation) {
    // arbitrary
  }

  @Override
  public void flip(FlipType flip, String imageName, String newImageName) {
    // arbitrary
  }

  @Override
  public void adjustLight(int value, String imageName, String newImageName) {

  }

  @Override
  public void addImage(ImageProcessingModelImpl.Pixel[][] image, String imageName) {

  }
  @Override
  public ImageProcessingModelImpl.Pixel[][] imageState(String imageName)
          throws IllegalArgumentException {
    return new ImageProcessingModelImpl.Pixel[0][];
  }

  @Override
  public int numImages() {
    return 0;
  }
}
