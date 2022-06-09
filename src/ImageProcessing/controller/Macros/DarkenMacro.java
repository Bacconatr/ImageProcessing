package ImageProcessing.controller.Macros;

import ImageProcessing.model.ImageProcessingModel;

/**
 *
 */
public class DarkenMacro extends AbstractAdjustLightMacro {
  /**
   * Constructs a Brightening Macro.
   *
   * @param increment  the amount the image will be brightened by.
   * @param fileName   the name of the file to be brightened.
   * @param outPutName the name of the output file after the image is brightened.
   */
  public DarkenMacro(int increment, String fileName, String outPutName)
          throws IllegalArgumentException {
    super(increment, fileName, outPutName);
  }

  /**
   * @param model the ImageProcessing model that will be executed on.
   */
  @Override
  public void executeProcessingMacro(ImageProcessingModel model) {
    int darkenIncrement = increment * -1;
    model.adjustLight(darkenIncrement, fileName, outPutName);
  }
}
