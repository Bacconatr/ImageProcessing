package imageprocessing.controller;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.IntStream;

import imageprocessing.model.FlipType;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ImageProcessingModelImpl;
import imageprocessing.view.IJFrameView;

/**
 * A Controller that allows for a scripting mode and a GUI mode.
 */
public class GuiScriptController implements Features {
  private final ImageProcessingModel model;
  private IJFrameView view;
  private final List<String> imageHistory;
  private final ImageControllerAdvancedImpl delegate;

  /**
   * Constructs a GuiScriptController.
   *
   * @param model the model representing the loaded image states
   */
  public GuiScriptController(ImageProcessingModel model) {
    this.model = model;
    imageHistory = new ArrayList<>();
    delegate = new ImageControllerAdvancedImpl(model);
  }

  /**
   * Sets the GUI view and provides it with features that can be called on when a listener
   * receives input.
   *
   * @param v the object that will be rendering the GUI
   */
  public void setView(IJFrameView v) {
    view = v;
    // gives the view this controller (providing access to the callback methods)
    view.addFeatures(this);
  }

  /**
   *
   */
  @Override
  public void imageBrightness() {
    if (imageHistory.size() < 1) {
      view.showErrorMessage("There must be an image loaded before performing commands.");
      return;
    }
    try {
      int brightnessAmount = view.userBrightnessInput();
      StringBuilder latest = findLatestVersion();
      String newName = latest + "-brightness";
      model.adjustLight(brightnessAmount, imageHistory.get(imageHistory.size() - 1), newName);
      imageHistory.add(newName);
      updateImage();
    } catch (IllegalStateException e) {
      // Do nothing. Allow the user to simply resume the program.
    }
  }

  /**
   * @param representation
   */
  @Override
  public void colorChanging(BiFunction<ImageProcessingModelImpl.Posn,
          ImageProcessingModelImpl.Pixel[][],
          ImageProcessingModelImpl.Pixel> representation) {
    if (imageHistory.size() < 1) {
      view.showErrorMessage("There must be an image loaded before performing commands.");
      return;
    }
    StringBuilder latest = findLatestVersion();
    String newName = latest + "-change";
    model.createRepresentation(latest.toString(), newName, representation);
    imageHistory.add(newName);
    updateImage();
  }

  // THE REASON THAT WE DO NOT SIMPLY OVERWRITE ONE IMAGE IN THE MODEL AND ADD NEW IMAGES WITH NEW
  // NAMES IS SO WE CAN HAVE AN IMAGE HISTORY IN CASE WE NEED IT IN THE FUTURE

  // REMEMBER TO THROW AN ERROR IF A BUTTON IS PRESSED WITHOUT AN IMAGE

  // BE ABLE TO NOT THROW AN ERROR AND JUST GO BACK TO THE PROGRAM IF NO LOAD OR SAVE IS SELECTED

  // ADD ERROR MESSAGE FOR IF USER INPUTS A STRING INSTEAD OF AN INT

  /**
   * @param flipType
   */
  @Override
  public void flipImage(FlipType flipType) {
    if (imageHistory.size() < 1) {
      view.showErrorMessage("There must be an image loaded before performing commands.");
      return;
    }
    // the most updated version so far
    StringBuilder latestVersion = findLatestVersion();
    String newImageName = latestVersion + "-" + flipType.toString();
    int flipSize = flipType.toString().length() + 1;
    int latestSize = latestVersion.length();

    // This entire if case is to check that there is a previous version we can revert to rather
    // than needing to perform another flip

    // ensures that the size of the latest version is greater than "-horizontal" or "-vertical"
    if (latestSize > flipSize && imageHistory.size() > 1) {
      // the second most updated version.
      String secondLatest = imageHistory.get(imageHistory.size() - 2);

      // a temp StringBuilder that is the same as the latestVersion, but will be substringed so
      // that it doesn't have the horizontal tag (if there is one, if there is not whatever other
      // strings that are there will be removed instead)
      StringBuilder temp = new StringBuilder(latestVersion);

      // the string that should be "-horizontal" or "vertical" if the tag is there
      String tag = temp.delete(latestSize - flipSize, latestSize).toString();
      if (temp.toString().equals(secondLatest) && tag.equals(flipType.toString())) {
        model.addImage(model.imageState(secondLatest), newImageName);
        imageHistory.add(newImageName);
        updateImage();
        return;
      }
    }

    model.flip(flipType, latestVersion.toString(), newImageName);
    imageHistory.add(newImageName);
    updateImage();
  }

  /**
   *
   */
  @Override
  public void componentOptions() {
    if (imageHistory.size() < 1) {
      view.showErrorMessage("There must be an image loaded before performing commands.");
      return;
    }
    try {
      colorChanging(view.showComponentOptions());
    } catch (IllegalStateException e) {
      // do nothing, let the user return to the program.
    }
  }

  /**
   *
   */
  @Override
  public void loadImageToDisplay() {
    String filePath = view.userLoadPath();
    if (filePath != null) {
      try {
        delegate.load(filePath, filePath);
      } catch (IllegalArgumentException e) {
        view.showErrorMessage("Invalid load path.");
        return;
      }
      imageHistory.add(filePath);
      updateImage();
    }
  }

  /**
   *
   */
  @Override
  public void saveCurrentImage() {
    if (imageHistory.size() < 1) {
      view.showErrorMessage("There must be an image loaded before performing commands.");
      return;
    }
    String savePath = view.userSavePath();
    if (savePath != null) {
      try {
        delegate.save(savePath, imageHistory.get(imageHistory.size() - 1));
      } catch (IllegalArgumentException e) {
        view.showErrorMessage("Saving Error: Invalid File Type");
      }
    }
  }

  @Override
  public int[][] colorHistogram() {

    int[][] rgbFrequencies = new int[3][256];

    // sets all the values to 0 in this array
    // (code from intellij suggestion)
    int[] redRepresentation = IntStream.range(0, 256).map(i -> 0).toArray();
    int[] greenRepresentation = IntStream.range(0, 256).map(i -> 0).toArray();
    int[] blueRepresentation = IntStream.range(0, 256).map(i -> 0).toArray();
    ImageProcessingModelImpl.Pixel[][] currentState =
            model.imageState(findLatestVersion().toString());
    for (ImageProcessingModelImpl.Pixel[] pixels : currentState) {
      for (int j = 0; j < currentState[0].length; j++) {
        ImageProcessingModelImpl.Pixel currentPixel = pixels[j];
        redRepresentation[currentPixel.getRed()] = redRepresentation[currentPixel.getRed()] + 1;
        greenRepresentation[currentPixel.getGreen()] =
                greenRepresentation[currentPixel.getGreen()] + 1;
        blueRepresentation[currentPixel.getBlue()] = blueRepresentation[currentPixel.getBlue()] + 1;
      }
    }

    rgbFrequencies[0] = redRepresentation;
    rgbFrequencies[1] = greenRepresentation;
    rgbFrequencies[2] = blueRepresentation;

    return rgbFrequencies;
  }


  /**
   *
   */
  @Override
  public void exitProgram() {
    System.exit(0);
  }

  //
  private void updateImage() {
    ImageProcessingModelImpl.Pixel[][] latestImageState =
            model.imageState(findLatestVersion().toString());
    BufferedImage currentImage = delegate.stateToImage(latestImageState);
    view.updateImage(currentImage, colorHistogram());
  }

  //
  private StringBuilder findLatestVersion() {
    int size = imageHistory.size();
    String s = imageHistory.get(size - 1);
    return new StringBuilder(s);
  }


}
