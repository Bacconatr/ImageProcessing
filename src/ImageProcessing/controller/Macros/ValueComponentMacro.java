package ImageProcessing.controller.Macros;

import ImageProcessing.model.ImageProcessingModel;
import ImageProcessing.model.VisualizationType;

/**
 *
 */
public class ValueComponentMacro extends AbstractComponentMacro {
  /**
   * Constructs a ValueComponentMacro that will perform a process on a file of the given name.
   *
   * @param fileName   the name of the file that will be executed on.
   * @param outPutName the name of the resulting file that will be added to the image map.
   */
  public ValueComponentMacro(String fileName, String outPutName) throws IllegalArgumentException {
    super(fileName, outPutName);
  }

  /**
   * Executes a macro given an ImageProcessing model.
   *
   * @param model the ImageProcessing model that will be executed on.
   */
  @Override
  public void executeProcessingMacro(ImageProcessingModel model) {
    model.createRepresentation(VisualizationType.Value, fileName, outPutName);
  }
}
