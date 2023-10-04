package imageprocessing.view;

import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

import imageprocessing.controller.Features;
import imageprocessing.model.ImageProcessingModelImpl;

/**
 *
 */
public interface IJFrameView  {


  /**
   *
   */
  String userLoadPath();

  /**
   *
   * @return
   */
  String userSavePath();

  /**
   *
   * @param newImage
   */
  void updateImage(BufferedImage newImage, int[][] histogram);

  /**
   *
   * @return
   */
  BiFunction<ImageProcessingModelImpl.Posn,
          ImageProcessingModelImpl.Pixel[][],
          ImageProcessingModelImpl.Pixel> showComponentOptions();

  /**
   *
   * @return
   */
  int userBrightnessInput();

  /**
   *
   * @param errorMessage
   */
  void showErrorMessage(String errorMessage);

  /**
   *
   * @param features
   */
  void addFeatures(Features features);
}
