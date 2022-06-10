package imageprocessing.controller;

import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

import imageprocessing.controller.Macros.AdjustLightMacro;
import imageprocessing.controller.Macros.ComponentMacro;
import imageprocessing.controller.Macros.FlipMacro;
import imageprocessing.controller.Macros.ImageProcessingMacro;
import imageprocessing.controller.Macros.LoadMacro;
import imageprocessing.controller.Macros.SaveMacro;
import imageprocessing.model.ComponentBiFunctions.BlueBiFunction;
import imageprocessing.model.ComponentBiFunctions.GreenBiFunction;
import imageprocessing.model.ComponentBiFunctions.IntensityBiFunction;
import imageprocessing.model.ComponentBiFunctions.LumaBiFunction;
import imageprocessing.model.ComponentBiFunctions.RedBiFunction;
import imageprocessing.model.ComponentBiFunctions.ValueBiFunction;
import imageprocessing.model.FlipType;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.view.IProcessingImageView;

/**
 * Represents a basic controller that allows a user to load in an image and type in commands to
 * perform processes on that image. If a user has an incorrect command then the user is prompted to
 * input the correct command. Once a correct command is registered, if the inputs are non-valid then
 * an IllegalArgumentException is thrown and the program exits.
 */
public class ImageController implements IProcessingImageController {
  private final ImageProcessingModel model;
  private final IProcessingImageView view;
  private final Readable readable;
  private final Map<String, Function<Scanner, ImageProcessingMacro>> imageProcessingCommands;

  /**
   * Constructs an ImageController.
   *
   * @param model the model of the image processor (which has a Map of the images that the user is
   *              performing processes on).
   * @param view  the view used in this program.
   * @param ap    what the view will append to when called on.
   */
  public ImageController(ImageProcessingModel model, IProcessingImageView view, Readable ap) {
    if (model == null || view == null || ap == null) {
      throw new IllegalArgumentException("Cannot have null arguments");
    }

    this.model = model;
    this.view = view;
    this.readable = ap;
    imageProcessingCommands = new HashMap<>();
    // Loading and saving
    imageProcessingCommands.put("load", sc -> new LoadMacro(sc.next(), sc.next()));
    imageProcessingCommands.put("save", sc -> new SaveMacro(sc.next(), sc.next()));
    // adjusting the light
    imageProcessingCommands.put("brighten",
            sc -> new AdjustLightMacro(sc.nextInt(), sc.next(), sc.next(), true));
    imageProcessingCommands.put("darken",
            sc -> new AdjustLightMacro(sc.nextInt(), sc.next(), sc.next(), false));
    // grey scaling
    imageProcessingCommands.put("red-component", sc -> new ComponentMacro(sc.next(), sc.next(),
            new RedBiFunction()));
    imageProcessingCommands.put("blue-component", sc -> new ComponentMacro(sc.next(),
            sc.next(), new BlueBiFunction()));
    imageProcessingCommands.put("green-component", sc -> new ComponentMacro(sc.next(),
            sc.next(), new GreenBiFunction()));
    imageProcessingCommands.put("value-component", sc -> new ComponentMacro(sc.next(),
            sc.next(), new ValueBiFunction()));
    imageProcessingCommands.put("intensity-component", sc -> new ComponentMacro(sc.next(),
            sc.next(), new IntensityBiFunction()));
    imageProcessingCommands.put("luma-component", sc -> new ComponentMacro(sc.next(),
            sc.next(), new LumaBiFunction()));
    // image flipping
    imageProcessingCommands.put("horizontal-flip", sc -> new FlipMacro(sc.next(), sc.next(),
            FlipType.Horizontal));
    imageProcessingCommands.put("vertical-flip", sc -> new FlipMacro(sc.next(),
            sc.next(), FlipType.Vertical));

  }

  /**
   * Initiates the image processing that will occur when this controller has the model, the view,
   * and the readable. User input will be taken until the user decides to quit out of the program by
   * typing either "q" or "quit" (not case sensitive). If a user types in a command that is not
   * known the user will be re-prompted to type in another command.
   */
  public void startProcessing() throws IllegalStateException {
    Scanner sc = new Scanner(this.readable);
    boolean quit = false;
    String userInput = "";
    while (!quit) {
      try {
        userInput = sc.next();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Need additional inputs.");
      }

      if (userInput.equalsIgnoreCase("q") || userInput.equalsIgnoreCase("quit")) {
        quit = true;
      } else {
        Function<Scanner, ImageProcessingMacro> cmd =
                imageProcessingCommands.getOrDefault(userInput, null);
        if (cmd == null) {
          view.renderMessage("The command specified was not valid. Please try again.\n");
          continue;
        }
        ImageProcessingMacro macro;
        try {
          macro = cmd.apply(sc);
        } catch (InputMismatchException e) {
          view.renderMessage("The input type was incorrect.\n");
          // THIS IS A DESIGN CHOICE
          // without sc.nextLine() here the incorrect argument is processed as a command.
          // we do not want this behavior so the input is removed from the scanner
          // if a command provided has illegal arguments or too many arguments
          sc.nextLine();
          continue;
        }
        try {
          macro.executeProcessingMacro(model);
          view.renderMessage("Command successful.\n");
        } catch (IllegalArgumentException e) {
          view.renderMessage("The arguments for the command were not valid. " + e.getMessage() +
                  "\n");
          // THIS IS A DESIGN CHOICE.
          // We implemented this so that the user will have to press enter (add a new line) in
          // order to move on to the next command. If the arguments of a command are invalid then
          // we want the user to start on a new line instead of linking multiple commands after.

          // It is okay if a user links multiple *valid* commands, but with regard to errors we
          // want them to start fresh on a new line.
          sc.nextLine();
        }
      }
    }
  }
}

//  load res/Koala.ppm koala horizontal-flip koal koala koala-horizontal-flip save
//  res\koala-horizontal-flip.ppm koala-horizontal-flip q