package imageprocessing.controller;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.StringReader;

import javax.imageio.ImageIO;

import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ImageProcessingModelImpl;
import imageprocessing.view.BasicImageProcessingView;
import imageprocessing.view.IProcessingImageView;

/**
 * Represents an advanced controller that allows a user to load in an image and type in commands to
 * perform processes on that image. If a user has an incorrect command then the user is prompted to
 * input the correct command. Once a correct command is registered, if the inputs are non-valid then
 * an IllegalArgumentException is thrown and the program exits. This controller is advanced since it
 * allows for the loading and saving of image files (bmp, png, jpg, etc.) in addition to ppm files.
 */
public class ImageControllerAdvancedImpl extends ImageControllerImpl
        implements IProcessingImageController {
  /**
   * Constructs an ImageControllerImpl.
   *
   * @param model the model of the image processor (which has a Map of the images that the user is
   *              performing processes on).
   * @param view  the view used in this program.
   * @param ap    what the view will append to when called on.
   */
  public ImageControllerAdvancedImpl(ImageProcessingModel model,
                                     IProcessingImageView view, Readable ap) {
    super(model, view, ap);
  }
  // ### Refactored ###
  // added another convenience constructor
  /**
   * Constructs an ImageControllerImpl.
   *
   * @param model the model of the image processor (which has a Map of the images that the user is
   *              performing processes on).
   */
  public ImageControllerAdvancedImpl(ImageProcessingModel model) {
    super(model, new BasicImageProcessingView(), new StringReader("q"));
  }

  // loads the image at the given file path and passes that image to the model with the given
  // file name. If the file path does not exist an error is thrown. Allows for the loading of
  // image extension in addition to ppm files.
  @Override
  protected void load(String filePath, String imageName) throws IllegalArgumentException {
    File file = new File(filePath);
    BufferedImage image;

    if (filePath.contains(".ppm")) {
      super.load(filePath, imageName);
    } else {
      try {
        image = ImageIO.read(file);
      } catch (IOException e) {
        throw new IllegalArgumentException("Sorry, the file at the given path was not found.");
      }

      int height = image.getHeight();
      int width = image.getWidth();
      ImageProcessingModelImpl.Pixel[][] listOfPixels =
              new ImageProcessingModelImpl.Pixel[height][width];

      for (int i = 0; i < height; i++) {
        for (int j = 0; j < width; j++) {
          Color pixelColor = new Color(image.getRGB(j, i));
          listOfPixels[i][j] =
                  new ImageProcessingModelImpl.Pixel(pixelColor.getRed(), pixelColor.getGreen(),
                          pixelColor.getBlue());
        }
      }

      model.addImage(listOfPixels, imageName);
    }
  }


  // Saves the given image name at the provided the file path. If the provided image or the
  // filepath doesn't exist then an IllegalArgumentException is thrown. If the file path is a ppm
  // file saves as a ppm. If the file path is a supported image extension then save as that
  // extension. Otherwise, an error is thrown.
  @Override
  protected void save(String filePath, String imageName) throws IllegalArgumentException {
    if (filePath.contains(".ppm")) {
      super.save(filePath, imageName);
    } else {

      // Checks if the filepath provided is an image. If it is then proceed. Otherwise, throw an
      // exception.
      boolean isImage = false;
      for (String s : ImageIO.getWriterFormatNames()) {
        StringBuilder fileType = new StringBuilder(".").append(s);
        if (filePath.contains(fileType.toString())) {
          isImage = true;
          break;
        }
      }
      if (!isImage) {
        throw new IllegalArgumentException("The filepath provided was not a valid image.");
      }

      // The filepath is an image. Check if the image being saved exists in the model.
      ImageProcessingModelImpl.Pixel[][] imageToSave = model.imageState(imageName);
      if (imageToSave == null) {
        throw new IllegalArgumentException("The image you are trying to save does not exist.");
      }

      // both the arguments are valid, save the image to the given filepath
      String fileType = filePath.split("\\.")[1];

      BufferedImage image = stateToImage(imageToSave);

      try {
        ImageIO.write(image, fileType, new FileOutputStream(new File(filePath)));
      } catch (IOException e) {
        throw new IllegalArgumentException(e.getMessage());
      }

    }
  }

  // ### REFACTORED ###
  // abstracted a method out of save. Functionality remains the same
  // turns a file in a HashMap into a BufferedImage
  protected BufferedImage stateToImage(ImageProcessingModelImpl.Pixel[][] imageToSave) {
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
    return image;
  }


}
