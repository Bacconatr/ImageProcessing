package ImageProcessing.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

import ImageProcessing.controller.Macros.BlueComponentMacro;
import ImageProcessing.controller.Macros.BrightenMacro;
import ImageProcessing.controller.Macros.DarkenMacro;
import ImageProcessing.controller.Macros.GreenComponentMacro;
import ImageProcessing.controller.Macros.HorizontalFlipMacro;
import ImageProcessing.controller.Macros.ImageProcessingMacro;
import ImageProcessing.controller.Macros.IntensityComponentMacro;
import ImageProcessing.controller.Macros.LoadMacro;
import ImageProcessing.controller.Macros.LumaComponentMacro;
import ImageProcessing.controller.Macros.RedComponentMacro;
import ImageProcessing.controller.Macros.SaveMacro;
import ImageProcessing.controller.Macros.ValueComponentMacro;
import ImageProcessing.controller.Macros.VerticalFlipMacro;
import ImageProcessing.model.ImageProcessingModel;
import ImageProcessing.view.IProcessingImageView;

/**
 * Represents a basic controller that allows a user to load in an image and type in commands to
 * perform processes on that image. If a user has an incorrect command then the user is prompted
 * to input the correct command. Once a correct command is registered, if the inputs are
 * non-valid then an IllegalArgumentException is thrown and the program exits.
 */
public class ImageController implements IProcessingImageController {
  private final ImageProcessingModel model;
  private final IProcessingImageView view;
  private final Readable readable;
  private final Map<String, Function<Scanner, ImageProcessingMacro>> imageProcessingCommands;

  /**
   * Constructs an ImageController.
   *
   * @param model the model of the image processor (which has a Map of the images that the user
   *              is performing processes on).
   * @param view the view used in this program.
   * @param ap what the view will append to when called on.
   */
  public ImageController(ImageProcessingModel model, IProcessingImageView view, Readable ap) {
    this.model = model;
    this.view = view;
    this.readable = ap;
    imageProcessingCommands = new HashMap<>();
    // Loading and saving
    imageProcessingCommands.put("load", sc -> new LoadMacro(sc.next(), sc.next()));
    imageProcessingCommands.put("save", sc -> new SaveMacro(sc.next(), sc.next()));
    // adjusting the light
    imageProcessingCommands.put("brighten",
            sc -> new BrightenMacro(sc.nextInt(), sc.next(), sc.next()));
    imageProcessingCommands.put("darken",
            sc -> new DarkenMacro(sc.nextInt(), sc.next(), sc.next()));
    // grey scaling
    imageProcessingCommands.put("red-component", sc -> new RedComponentMacro(sc.next(), sc.next()));
    imageProcessingCommands.put("blue-component", sc -> new BlueComponentMacro(sc.next(),
            sc.next()));
    imageProcessingCommands.put("green-component", sc -> new GreenComponentMacro(sc.next(),
            sc.next()));
    imageProcessingCommands.put("value-component", sc -> new ValueComponentMacro(sc.next(),
            sc.next()));
    imageProcessingCommands.put("intensity-component", sc -> new IntensityComponentMacro(sc.next(),
            sc.next()));
    imageProcessingCommands.put("luma-component", sc -> new LumaComponentMacro(sc.next(),
            sc.next()));
    // image flipping
    imageProcessingCommands.put("horizontal-flip",
            sc -> new HorizontalFlipMacro(sc.next(), sc.next()));
    imageProcessingCommands.put("vertical-flip", sc -> new VerticalFlipMacro(sc.next(), sc.next()));

  }

  /**
   * Initiates the image processing that will occur when this controller has the model, the view,
   * and the readable. User input will be taken until the user decides to quit out of the program
   * by typing either "q" or "quit" (not case sensitive). If a user types in a command that is
   * not known the user will be re-prompted to type in another command.
   */
  public void startProcessing() throws IllegalStateException {
    Scanner sc = new Scanner(this.readable);

    boolean quit = false;
    while (!quit) {
      String userInput;
      try {
        userInput = sc.next().toLowerCase();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Need additional inputs.");
      }

      if (userInput.equalsIgnoreCase("q") || userInput.equalsIgnoreCase("quit")) {
        quit = true;
      } else {
        Function<Scanner, ImageProcessingMacro> cmd =
                imageProcessingCommands.getOrDefault(userInput, null);
        if (cmd == null) {
          view.renderMessage("This command was not valid. Please try again.");
          continue;
        }
        ImageProcessingMacro macro = cmd.apply(sc);
        macro.executeProcessingMacro(model);
      }
    }
  }
}


