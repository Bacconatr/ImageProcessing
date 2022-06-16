package imageprocessing.model;

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
          int r = image.getRGB(j, i) >> 16 & 0xff;
          int g = image.getRGB(j, i) >> 8 & 0xff;
          int b = image.getRGB(j, i) & 0xff;
          listOfPixels[i][j] = new Pixel(r, g, b);
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
      Pixel[][] fileToSave = mapOfImages.getOrDefault(imageName, null);
      if (fileToSave == null) {
        throw new IllegalArgumentException("The image you are trying to save does not exist.");
      }
      File file = new File(filePath);

      String fileType = filePath.split("\\.")[1];

      BufferedImage image = new BufferedImage(fileToSave.length, fileToSave[0].length, 1);
      for (Pixel[] pixels : fileToSave) {
        for (int j = 0; j < fileToSave[0].length; j++) {

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
