package imageprocessing.controller.Macros;

import imageprocessing.model.ImageProcessingModel;

/**
 *
 */
public class LoadMacro implements ImageProcessingMacro {
  private final String imagePath;
  private final String fileName;

  /**
   * Constructs a LoadMacro.
   * @param imagePath the file path to this image.
   * @param fileName the name of this image.
   */
  public LoadMacro(String imagePath, String fileName) throws IllegalArgumentException {
    if (imagePath == null) {
      throw new IllegalArgumentException("The image path provided must be non-null");
    }
    if (fileName == null) {
      throw new IllegalArgumentException("The File Name provided must be non-null");
    }
    this.imagePath = imagePath;
    this.fileName = fileName;
  }

  /**
   * Executes a macro given an ImageProcessing model.
   *
   * @param model the ImageProcessing model that will be executed on.
   */
  @Override
  public void executeProcessingMacro(ImageProcessingModel model) {
    model.readPPM(imagePath, fileName);
  }
}