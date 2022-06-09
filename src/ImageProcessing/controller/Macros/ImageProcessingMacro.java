package ImageProcessing.controller.Macros;

import ImageProcessing.model.ImageProcessingModel;

/**
 * Represents a macro that will execute on an ImageProcessingModel.
 */
public interface ImageProcessingMacro {

  /**
   * Executes a macro given an ImageProcessing model.
   *
   * @param model the ImageProcessing model that will be executed on.
   */
  void executeProcessingMacro(ImageProcessingModel model);
}
