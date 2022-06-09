package ImageProcessing.controller.Macros;

import ImageProcessing.model.ImageProcessingModel;

/**
 *
 */
public abstract class AbstractAdjustLightMacro implements ImageProcessingMacro {
  protected final int increment;
  protected final String fileName;
  protected final String outPutName;

  /**
   * Constructs a Brightening Macro.
   *
   * @param increment  the amount the image will be brightened by.
   * @param fileName   the name of the file to be brightened.
   * @param outPutName the name of the output file after the image is brightened.
   */
  public AbstractAdjustLightMacro(int increment, String fileName, String outPutName)
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
  }

  /**
   * @param model the ImageProcessing model that will be executed on.
   */
  @Override
  public abstract void executeProcessingMacro(ImageProcessingModel model);
}
