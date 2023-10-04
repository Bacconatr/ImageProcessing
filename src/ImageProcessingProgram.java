import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.Scanner;

import imageprocessing.controller.Features;
import imageprocessing.controller.IProcessingImageController;
import imageprocessing.controller.ImageControllerAdvancedImpl;
import imageprocessing.controller.GuiScriptController;
import imageprocessing.model.ImageProcessingExtraFeatures;
import imageprocessing.model.ImageProcessingExtraFeaturesImpl;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ImageProcessingModelImpl;
import imageprocessing.view.BasicImageProcessingView;
import imageprocessing.view.IJFrameView;
import imageprocessing.view.IProcessingImageView;
import imageprocessing.view.JFrameViewImpl;

/**
 * The object that contains the method to run the image processing program.
 */
public class ImageProcessingProgram {

  // #### REFACTORED ####
  // updated models in the main method to the one with extra features

  // ### REFACTORED ###
  // allows for the use of GUI

  // ### REFACTORED ###
  // MAIN METHOD SO THAT IT CAN TAKE IN A SCRIPT FILE AS WELL the user must be able to provide a
  // valid file path.

  /**
   * The main method for this program. Allows for the parsing of command line arguments, command
   * line arguments from a script file, or regular user inputs.
   *
   * @param args the user input.
   */
  public static void main(String[] args) {
    // if the user does not want to use the GUI
    if (args.length > 0) {

      // if the user wants to use the interactive text mode
      if (args[0].equals("-text")) {
        Readable in = new InputStreamReader(System.in);
        ImageProcessingExtraFeatures model = new ImageProcessingExtraFeaturesImpl();
        IProcessingImageView view = new BasicImageProcessingView();
        IProcessingImageController controller = new ImageControllerAdvancedImpl(model, view, in);
        controller.startProcessing();
      } else {
        // if the user wants to use command line arguments
        StringBuilder script = new StringBuilder();
        // if the user has a file to use containing commands
        if (args[0].equals("-file") && args[1].contains(".txt")) {
          Scanner sc;
          try {
            sc = new Scanner(new FileInputStream(args[1]));
          } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Sorry, the file at the given path was not found.");
          }
          //read the file line by line, and populate a string. This will throw away any comment lines
          while (sc.hasNext()) {
            String s = sc.next();
            script.append(s).append(System.lineSeparator());
          }
        }
        // if the user is typing out the arguments themselves
        else {
          for (String arg : args) {
            script.append(arg);
            script.append(" ");
          }
        }
        // provide the commands to the Readable and run the program
        Readable in = new StringReader(script.toString());
        ImageProcessingModel model = new ImageProcessingModelImpl();
        IProcessingImageView view = new BasicImageProcessingView();
        IProcessingImageController controller = new ImageControllerAdvancedImpl(model, view, in);
        controller.startProcessing();
        // ### REFACTORED ###
        // CHANGED SO THAT THE UPDATED CONTROLLER IS USED
      }

    }
    // if the user wishes to use the GUI
    else {
      ImageProcessingExtraFeatures model = new ImageProcessingExtraFeaturesImpl();
      IJFrameView view = new JFrameViewImpl();
      Features controller = new GuiScriptController(model);
      controller.setView(view);
    }
  }
}
