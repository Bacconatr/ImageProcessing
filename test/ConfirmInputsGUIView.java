import java.awt.image.BufferedImage;
import java.util.function.BiFunction;

import imageprocessing.controller.Features;
import imageprocessing.model.ImageProcessingModelImpl;
import imageprocessing.model.componentbifunctions.RedBiFunction;
import imageprocessing.view.IJFrameView;

/**
 *
 */
public class ConfirmInputsGUIView implements IJFrameView {
  public StringBuilder log;

  /**
   *
   * @param log
   */
  public ConfirmInputsGUIView(StringBuilder log) {
    this.log = log;
  }


  /**
   *
   */
  @Override
  public String userLoadPath() {
    // simulates a user choosing a path
    return "C:/cool.png";
  }

  /**
   * @return
   */
  @Override
  public String userSavePath() {
    // simulates a user providing a path
    return "C:/yo.png";
  }

  /**
   * @param newImage
   * @param histogram
   */
  @Override
  public void updateImage(BufferedImage newImage, int[][] histogram) {
    int total = 0;
    for (int[] k : histogram) {
      for (int m : k) {
        total += m;
      }
    }
    log.append(newImage.toString()).append(total);
  }

  /**
   * @return
   */
  @Override
  public BiFunction<ImageProcessingModelImpl.Posn, ImageProcessingModelImpl.Pixel[][],
          ImageProcessingModelImpl.Pixel> showComponentOptions() {
    // This simulates that the user pressed the "red" button to do a red-component
    return new RedBiFunction();
  }

  /**
   * @return
   */
  @Override
  public int userBrightnessInput() {
    // arbitrary value returned that simulates if a user typed in 10
    return 10;
  }

  /**
   * @param errorMessage
   */
  @Override
  public void showErrorMessage(String errorMessage) {
    log.append(errorMessage).append(" ");
  }

  /**
   * @param features
   */
  @Override
  public void addFeatures(Features features) {
    log.append(features.toString()).append(" ");
  }
}
