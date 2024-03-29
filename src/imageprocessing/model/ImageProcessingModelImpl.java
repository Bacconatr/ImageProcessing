package imageprocessing.model;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Scanner;
import java.util.function.BiFunction;

/**
 *
 */
public class ImageProcessingModelImpl implements ImageProcessingModel {
  // ######### REFACTORED FROM PRIVATE TO PROTECTED SO IT CAN BE INHERITED #############
  protected final HashMap<String, Pixel[][]> mapOfImages;


  /**
   * Constructs an ImageProcessingModelImpl.
   */
  public ImageProcessingModelImpl() {
    mapOfImages = new HashMap<>();
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
    // #### REFACTORED ###
    // CHANGED SO THAT THE ORIGINAL IMAGE IS NOT MUTATED AS WE ITERATE OVER THE FOR LOOP
    Pixel[][] originalImage = imageDeepCopy(mapOfImages.getOrDefault(imageName, null));
    if (originalImage == null) {
      throw new IllegalArgumentException("The file for this command was not found.");
    }
    Pixel[][] newImage = new Pixel[originalImage.length][originalImage[0].length];
    for (int i = 0; i < originalImage.length; i++) {
      for (int j = 0; j < originalImage[0].length; j++) {
        newImage[i][j] = representation.apply(new Posn(i, j), originalImage);
      }
    }
    mapOfImages.put(newImageName, newImage);
  }

  /**
   * Allows for adjusting the light of an image.
   *
   * @param value        the amount of brightness/darkness that will be applied (negative values is
   *                     equivalent to darkening while positive values brighten the image).
   * @param imageName    the name of the image to be brightened.
   * @param newImageName the name of the new image that is produced after the image is brightened.
   * @throws IllegalArgumentException if the user inputted name of the image they want to change is
   *                                  not in our list of images
   */
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

  /**
   * Method to flip an image.
   *
   * @param flip         the type of flipping that will be performed (currently horizontal or
   *                     vertical)
   * @param imageName    the name of the image to be flipped.
   * @param newImageName the name of the new image once the flipping has been performed.
   */
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

  /**
   * allows for the reading of a ppm file in order to convert to a 2d Pixel array.
   *
   * @param filePath  the path of the PPM file that will be read.
   * @param imageName the name of the image that will be loaded and represented as data.
   * @throws IllegalArgumentException if the file is not found
   */
  @Override
  public void readPPM(String filePath, String imageName) throws IllegalArgumentException {

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

    ImageProcessingModelImpl.Pixel[][] listOfPixels = new ImageProcessingModelImpl.Pixel[height][width];

    for (int i = 0; i < height; i++) {
      for (int j = 0; j < width; j++) {
        int r = sc.nextInt();
        int g = sc.nextInt();
        int b = sc.nextInt();

        listOfPixels[i][j] = new ImageProcessingModelImpl.Pixel(r, g, b);
      }
    }
    mapOfImages.put(imageName, listOfPixels);
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
        writer.write(fileToSave[0].length + " " +
                fileToSave.length);
        writer.newLine();
        writer.write(String.valueOf(255));
        writer.newLine();
        for (Pixel[] pixels : fileToSave) {
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

  // creates a deep copy of an image. If the image provided is null return null.
  protected Pixel[][] imageDeepCopy(Pixel[][] image) {
    if (image == null) {
      return null;
    }
    Pixel[][] temp = new Pixel[image.length][image[0].length];
    for (int i = 0; i < image.length; i++) {
      System.arraycopy(image[i], 0, temp[i], 0, image[i].length);
    }
    return temp;
  }

  /**
   * Represents a pixel of an image. This pixel contains RGB values with a range of 0-255 (8 bit).
   */
  public static class Pixel {
    private int red;
    private int green;
    private int blue;

    /**
     * Constructs a Pixel.
     *
     * @param red   the red value of this pixel.
     * @param green the green value of this pixel.
     * @param blue  the blue value of this pixel.
     */
    public Pixel(int red, int green, int blue) {
      // ### REFACTORED ###
      // SO THAT INSTEAD OF THROWING AN EXCEPTION WE APPLY A CLAMP
      this.red = clamp(red);
      this.green = clamp(green);
      this.blue = clamp(blue);
    }

    /**
     * Provides the red value of this pixel.
     *
     * @return the red value.
     */
    public int getRed() {
      return red;
    }

    /**
     * Provides the green value of this pixel.
     *
     * @return the green value.
     */
    public int getGreen() {
      return green;
    }

    /**
     * Provides the blue value of this pixel.
     *
     * @return the blue value.
     */
    public int getBlue() {
      return blue;
    }

    // ######## REFACTORED ALL SETTERS SO THAT THE RANGE IS 0-255

    /**
     * Adjusts the red value of this pixel.
     *
     * @param red the value that the red should be set to.
     */
    public void setRed(int red) {
      this.red = clamp(red);
    }

    /**
     * Adjusts the green value of this pixel.
     *
     * @param green the value that the green should be set to.
     */
    public void setGreen(int green) {
      this.green = clamp(green);
    }

    /**
     * Adjusts the blue value of this pixel.
     *
     * @param blue the value that the blue should be set to.
     */
    public void setBlue(int blue) {
      this.blue = clamp(blue);
    }

    /**
     * Determines if this is equal to the provided object.
     *
     * @param o the object to be compared to.
     * @return true if this is equal to the object provided, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
      if (o == this) {
        return true;
      }
      if (!(o instanceof Pixel)) {
        return false;
      }
      Pixel other = (Pixel) o;
      return this.red == other.red
              && this.green == other.green
              && this.blue == other.blue;
    }

    /**
     * Computes the hash code for this Pixel object.
     *
     * @return the hashcode using the fields of this Pixel.
     */
    @Override
    public int hashCode() {
      return Objects.hash(this.red, this.green, this.blue);
    }

    // ### REFACTORED ###
    // private method that checks if a value is less than 0 or greater than 255 and applies
    // a clamp appropriately
    private int clamp(int value) {
      if (value > 255) {
        value = 255;
      }
      if (value < 0) {
        value = 0;
      }
      return value;
    }

  }

  /**
   * A simple Posn. Represents a position with an x and y value on a 2d plane. The coordinates of
   * this posn are in (y,x) rather than (x,y) to represents rows and columns.
   */
  public static class Posn {
    private final int y;
    private final int x;

    /**
     * Constructs a Posn. Is reordered from (x,y) to (y,x) to think of it as in terms of rows and
     * columns.
     *
     * @param y the y value of this posn (the row).
     * @param x the x value of this posn (the column).
     */
    public Posn(int y, int x) {
      this.y = y;
      this.x = x;
    }

    /**
     * Provides the y value of this posn.
     *
     * @return the y value.
     */
    public int getY() {
      return this.y;
    }

    /**
     * Provides the x value of this posn.
     *
     * @return the x value.
     */
    public int getX() {
      return this.x;
    }
  }
}
