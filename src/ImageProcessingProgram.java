import java.io.InputStreamReader;

import ImageProcessing.controller.IProcessingImageController;
import ImageProcessing.controller.ImageController;
import ImageProcessing.model.ImageProcessingModel;
import ImageProcessing.model.ImageProcessingModelImpl;
import ImageProcessing.view.IProcessingImageView;
import ImageProcessing.view.IProcessingImageViewImpl;

/**
 * The object that contains the method to run the image processing program.
 */
public class ImageProcessingProgram {
  /**
   * The main method for this program.
   *
   * @param args the user input.
   */
  public static void main(String[] args) {
    Readable in = new InputStreamReader(System.in);
    ImageProcessingModel model = new ImageProcessingModelImpl();
    IProcessingImageView view = new IProcessingImageViewImpl();
    IProcessingImageController controller = new ImageController(model, view, in);
    controller.startProcessing();
  }
}
