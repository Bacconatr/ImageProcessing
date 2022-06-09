package ImageProcessing.controller.Macros;

import ImageProcessing.model.ImageProcessingModel;
import ImageProcessing.model.VisualizationType;

/**
 *
 */
public class RedComponentMacro extends AbstractComponentMacro {

  /**
   * Constructs a RedComponentMacro.
   *
   * @param fileName the fileName of the image to be performed on.
   * @param outPutName the name of the image saved after the process has been performed.
   */
  public RedComponentMacro(String fileName, String outPutName) throws IllegalArgumentException {
    super(fileName, outPutName);
  }

  /**
   * Executes a macro given an ImageProcessing model.
   *
   * @param model the ImageProcessing model that will be executed on.
   */
  @Override
  public void executeProcessingMacro(ImageProcessingModel model) {
    model.createRepresentation(VisualizationType.Red, fileName, outPutName);
  }
}
