import org.junit.Before;
import org.junit.Test;

import imageprocessing.model.ComponentBiFunctions.BlueBiFunction;
import imageprocessing.model.ComponentBiFunctions.GreenBiFunction;
import imageprocessing.model.ComponentBiFunctions.IntensityBiFunction;
import imageprocessing.model.ComponentBiFunctions.LumaBiFunction;
import imageprocessing.model.ComponentBiFunctions.RedBiFunction;
import imageprocessing.model.ComponentBiFunctions.ValueBiFunction;
import imageprocessing.model.FlipType;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ImageProcessingModelImpl;
import imageprocessing.model.Pixel;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Tests for ImageProcessingImpl.
 */
public class ImageProcessingImplTest {
  ImageProcessingModel model;
  Pixel[][] checkImage;

  @Before
  public void init() {
    model = new ImageProcessingModelImpl();
    model.readPPM("res/2by2.ppm", "2x2");

    // assigning each pixel to a specific value
    // this image is the same as 2by2.ppm

    checkImage = new Pixel[2][2];
    checkImage[0][0] = new Pixel(255, 0, 0);
    checkImage[0][1] = new Pixel(0, 255, 0);
    checkImage[1][0] = new Pixel(0, 0, 255);
    checkImage[1][1] = new Pixel(250, 100, 100);
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

  @Test (expected = IllegalArgumentException.class)
  public void testRedInvalidInput() {
    model.createRepresentation("does not exist", "will not load", new RedBiFunction());
  }

  @Test
  public void testCreateRepresentationRed() {
    model.createRepresentation("2x2", "2x2-red-greyscale", new RedBiFunction());
    checkImage[0][0] = new Pixel(255, 255, 255);
    checkImage[0][1] = new Pixel(0, 0, 0);
    checkImage[1][0] = new Pixel(0, 0, 0);
    checkImage[1][1] = new Pixel(250, 250, 250);
    assertArrayEquals(model.imageState("2x2-red-greyscale"), checkImage);
  }

  @Test (expected = IllegalArgumentException.class)
  public void testBlueInvalidInput() {
    model.createRepresentation("does not exist", "will not load", new BlueBiFunction());
  }

  @Test
  public void testCreateRepresentationBlue() {
    model.createRepresentation("2x2", "2x2-blue-greyscale", new BlueBiFunction());
    checkImage[0][0] = new Pixel(0, 0, 0);
    checkImage[0][1] = new Pixel(0, 0, 0);
    checkImage[1][0] = new Pixel(255, 255, 255);
    checkImage[1][1] = new Pixel(100, 100, 100);
    assertArrayEquals(model.imageState("2x2-blue-greyscale"), checkImage);
  }


  @Test (expected = IllegalArgumentException.class)
  public void testGreenInvalidInput() {
    model.createRepresentation("does not exist", "will not load", new GreenBiFunction());
  }

  @Test
  public void testCreateRepresentationGreen() {
    model.createRepresentation("2x2", "2x2-green-greyscale", new GreenBiFunction());
    checkImage[0][0] = new Pixel(0, 0, 0);
    checkImage[0][1] = new Pixel(255, 255, 255);
    checkImage[1][0] = new Pixel(0, 0, 0);
    checkImage[1][1] = new Pixel(100, 100, 100);
    assertArrayEquals(model.imageState("2x2-green-greyscale"), checkImage);
  }


  @Test (expected = IllegalArgumentException.class)
  public void testValueInvalidInput() {
    model.createRepresentation("does not exist", "will not load", new ValueBiFunction());
  }

  @Test
  public void testCreateRepresentationValue() {
    model.createRepresentation("2x2", "2x2-value-greyscale", new ValueBiFunction());
    checkImage[0][0] = new Pixel(255, 255, 255);
    checkImage[0][1] = new Pixel(255, 255, 255);
    checkImage[1][0] = new Pixel(255, 255, 255);
    checkImage[1][1] = new Pixel(250, 250, 250);
    assertArrayEquals(model.imageState("2x2-value-greyscale"), checkImage);
  }


  @Test (expected = IllegalArgumentException.class)
  public void testLumaInvalidInput() {
    model.createRepresentation("does not exist", "will not load", new LumaBiFunction());
  }

  @Test
  public void testCreateRepresentationLuma() {
    model.createRepresentation("2x2", "2x2-luma-greyscale", new LumaBiFunction());
    checkImage[0][0] = new Pixel(54, 54, 54);
    checkImage[0][1] = new Pixel(182, 182, 182);
    checkImage[1][0] = new Pixel(18, 18, 18);
    checkImage[1][1] = new Pixel(131, 131, 131);
    assertArrayEquals(model.imageState("2x2-luma-greyscale"), checkImage);
  }


  @Test (expected = IllegalArgumentException.class)
  public void testIntensityInvalidInput() {
    model.createRepresentation("does not exist", "will not load", new IntensityBiFunction());
  }

  @Test
  public void testCreateRepresentationIntensity() {
    model.createRepresentation("2x2", "2x2-intensity-greyscale", new IntensityBiFunction());
    checkImage[0][0] = new Pixel(85, 85, 85);
    checkImage[0][1] = new Pixel(85, 85, 85);
    checkImage[1][0] = new Pixel(85, 85, 85);
    checkImage[1][1] = new Pixel(150, 150, 150);
    assertArrayEquals(model.imageState("2x2-intensity-greyscale"), checkImage);
  }

  @Test
  public void testFlip() {
    model = new ImageProcessingModelImpl();
    model.readPPM("res\\2by2.ppm", "2x2");
    model.flip(FlipType.Vertical, "2x2", "2x2-verticalFlip");


    checkImage[1][0] = new Pixel(255, 0, 0);
    checkImage[1][1] = new Pixel(0, 255, 0);
    checkImage[0][0] = new Pixel(0, 0, 255);
    checkImage[0][1] = new Pixel(250, 100, 100);


    assertArrayEquals(model.imageState("2x2-verticalFlip"), checkImage);

    model = new ImageProcessingModelImpl();
    model.readPPM("res\\2by2.ppm", "2x2");
    model.flip(FlipType.Horizontal, "2x2", "2x2-verticalFlip");


    checkImage[0][1] = new Pixel(255, 0, 0);
    checkImage[0][0] = new Pixel(0, 255, 0);
    checkImage[1][1] = new Pixel(0, 0, 255);
    checkImage[1][0] = new Pixel(250, 100, 100);

    assertArrayEquals(model.imageState("2x2-verticalFlip"), checkImage);

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

    checkImage[0][0] = new Pixel(255, 10, 10);
    checkImage[0][1] = new Pixel(10, 255, 10);
    checkImage[1][0] = new Pixel(10, 10, 255);
    checkImage[1][1] = new Pixel(255, 110, 110);

    assertArrayEquals(model.imageState("2x2-brighten"), checkImage);

    // darken an image

    model = new ImageProcessingModelImpl();
    model.readPPM("res\\2by2.ppm", "2x2");
    model.adjustLight(-10, "2x2", "2x2-darken");

    checkImage[0][0] = new Pixel(245, 0, 0);
    checkImage[0][1] = new Pixel(0, 245, 0);
    checkImage[1][0] = new Pixel(0, 0, 245);
    checkImage[1][1] = new Pixel(240, 90, 90);

    assertArrayEquals(model.imageState("2x2-darken"), checkImage);
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
    Pixel[][] expected = new Pixel[3][3];
    expected[0][0] = new Pixel(255, 0, 0);
    expected[0][1] = new Pixel(0, 255, 0);
    expected[0][2] = new Pixel(0, 0, 255);
    expected[1][0] = new Pixel(0, 0, 0);
    expected[1][1] = new Pixel(255, 255, 255);
    expected[1][2] = new Pixel(100, 100, 100);
    expected[2][0] = new Pixel(250, 100, 100);
    expected[2][1] = new Pixel(230, 20, 70);
    expected[2][2] = new Pixel(94, 232, 255);
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

    // saves to the saveTester.ppm file with a currently existing image within the model
    // (this will overwrite any previous file that was created when running this test)
    model.savePPM("res/saveTester.ppm", "2x2");
    // load the newly saved file into the model and check that it is the same value as the 2x2 image
    model.readPPM("res/saveTester.ppm", "fileSaveSuccessful");
    assertArrayEquals(checkImage, model.imageState("fileSaveSuccessful"));

  }
}