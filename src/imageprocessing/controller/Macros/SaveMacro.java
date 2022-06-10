package imageprocessing.controller.Macros;

import imageprocessing.model.ImageProcessingModel;

/**
 *
 */
public class SaveMacro implements ImageProcessingMacro {
  private final String fileName;
  private final String filePath;

  /**
   * Constructs a SaveMacro.
   *
   * @param filePath the path that the user wants to save the file to (including the name of the
   *                 new file).
   * @param fileName the name of the file in the image map that will be saved.
   */
  public SaveMacro(String filePath, String fileName) throws IllegalArgumentException {
    if (filePath == null) {
      throw new IllegalArgumentException("The File Path must be non-null");
    }
    if (fileName == null) {
      throw new IllegalArgumentException("The File Name must be non-null");
    }
    this.fileName = fileName;
    this.filePath = filePath;
  }

  /**
   * Executes a macro given an ImageProcessing model.
   *
   * @param model the ImageProcessing model that will be executed on.
   */
  @Override
  public void executeProcessingMacro(ImageProcessingModel model) {
    model.savePPM(filePath, fileName);
  }
}
