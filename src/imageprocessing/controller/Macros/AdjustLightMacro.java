package imageprocessing.controller.Macros;

import imageprocessing.model.ImageProcessingModel;

/**
 *
 */
public class AdjustLightMacro implements ImageProcessingMacro {
  private final int increment;
  private final String fileName;
  private final String outPutName;
  private final boolean isBrightening;

  /**
   * Constructs a Brightening Macro.
   *
   * @param increment  the amount the image will be brightened by.
   * @param fileName   the name of the file to be brightened.
   * @param outPutName the name of the output file after the image is brightened.
   */
  public AdjustLightMacro(int increment, String fileName, String outPutName,
                          boolean isBrightening)
          throws IllegalArgumentException {
    if (increment < 0) {
      throw new IllegalArgumentException("Cannot brighten by a negative value");
    }
    if (fileName == null) {
      throw new IllegalArgumentException("Must provide a non-null String value for the File Name.");
    }
    if (outPutName == null) {
      throw new IllegalArgumentException("Must provide a non-null String value for the File Path.");
    }
    this.increment = increment;
    this.fileName = fileName;
    this.outPutName = outPutName;
    this.isBrightening = isBrightening;
  }

  /**
   * @param model the ImageProcessing model that will be executed on.
   */
  @Override
  public void executeProcessingMacro(ImageProcessingModel model) {
    if (isBrightening) {
      model.adjustLight(increment, fileName, outPutName);
    } else {
      model.adjustLight(increment * -1, fileName, outPutName);
    }
  }
}
