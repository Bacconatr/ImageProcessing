package imageprocessing.controller;

import java.util.function.BiFunction;

import imageprocessing.model.FlipType;
import imageprocessing.model.ImageProcessingModelImpl;
import imageprocessing.view.IJFrameView;

/**
 *
 */
public interface Features {

  /**
   *
   * @param view
   */
  void setView(IJFrameView view);

  /**
   *
   */
  void flipImage(FlipType flipType);

  /**
   *
   */
  void imageBrightness();

  /**
   *
   */
  void colorChanging(BiFunction<ImageProcessingModelImpl.Posn,
          ImageProcessingModelImpl.Pixel[][],
          ImageProcessingModelImpl.Pixel> representation);

  /**
   *
   */
  void componentOptions();


  /**
   *
   */
  void loadImageToDisplay();

  /**
   *
   */
  void saveCurrentImage();


  /**
   *
   */
  void exitProgram();
}
