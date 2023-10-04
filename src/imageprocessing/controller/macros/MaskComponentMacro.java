package imageprocessing.controller.macros;

import java.util.function.BiFunction;

import imageprocessing.model.ImageProcessingExtraFeatures;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ImageProcessingModelImpl;

/**
 * Applies
 */
public class MaskComponentMacro implements ImageProcessingMacro {
  private final String fileName;
  private final String maskName;
  private final String outPutName;
  private final BiFunction<ImageProcessingModelImpl.Posn, ImageProcessingModelImpl.Pixel[][],
          ImageProcessingModelImpl.Pixel>
          biFunction;

  /**
   * Constructs a macro that will perform a process on a file of the given name.
   *
   * @param fileName   the name of the file that will be executed on.
   * @param outPutName the name of the resulting file that will be added to the image map.
   */
  public MaskComponentMacro(String fileName, String outPutName, String maskName,
                            BiFunction<ImageProcessingModelImpl.Posn,
                                    ImageProcessingModelImpl.Pixel[][],
                                    ImageProcessingModelImpl.Pixel> biFunction)
          throws IllegalArgumentException {
    if (fileName == null) {
      throw new IllegalArgumentException("The File Name must be non-null.");
    }
    if (maskName == null) {
      throw new IllegalArgumentException("The Mask Name must be non-null.");
    }
    if (outPutName == null) {
      throw new IllegalArgumentException("The output name must be non-null");
    }
    this.fileName = fileName;
    this.maskName = maskName;
    this.outPutName = outPutName;
    this.biFunction = biFunction;
  }

  /**
   * Executes a macro given an ImageProcessing model.
   *
   * @param model the ImageProcessing model that will be executed on.
   */
  @Override
  public void executeProcessingMacro(ImageProcessingModel model) {
    model.createRepresentation(fileName, outPutName, biFunction);
  }

}
