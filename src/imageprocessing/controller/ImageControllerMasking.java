package imageprocessing.controller;

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

import imageprocessing.controller.macros.ImageProcessingMacro;
import imageprocessing.model.ImageProcessingExtraFeatures;
import imageprocessing.model.ImageProcessingModelImpl;
import imageprocessing.view.IProcessingImageView;

public class ImageControllerMasking extends ImageControllerAdvancedImpl implements IProcessingImageController {
  // IProcessingImageController delegate;
  private ImageProcessingExtraFeatures model;

  /**
   *
   * @param model
   * @param view
   * @param rd
   */
  public ImageControllerMasking(ImageProcessingExtraFeatures model, IProcessingImageView view,
                                Readable rd) {
    super(model, view, rd);
    this.model = model;
    // delegate = new ImageControllerAdvancedImpl(model, view, rd);
  }

  /**
   * Starts the program.
   *
   * @throws IllegalStateException if there are any user input errors
   */
  @Override
  public void startProcessing() throws IllegalStateException {
    Scanner sc = new Scanner(this.readable);

    view.introMessage();
    view.renderMessage("\n");

    boolean quit = false;
    String userInput;
    // while loop that continues to take arguments until the user quits
    while (!quit) {
      // the command user input (should not be the arguments of a command)
      try {
        userInput = sc.next();
      } catch (NoSuchElementException e) {
        throw new IllegalStateException("Need additional inputs.");
      }

      // if the user input is "q" or "quit" (case insensitive)
      if (userInput.equalsIgnoreCase("q") || userInput.equalsIgnoreCase("quit")) {
        quit = true;
        view.renderMessage("Thank you for using the program!\n");
      } else if (userInput.equals("load")) {
        // the first argument for load
        String filePath = "";
        try {
          filePath = sc.next();
        } catch (NoSuchElementException e) {
          throw new IllegalStateException("Need additional inputs.");
        }

        // the second argument for load
        String newImageName;
        try {
          newImageName = sc.next();
        } catch (NoSuchElementException e) {
          throw new IllegalStateException("Need additional inputs.");
        }

        // if the filePath does not exist the view renders an error
        try {
          load(filePath, newImageName);
          view.renderMessage("load command successful.\n");
        } catch (IllegalArgumentException e) {
          view.renderMessage("The arguments for the command were not valid. " + e.getMessage() +
                  "\n");
        }
        // if the user input is save
      } else if (userInput.equals("save")) {
        // the first argument for save
        String filePath;
        try {
          filePath = sc.next();
        } catch (NoSuchElementException e) {
          throw new IllegalStateException("Need additional inputs.");
        }

        // the second argument for save
        String newImageName;
        try {
          newImageName = sc.next();
        } catch (NoSuchElementException e) {
          throw new IllegalStateException("Need additional inputs.");
        }

        // if the filePath does not exist the view renders an error
        try {
          save(filePath, newImageName);
          view.renderMessage("save command successful.\n");
        } catch (IllegalArgumentException e) {
          view.renderMessage("The arguments for the command were not valid. " + e.getMessage() +
                  "\n");
        }
      } else {
        Function<Scanner, ImageProcessingMacro> cmd =
                imageProcessingCommands.getOrDefault(userInput, null);
        if (cmd == null) {
          // DESIGN CHOICE
          // if a user is unable to even input a basic command we do not require them to start on
          // a new line.
          view.renderMessage("The command specified was not valid. Please try again.\n");
          continue;
        }
        ImageProcessingMacro macro;
        try {
          macro = cmd.apply(sc);
        } catch (InputMismatchException e) {
          view.renderMessage("The input type was incorrect.\n");
          // THIS IS A DESIGN CHOICE
          // without sc.nextLine() here the incorrect argument is processed as a command. We do
          // not want this behavior.

          sc.nextLine();
          continue;
        }
        try {
          macro.executeProcessingMacro(model);
          view.renderMessage(userInput + " command successful.\n");
        } catch (IllegalArgumentException e) {
          view.renderMessage("The arguments for the command were not valid. " + e.getMessage() +
                  "\n");
          // THIS IS A DESIGN CHOICE.
          // We implemented this so that the user will have to press enter (add a new line) in
          // order to move on to the next command.

          sc.nextLine();
        }
      }
    }
    sc.close();
  }
}
