import org.junit.Before;
import org.junit.Test;

import imageprocessing.controller.ImageControllerImpl;
import imageprocessing.model.componentbifunctions.BlueBiFunction;
import imageprocessing.model.componentbifunctions.FilterBiFunction;
import imageprocessing.model.componentbifunctions.GreenBiFunction;
import imageprocessing.model.componentbifunctions.IntensityBiFunction;
import imageprocessing.model.componentbifunctions.LumaBiFunction;
import imageprocessing.model.componentbifunctions.RedBiFunction;
import imageprocessing.model.componentbifunctions.ValueBiFunction;
import imageprocessing.model.FlipType;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ImageProcessingModelImpl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for ImageProcessingImpl.
 */
public class ImageProcessingImplTest {
  ImageProcessingModel model;
  ImageProcessingModelImpl.Pixel[][] checkImage;

  @Before
  public void init() {
    model = new ImageProcessingModelImpl();
    model.readPPM("res/2by2.ppm", "2x2");

    // assigning each pixel to a specific value
    // this image is the same as 2by2.ppm

    checkImage = new ImageProcessingModelImpl.Pixel[2][2];
    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(255, 0, 0);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(0, 255, 0);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(0, 0, 255);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(250, 100, 100);

  }

  @Test
  public void testConstructor() {
    ImageProcessingModel noImages = new ImageProcessingModelImpl();
    // verifying that the field initializes with no images
    assertEquals(0, noImages.numImages());
  }

