package ImageProcessing.controller.Macros;

import ImageProcessing.model.ImageProcessingModel;

/**
 *
 */
public abstract class AbstractFlipMacro implements ImageProcessingMacro {
  protected final String fileName;
  protected final String outPutName;

  /**
   *
   * @param fileName
   * @param outPutName
   */
  public AbstractFlipMacro(String fileName, String outPutName) throws IllegalArgumentException {
    if (fileName == null) {
      throw new IllegalArgumentException("The File Name must be a non-null value.");
    }
    if (outPutName == null) {
      throw new IllegalArgumentException("The output name must be a non-null value.");
    }
    this.fileName = fileName;
    this.outPutName = outPutName;
  }

  /**
   * Executes a macro given an ImageProcessing model.
   *
   * @param model the ImageProcessing model that will be executed on.
   */
  @Override
  public abstract void executeProcessingMacro(ImageProcessingModel model);
}
