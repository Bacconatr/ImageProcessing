import java.io.InputStreamReader;
import java.io.StringReader;

import imageprocessing.controller.IProcessingImageController;
import imageprocessing.controller.ImageControllerImpl;
import imageprocessing.model.AdvancedImageProcessingModel;
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
    // if the user wants to use command line arguments, only allow for this
    if (args.length > 0) {
      StringBuilder script = new StringBuilder();
      for (String arg : args) {
        if (arg.equalsIgnoreCase("q") || arg.equalsIgnoreCase("quit")) {
          script.append(arg);
        } else {
          script.append(arg);
          script.append(" ");
        }
      }
      Readable in = new StringReader(script.toString());
      // ImageProcessingModel model = new ImageProcessingModelImpl();
      ImageProcessingModel model = new AdvancedImageProcessingModel();
      IProcessingImageView view = new BasicImageProcessingView();
      IProcessingImageController controller = new ImageControllerImpl(model, view, in);
      controller.startProcessing();
    }
    // if the user wants to type in inputs themselves then check for inputs
    else {
      Readable in = new InputStreamReader(System.in);
      // ImageProcessingModel model = new ImageProcessingModelImpl();
      ImageProcessingModel model = new AdvancedImageProcessingModel();
      IProcessingImageView view = new BasicImageProcessingView();
      IProcessingImageController controller = new ImageControllerImpl(model, view, in);
      controller.startProcessing();
    }
  }
}
