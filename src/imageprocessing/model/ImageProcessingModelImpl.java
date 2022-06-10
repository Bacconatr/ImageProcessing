package imageprocessing.model;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Scanner;
import java.util.function.BiFunction;

/**
 *
 */
public class ImageProcessingModelImpl implements ImageProcessingModel {
  private final HashMap<String, Pixel[][]> mapOfImages;

  /**
   * Constructs an ImageProcessingModelImpl.
   */
  public ImageProcessingModelImpl() {
    mapOfImages = new HashMap<String, Pixel[][]>();
  }

  @Override
  public Pixel[][] imageState(String imageName) throws IllegalArgumentException {
    Pixel[][] state = imageDeepCopy(mapOfImages.getOrDefault(imageName, null));
    if (state == null) {
      throw new IllegalArgumentException("The specified image does not exist.");
    }
    return state;
  }

  @Override
  public int numImages() {
    return mapOfImages.size();
  }


  @Override
  public void createRepresentation(String imageName, String newImageName,
                                   BiFunction<Posn, Pixel[][], Pixel> representation) {
    Pixel[][] image = imageDeepCopy(mapOfImages.getOrDefault(imageName, null));
    if (image == null) {
      throw new IllegalArgumentException("The file for this component grey-scaling was not found.");
    }
    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        image[i][j] = representation.apply(new Posn(i, j), image);
      }
    }
    mapOfImages.put(newImageName, image);

  }

  @Override
  public void flip(FlipType flip, String imageName, String newImageName) {
    Pixel[][] image = imageDeepCopy(mapOfImages.getOrDefault(imageName, null));

    if (image == null) {
      throw new IllegalArgumentException("sorry the file you are trying to flip does not exist");
    }
    switch (flip) {
      // vertical flip
      case Vertical:
        for (int i = 0; i < image.length / 2; i++) {
          for (int j = 0; j < image[i].length; j++) {
            Pixel temp = image[i][j];
            image[i][j] = image[image.length - 1 - i][j];
            image[image.length - 1 - i][j] = temp;
          }
        }
        break;

      // horizontal flip
      case Horizontal:
        for (int i = 0; i < image.length; i++) {
          for (int j = 0; j < image[i].length / 2; j++) {
            Pixel temp = image[i][j];
            image[i][j] = image[i][image[0].length - 1 - j];
            image[i][image[0].length - 1 - j] = temp;
          }
        }
        break;
      default:
        throw new IllegalArgumentException("Must be a valid flip type");
    }

    this.mapOfImages.put(newImageName, image);

  }

  @Override
  public void adjustLight(int value, String imageName, String newImageName)
          throws IllegalArgumentException {

    Pixel[][] image = imageDeepCopy(mapOfImages.getOrDefault(imageName, null));

    if (image == null) {
      throw new IllegalArgumentException("Didn't provide the name of an existing image.");
    }

    for (int i = 0; i < image.length; i++) {
      for (int j = 0; j < image[0].length; j++) {
        Pixel currentPixel = image[i][j];
        int tempRed = currentPixel.getRed() + value;
        if (tempRed >= 255) {
          tempRed = 255;
        } else if (tempRed <= 0) {
          tempRed = 0;
        }
        int tempGreen = currentPixel.getGreen() + value;
        if (tempGreen >= 255) {
          tempGreen = 255;
        } else if (tempGreen <= 0) {
          tempGreen = 0;
        }

        int tempBlue = currentPixel.getBlue() + value;
        if (tempBlue >= 255) {
          tempBlue = 255;
        } else if (tempBlue <= 0) {
          tempBlue = 0;
        }

        image[i][j] = new Pixel(tempRed, tempGreen, tempBlue);
      }
    }
    this.mapOfImages.put(newImageName, image);

  }

  @Override
  public void readPPM(String filePath, String imageName) {

    // Code originally provided in ImageUtil, but we have changed to fit our program.

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      //  System.out.println("File " + filePath + " not found!");
      throw new IllegalArgumentException("sorry file not found");
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
    // System.out.println("Width of image: " + width);
    int height = sc.nextInt();
    // System.out.println("Height of image: " + height);
    int maxValue = sc.nextInt();
    // System.out.println("Maximum value of a color in this file (usually 255): " + maxValue);

    Pixel[][] listOfPixels = new Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        listOfPixels[i][j] = new Pixel(r, g, b);
      }
    }
    mapOfImages.put(imageName, listOfPixels);
  }

  public void savePPM(String filePath, String imageName) throws IllegalArgumentException {

    Pixel[][] fileToSave = mapOfImages.getOrDefault(imageName, null);
    if (fileToSave == null) {
      throw new IllegalArgumentException("The image you are trying to save does not exist.");
    } else {
      File file = new File(filePath);
      try {
        BufferedWriter writer = new BufferedWriter(new FileWriter(file));
        writer.write("P3");
        writer.newLine();
        writer.write(String.valueOf(fileToSave[0].length) + " " +
                String.valueOf(fileToSave.length));
        writer.newLine();
        writer.write(String.valueOf(255));
        writer.newLine();
        for (int i = 0; i < fileToSave.length; i++) {
          for (int j = 0; j < fileToSave[0].length; j++) {
            writer.write(String.valueOf(fileToSave[i][j].getRed()));
            writer.newLine();
            writer.write(String.valueOf(fileToSave[i][j].getGreen()));
            writer.newLine();
            writer.write(String.valueOf(fileToSave[i][j].getBlue()));
            writer.newLine();
          }
        }

        writer.close();

      } catch (IOException e) {
        throw new IllegalArgumentException("Error, the file path provided does not exist.");
      }

    }
  }

  // creates a deep copy of an image. If the image provided is null return null.
  private Pixel[][] imageDeepCopy(Pixel[][] image) {
    if (image == null) {
      return null;
    }
    Pixel[][] temp = new Pixel[image.length][image[0].length];
    for (int i = 0; i < image.length; i++) {
      System.arraycopy(image[i], 0, temp[i], 0, image[i].length);
    }
    return temp;
  }

}
