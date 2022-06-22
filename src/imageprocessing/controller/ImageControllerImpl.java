package imageprocessing.controller;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.util.function.Function;

import imageprocessing.controller.macros.AdjustLightMacro;
import imageprocessing.controller.macros.ComponentMacro;
import imageprocessing.controller.macros.FlipMacro;
import imageprocessing.controller.macros.ImageProcessingMacro;
import imageprocessing.model.ImageProcessingModelImpl;
import imageprocessing.model.componentbifunctions.BlueBiFunction;
import imageprocessing.model.componentbifunctions.ColorTransformationBiFunction;
import imageprocessing.model.componentbifunctions.FilterBiFunction;
import imageprocessing.model.componentbifunctions.GreenBiFunction;
import imageprocessing.model.componentbifunctions.IntensityBiFunction;
import imageprocessing.model.componentbifunctions.LumaBiFunction;
import imageprocessing.model.componentbifunctions.RedBiFunction;
import imageprocessing.model.componentbifunctions.ValueBiFunction;
import imageprocessing.model.FlipType;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.view.IProcessingImageView;

/**
 * Represents a basic controller that allows a user to load in an image and type in commands to
 * perform processes on that image. If a user has an incorrect command then the user is prompted to
 * input the correct command. Once a correct command is registered, if the inputs are non-valid then
 * an IllegalArgumentException is thrown and the program exits.
 */
public class ImageControllerImpl implements IProcessingImageController {
  // #### REFACTORED ###
  // CHANGED FIELDS FROM PRIVATE TO PROTECTED SO THEY CAN BE INHERITED BY CLASSES THAT EXTEND
  // THIS ONE
  protected final ImageProcessingModel model;
  protected final IProcessingImageView view;
  protected final Readable readable;
  protected final Map<String, Function<Scanner, ImageProcessingMacro>> imageProcessingCommands;

  // ######## Added matrices for blurring, sharpening, and color transformations ########
  public static double[][] BLUR_KERNEL = {{(double) 1 / 16, (double) 1 / 8, (double) 1 / 16},
                                          {(double) 1 / 8, (double) 1 / 4, (double) 1 / 8},
                                          {(double) 1 / 16, (double) 1 / 8, (double) 1 / 16}};

  public static double[][] SHARPEN_KERNEL = {{(double) -1 / 8, (double) -1 / 8, (double) -1 / 8,
                                              (double) -1 / 8, (double) -1 / 8},
                                             {(double) -1 / 8, (double) 1 / 4, (double) 1 / 4,
                                              (double) 1 / 4, (double) -1 / 8},
                                             {(double) -1 / 8, (double) 1 / 4, 1.0, (double) 1 / 4,
                                              (double) -1 / 8},
                                             {(double) -1 / 8, (double) 1 / 4, (double) 1 / 4,
                                              (double) 1 / 4, (double) -1 / 8},
                                             {(double) -1 / 8, (double) -1 / 8, (double) -1 / 8,
                                              (double) -1 / 8, (double) -1 / 8}};
  public static double[][] GREYSCALE = {{0.2126, 0.7152, 0.0722},
                                        {0.2126, 0.7152, 0.0722},
                                        {0.2126, 0.7152, 0.0722}};
  public static double[][] SEPIA_TONE = {{0.393, 0.769, 0.189},
                                         {0.349, 0.686, 0.168},
                                         {0.272, 0.534, 0.131}};

  /**
   * Constructs an ImageControllerImpl.
   *
   * @param model the model of the image processor (which has a Map of the images that the user is
   *              performing processes on).
   * @param view  the view used in this program.
   * @param ap    what the view will append to when called on.
   */
  public ImageControllerImpl(ImageProcessingModel model, IProcessingImageView view, Readable ap) {
    if (model == null || view == null || ap == null) {
      throw new IllegalArgumentException("Cannot have null arguments");
    }

    this.model = model;
    this.view = view;
    this.readable = ap;
    imageProcessingCommands = new HashMap<>();
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

    // ############### REFACTORED ###########################
    // added another command
    imageProcessingCommands.put("blur", sc -> new ComponentMacro(sc.next(), sc.next(),
            new FilterBiFunction(BLUR_KERNEL)));
    imageProcessingCommands.put("sharpen", sc -> new ComponentMacro(sc.next(), sc.next(),
            new FilterBiFunction(SHARPEN_KERNEL)));

    imageProcessingCommands.put("greyscale", sc -> new ComponentMacro(sc.next(), sc.next(),
            new ColorTransformationBiFunction(GREYSCALE)));
    imageProcessingCommands.put("sepia", sc -> new ComponentMacro(sc.next(), sc.next(),
            new ColorTransformationBiFunction(SEPIA_TONE)));


  }

