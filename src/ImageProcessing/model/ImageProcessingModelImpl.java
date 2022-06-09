package ImageProcessing.model;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 */
public class ImageProcessingModelImpl implements ImageProcessingModel {
  private HashMap<String, Pixel[][]> mapOfImages;

  /**
   * Constructs an ImageProcessingModelImpl.
   */
  public ImageProcessingModelImpl() {
    mapOfImages = new HashMap<String, Pixel[][]>();
  }

  @Override
  public void createRepresentation(VisualizationType type, String filename) {


  }

  @Override
  public void flip(FlipType flip, String filename) {

  }

  @Override
  public void adjustLight(int value, String filename) throws IllegalArgumentException {

    Pixel[][] image = mapOfImages.getOrDefault(filename, null).clone();


    if (image == null) {
      throw new IllegalArgumentException("Didn't provide the name of an existing images.");
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


  }

  @Override
  public void readPPM(String filePath, String fileName) {

    // Code originally provided in ImageUtil, but we have changed to fit our program.

    Scanner sc;

    try {
      sc = new Scanner(new FileInputStream(filePath));
    } catch (FileNotFoundException e) {
      //  System.out.println("File " + filePath + " not found!");
      return;
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


        //  System.out.println("Color of pixel (" + j + "," + i + "): " + r + "," + g + "," + b);

        mapOfImages.put(fileName, listOfPixels);
      }
    }
  }

  public void savePPM(String filePath, String fileName) throws IllegalArgumentException {

    Pixel[][] fileToSave = mapOfImages.getOrDefault(fileName, null);
    if (fileToSave == null) {
      throw new IllegalArgumentException("sorry their is no file with given file name");
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

        throw new IllegalArgumentException("sorry there was an error with the saving the file");
      }

    }


  }

}
