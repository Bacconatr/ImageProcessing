import java.io.InputStreamReader;

import imageprocessing.controller.IProcessingImageController;
import imageprocessing.controller.ImageController;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ImageProcessingModelImpl;
import imageprocessing.view.BasicImageProcessingView;
import imageprocessing.view.IProcessingImageView;

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
    IProcessingImageView view = new BasicImageProcessingView();
    IProcessingImageController controller = new ImageController(model, view, in);
    controller.startProcessing();
  }
}
