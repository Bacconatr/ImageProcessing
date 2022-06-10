package imageprocessing.controller.Macros;

import imageprocessing.model.FlipType;
import imageprocessing.model.ImageProcessingModel;

/**
 *
 */
public class FlipMacro implements ImageProcessingMacro {
  private final String fileName;
  private final String outPutName;
  private final FlipType flipType;

  /**
   *
   * @param fileName
   * @param outPutName
   */
  public FlipMacro(String fileName, String outPutName, FlipType flipType)
          throws IllegalArgumentException {
    if (fileName == null) {
      throw new IllegalArgumentException("The File Name must be a non-null value.");
    }
    if (outPutName == null) {
      throw new IllegalArgumentException("The output name must be a non-null value.");
    }
    this.fileName = fileName;
    this.outPutName = outPutName;
    this.flipType = flipType;
  }

  /**
   * Executes a macro given an ImageProcessing model.
   *
   * @param model the ImageProcessing model that will be executed on.
   */
  @Override
  public void executeProcessingMacro(ImageProcessingModel model) {
    model.flip(flipType, fileName, outPutName);
  }
}
