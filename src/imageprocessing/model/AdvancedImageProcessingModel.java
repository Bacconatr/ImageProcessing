package imageprocessing.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

/**
 *
 */
public class AdvancedImageProcessingModel extends ImageProcessingModelImpl
        implements ImageProcessingModel {
  /**
   *
   */
  public AdvancedImageProcessingModel() {
    super();
  }

  /**
   * @param filePath  the path of the PPM file that will be read.
   * @param imageName the name of the image that will be loaded and represented as data.
   * @throws IllegalArgumentException
   */
  @Override
  public void readPPM(String filePath, String imageName) throws IllegalArgumentException {
    File file = new File(filePath);
    BufferedImage image;

    if (filePath.contains(".ppm")) {
      super.readPPM(filePath, imageName);
    } else {
      try {
        image = ImageIO.read(file);
      } catch (IOException e) {
        throw new IllegalArgumentException("sorry file not found");
      }

      int height = image.getHeight();
      int width = image.getWidth();
      Pixel[][] listOfPixels = new Pixel[height][width];

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          Color pixelColor = new Color(image.getRGB(j, i));
          listOfPixels[i][j] = new Pixel(pixelColor.getRed(), pixelColor.getGreen(),
                  pixelColor.getBlue());
        }
      }

      mapOfImages.put(imageName, listOfPixels);
    }
  }

  /**
   * @param filePath  the path that the image will be saved to. This path should include the name of
   *                  the new file that will be created when it is stored locally. (e.g. C:/file.ppm
   *                  is the required path to save the file to the C drive).
   * @param imageName the name of the image that should be saved.
   * @throws IllegalArgumentException
   */
  @Override
  public void savePPM(String filePath, String imageName) throws IllegalArgumentException {
    if (filePath.contains(".ppm")) {
      super.savePPM(filePath, imageName);
    } else {
      Pixel[][] imageToSave = mapOfImages.getOrDefault(imageName, null);
      if (imageToSave == null) {
        throw new IllegalArgumentException("The image you are trying to save does not exist.");
      }
      File file = new File(filePath);
      // ImageIO.

      String fileType = filePath.split("\\.")[1];
      int height = imageToSave.length;
      int width = imageToSave[0].length;

      BufferedImage image = new BufferedImage(width, height, 1);
      for (int i = 0; i < imageToSave.length; i++) {
        for (int j = 0; j < imageToSave[0].length; j++) {
          Color pixelColor = new Color(imageToSave[i][j].getRed(), imageToSave[i][j].getGreen(),
                  imageToSave[i][j].getBlue());
          image.setRGB(j, i, pixelColor.getRGB());
        }
      }

      try {
        ImageIO.write(image, fileType, file);
      } catch (IOException e) {
        throw new IllegalArgumentException("Error, the file path provided does not exist.");
      }

    }
  }
}
