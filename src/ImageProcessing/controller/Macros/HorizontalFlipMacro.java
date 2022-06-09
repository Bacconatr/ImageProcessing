package ImageProcessing.controller.Macros;

import ImageProcessing.model.FlipType;
import ImageProcessing.model.ImageProcessingModel;

/**
 *
 */
public class HorizontalFlipMacro extends AbstractFlipMacro {

  /**
   *
   * @param fileName
   * @param outPutName
   */
  public HorizontalFlipMacro(String fileName, String outPutName) throws IllegalArgumentException {
    super(fileName, outPutName);
  }

  /**
   * Executes a macro given an ImageProcessing model.
   *
   * @param model the ImageProcessing model that will be executed on.
   */
  @Override
  public void executeProcessingMacro(ImageProcessingModel model) {
    model.flip(FlipType.Horizontal, fileName, outPutName);
  }
}
