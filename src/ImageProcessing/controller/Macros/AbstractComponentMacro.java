package ImageProcessing.controller.Macros;

import ImageProcessing.model.ImageProcessingModel;

/**
 * Represents an AbstractComponentMacro that will perform a process on a file of the given name. The
 * type of process that is executed depends on the implementation required for the class that
 * extends it, but generally some grey-scaling operation will be performed.
 */
public abstract class AbstractComponentMacro implements ImageProcessingMacro {
  protected final String fileName;
  protected final String outPutName;

  /**
   * Constructs a macro that will perform a process on a file of the given name.
   *
   * @param fileName   the name of the file that will be executed on.
   * @param outPutName the name of the resulting file that will be added to the image map.
   */
  public AbstractComponentMacro(String fileName, String outPutName)
          throws IllegalArgumentException {
    if (fileName == null) {
      throw new IllegalArgumentException("The File Name must be non-null.");
    }
    if (outPutName == null) {
      throw new IllegalArgumentException("The output name must be non-null");
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