  @Test
  public void testImageState() {
    // tests that calling the imageState on a non-existing image in the model throws an error
    try {
      model.imageState("2x2-red-greyscale");
      fail("Did not input a non-existing image name");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "The specified image does not exist.");
    }
    // checks that the imageState returned is correct
    assertArrayEquals(checkImage, model.imageState("2x2"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testRedInvalidInput() {
    model.createRepresentation("does not exist", "will not load", new RedBiFunction());
  }

  @Test
  public void testCreateRepresentationRed() {
    model.createRepresentation("2x2", "2x2-red-greyscale", new RedBiFunction());
    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(255, 255, 255);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(0, 0, 0);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(0, 0, 0);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(250, 250, 250);
    assertArrayEquals(checkImage, model.imageState("2x2-red-greyscale"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testBlueInvalidInput() {
    model.createRepresentation("does not exist", "will not load", new BlueBiFunction());
  }

  @Test
  public void testCreateRepresentationBlue() {
    model.createRepresentation("2x2", "2x2-blue-greyscale", new BlueBiFunction());
    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(0, 0, 0);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(0, 0, 0);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(255, 255, 255);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    assertArrayEquals(checkImage, model.imageState("2x2-blue-greyscale"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testGreenInvalidInput() {
    model.createRepresentation("does not exist", "will not load", new GreenBiFunction());
  }

  @Test
  public void testCreateRepresentationGreen() {
    model.createRepresentation("2x2", "2x2-green-greyscale", new GreenBiFunction());
    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(0, 0, 0);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(255, 255, 255);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(0, 0, 0);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    assertArrayEquals(checkImage, model.imageState("2x2-green-greyscale"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testValueInvalidInput() {
    model.createRepresentation("does not exist", "will not load", new ValueBiFunction());
  }

  @Test
  public void testCreateRepresentationValue() {
    model.createRepresentation("2x2", "2x2-value-greyscale", new ValueBiFunction());
    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(255, 255, 255);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(255, 255, 255);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(255, 255, 255);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(250, 250, 250);
    assertArrayEquals(checkImage, model.imageState("2x2-value-greyscale"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testLumaInvalidInput() {
    model.createRepresentation("does not exist", "will not load", new LumaBiFunction());
  }

  @Test
  public void testCreateRepresentationLuma() {
    model.createRepresentation("2x2", "2x2-luma-greyscale", new LumaBiFunction());
    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(54, 54, 54);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(182, 182, 182);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(18, 18, 18);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(131, 131, 131);
    assertArrayEquals(checkImage, model.imageState("2x2-luma-greyscale"));
  }


  @Test(expected = IllegalArgumentException.class)
  public void testIntensityInvalidInput() {
    model.createRepresentation("does not exist", "will not load", new IntensityBiFunction());
  }

  @Test
  public void testCreateRepresentationIntensity() {
    model.createRepresentation("2x2", "2x2-intensity-greyscale", new IntensityBiFunction());
    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(85, 85, 85);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(85, 85, 85);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(85, 85, 85);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(150, 150, 150);
    assertArrayEquals(checkImage, model.imageState("2x2-intensity-greyscale"));
  }

  @Test
  public void testFlip() {
    model = new ImageProcessingModelImpl();
    model.readPPM("res\\2by2.ppm", "2x2");
    model.flip(FlipType.Vertical, "2x2", "2x2-verticalFlip");


    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(255, 0, 0);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(0, 255, 0);
    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(0, 0, 255);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(250, 100, 100);


    assertArrayEquals(checkImage, model.imageState("2x2-verticalFlip"));

    model = new ImageProcessingModelImpl();
    model.readPPM("res\\2by2.ppm", "2x2");
    model.flip(FlipType.Horizontal, "2x2", "2x2-verticalFlip");


    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(255, 0, 0);
    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(0, 255, 0);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(0, 0, 255);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(250, 100, 100);

    assertArrayEquals(checkImage, model.imageState("2x2-verticalFlip"));

  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalFileNameFlip() {
    model = new ImageProcessingModelImpl();
    model.readPPM("res\\2by2.ppm", "2x2");
    model.flip(FlipType.Horizontal, "helloWorld", "2x2-verticalFlip");

  }

  @Test
  public void testAdjustLight() {
    // brighten an image
    model = new ImageProcessingModelImpl();
    model.readPPM("res\\2by2.ppm", "2x2");
    model.adjustLight(10, "2x2", "2x2-brighten");

    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(255, 10, 10);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(10, 255, 10);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(10, 10, 255);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(255, 110, 110);

    assertArrayEquals(checkImage, model.imageState("2x2-brighten"));

    // darken an image

    model = new ImageProcessingModelImpl();
    model.readPPM("res\\2by2.ppm", "2x2");
    model.adjustLight(-10, "2x2", "2x2-darken");

    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(245, 0, 0);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(0, 245, 0);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(0, 0, 245);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(240, 90, 90);

    assertArrayEquals(checkImage, model.imageState("2x2-darken"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalNameAdjustLight() {
    model = new ImageProcessingModelImpl();
    model.readPPM("res\\2by2.ppm", "2x2");
    model.adjustLight(-10, "iloveood", "2x2-brighten");
  }

  @Test
  public void testReadPPMInvalidPath() {
    try {
      model.readPPM("res/invalidDirectory/2by2.ppm", "should not exist");
      fail("the file path did exist");
    } catch (IllegalArgumentException e) {
      // the file path did not exist and an error was thrown appropriately
    }
    // an additional check to ensure that an invalid ppm file was not loaded as an image
    try {
      model.imageState("should not exist");
      fail("the image was loaded when it shouldn't have");
    } catch (IllegalArgumentException e) {
      // the image was not loaded
    }
  }

  @Test
  public void testReadPPM() {
    // this shows that 9pixel does not already exist in the model
    try {
      model.imageState("9pixel");
      fail("Did not input a non-existing image name");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "The specified image does not exist.");
    }

    model.readPPM("res/9pixel.ppm", "9pixel");
    ImageProcessingModelImpl.Pixel[][] expected = new ImageProcessingModelImpl.Pixel[3][3];
    expected[0][0] = new ImageProcessingModelImpl.Pixel(255, 0, 0);
    expected[0][1] = new ImageProcessingModelImpl.Pixel(0, 255, 0);
    expected[0][2] = new ImageProcessingModelImpl.Pixel(0, 0, 255);
    expected[1][0] = new ImageProcessingModelImpl.Pixel(0, 0, 0);
    expected[1][1] = new ImageProcessingModelImpl.Pixel(255, 255, 255);
    expected[1][2] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    expected[2][0] = new ImageProcessingModelImpl.Pixel(250, 100, 100);
    expected[2][1] = new ImageProcessingModelImpl.Pixel(230, 20, 70);
    expected[2][2] = new ImageProcessingModelImpl.Pixel(94, 232, 255);
    assertArrayEquals(expected, model.imageState("9pixel"));
  }

  @Test
  public void testSavePPM() {
    // checks that if the file path doesn't exist an error is thrown
    try {
      model.savePPM("res/res/fail.ppm", "2x2");
      fail("the file path actually existed");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "Error, the file path provided does not exist.");
    }

    // checks that if the image name does not exist in the model an error is thrown
    try {
      model.savePPM("res/save.ppm", "non-existent image");
      fail("the image actually existed");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "The image you are trying to save does not exist.");
    }

    // could add a command about what operation was performed on the file.

    // saves to the saveTester.ppm file with a currently existing image within the model
    // (this will overwrite any previous file that was created when running this test)
    model.savePPM("res/saveTester.ppm", "2x2");
    // load the newly saved file into the model and check that it is the same value as the 2x2 image
    model.readPPM("res/saveTester.ppm", "fileSaveSuccessful");
    assertArrayEquals(checkImage, model.imageState("fileSaveSuccessful"));

    // ***** Note that checkImage is the same as 2x2. It is the expected and is defined in the init

  }

  @Test
  public void testHorizontalAndVertical() {

    model = new ImageProcessingModelImpl();
    model.readPPM("res\\2by2.ppm", "2x2");
    model.flip(FlipType.Vertical, "2x2", "2x2-verticalFlip");
    model.flip(FlipType.Horizontal, "2x2-verticalFlip",
            "2x2-verticalHorizontalFlip");

    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(255, 0, 0);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(0, 255, 0);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(0, 0, 255);
    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(250, 100, 100);

    assertArrayEquals(checkImage, model.imageState("2x2-verticalHorizontalFlip"));
  }

  @Test
  public void testComponentFlipAndBrighten() {
    model = new ImageProcessingModelImpl();
    model.readPPM("res\\2by2.ppm", "2x2");
    model.adjustLight(20, "2x2", "2x2-brighten");
    model.flip(FlipType.Horizontal, "2x2-brighten",
            "2x2-brightenHorizontalFlip");

    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(255, 20, 20);
    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(20, 255, 20);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(20, 20, 255);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(255, 120, 120);

    assertArrayEquals(checkImage, model.imageState("2x2-brightenHorizontalFlip"));

  }

  @Test
  public void testPerformProcessAndSaveThenLoad() {

    model = new ImageProcessingModelImpl();
    model.readPPM("res\\2by2.ppm", "2x2");
    model.adjustLight(10, "2x2", "2x2-brighten");
    model.savePPM("res\\2x2brighten.ppm", "2x2-brighten");

    model.readPPM("res\\2x2brighten.ppm", "2x2-brighten.ppm");

    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(255, 10, 10);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(10, 255, 10);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(10, 10, 255);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(255, 110, 110);

    assertArrayEquals(checkImage, model.imageState("2x2-brighten.ppm"));
  }

  @Test
  public void testBlur() {
    model = new ImageProcessingModelImpl();
    model.readPPM("res/9pixel.ppm", "three!");
    assertEquals(255, model.imageState("three!")[1][1].getRed());
    model.createRepresentation("three!", "three!",
            new FilterBiFunction(ImageControllerImpl.BLUR_KERNEL));
    // testing the red channel with the blur
    assertEquals(79, model.imageState("three!")[0][0].getRed());
    assertEquals(142, model.imageState("three!")[1][1].getRed());

    ImageProcessingModelImpl.Pixel[][] blurred = new ImageProcessingModelImpl.Pixel[3][3];
    blurred[0][0] = new ImageProcessingModelImpl.Pixel(79, 47, 15);
    blurred[0][1] = new ImageProcessingModelImpl.Pixel(70, 101, 70);
    blurred[0][2] = new ImageProcessingModelImpl.Pixel(28, 60, 92);
    blurred[1][0] = new ImageProcessingModelImpl.Pixel(109, 61, 48);
    blurred[1][1] = new ImageProcessingModelImpl.Pixel(142, 131, 123);
    blurred[1][2] = new ImageProcessingModelImpl.Pixel(83, 103, 125);
    blurred[2][0] = new ImageProcessingModelImpl.Pixel(107, 43, 49);
    blurred[2][1] = new ImageProcessingModelImpl.Pixel(138, 84, 100);
    blurred[2][2] = new ImageProcessingModelImpl.Pixel(80, 88, 100);

    assertArrayEquals(model.imageState("three!"), blurred);
  }

  @Test
  public void testSharpen() {
    model = new ImageProcessingModelImpl();
    model.readPPM("res/5x5.ppm", "5x5");
    assertEquals(80, model.imageState("5x5")[2][2].getRed());
    model.createRepresentation("5x5", "5x5",
            new FilterBiFunction(ImageControllerImpl.SHARPEN_KERNEL));
    // these tests are for when 80, 90, 100 is at the first pixel
    //    assertEquals(92, model.imageState("5x5")[0][0].getRed());
    //    assertEquals(102, model.imageState("5x5")[0][0].getGreen());

    // these tests are for when 80, 90, 100 is at the center pixel
    assertEquals(80, model.imageState("5x5")[2][2].getRed());
  }


}