  // #### REFACTORED ####
  // added checks for save and load in this method since save and load can no longer be macros
  // since the model no longer contains methods that allow for loading and saving

  /**
   * Initiates the image processing that will occur when this controller has the model, the view,
   * and the readable. User input will be taken until the user decides to quit out of the program by
   * typing either "q" or "quit" (not case sensitive). If a user types in a command that is not
   * known the user will be re-prompted to type in another command.
   */
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

  // ### REFACTORED ###
  // added save and load to the controller since the controller should be handling user inputs.
  // Previously was in the model as "readPPM" and "savePPM"

  /**
   * allows for the reading of a ppm file in order to convert to a 2d Pixel array.
   *
   * @param filePath  the path of the PPM file that will be read.
   * @param imageName the name of the image that will be loaded and represented as data.
   * @throws IllegalArgumentException if the file is not found
   */
  protected void load(String filePath, String imageName) throws IllegalArgumentException {

    // Code originally provided in ImageUtil, but we have changed to fit our program.

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      //  System.out.println("File " + filePath + " not found!");
      throw new IllegalArgumentException("Sorry, the file at the given path was not found.");
    }
    StringBuilder builder = new StringBuilder();
    //read the file line by line, and populate a string. This will throw away any comment lines
    while (sc.hasNextLine()) {
      String s = sc.nextLine();
      if (s.charAt(0) != '#') {
        builder.append(s).append(System.lineSeparator());
      }
    }

    //now set up the scanner to read from the string we just built
    sc = new Scanner(builder.toString());

    String token;

    token = sc.next();
    if (!token.equals("P3")) {
      System.out.println("Invalid PPM file: plain RAW file should begin with P3");
    }
    int width = sc.nextInt();
    int height = sc.nextInt();
    int max = sc.nextInt();

    ImageProcessingModelImpl.Pixel[][] listOfPixels =
            new ImageProcessingModelImpl.Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        listOfPixels[i][j] = new ImageProcessingModelImpl.Pixel(r, g, b);
      }
    }
    model.addImage(listOfPixels, imageName);
  }

  /**
   * allows for the saving of an image.
   *
   * @param filePath  the path that the image will be saved to. This path should include the name of
   *                  the new file that will be created when it is stored locally. (e.g. C:/file.ppm
   *                  is the required path to save the file to the C drive).
   * @param imageName the name of the image that should be saved.
   * @throws IllegalArgumentException if the name of the image the user wants to save is not in our
   *                                  list of images.
   */
  protected void save(String filePath, String imageName) throws IllegalArgumentException {

    ImageProcessingModelImpl.Pixel[][] fileToSave = model.imageState(imageName);
    if (fileToSave == null) {
      throw new IllegalArgumentException("The image you are trying to save does not exist.");
    } else {
      File file = new File(filePath);
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("P3");
        writer.newLine();
        writer.write(fileToSave[0].length + " " +
                fileToSave.length);
        writer.newLine();
        writer.write(String.valueOf(255));
        writer.newLine();
        for (ImageProcessingModelImpl.Pixel[] pixels : fileToSave) {
          for (int j = 0; j < fileToSave[0].length; j++) {
            writer.write(String.valueOf(pixels[j].getRed()));
            writer.newLine();
            writer.write(String.valueOf(pixels[j].getGreen()));
            writer.newLine();
            writer.write(String.valueOf(pixels[j].getBlue()));
            writer.newLine();
          }
        }

        writer.close();

      } catch (IOException e) {
        throw new IllegalArgumentException("Error, the file path provided does not exist.");
      }

    }
  }
}