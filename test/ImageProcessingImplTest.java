import org.junit.Before;
import org.junit.Test;

import ImageProcessing.model.ComponentBiFunctions.BlueBiFunction;
import ImageProcessing.model.ComponentBiFunctions.GreenBiFunction;
import ImageProcessing.model.ComponentBiFunctions.IntensityBiFunction;
import ImageProcessing.model.ComponentBiFunctions.LumaBiFunction;
import ImageProcessing.model.ComponentBiFunctions.RedBiFunction;
import ImageProcessing.model.ComponentBiFunctions.ValueBiFunction;
import ImageProcessing.model.FlipType;
import ImageProcessing.model.ImageProcessingModel;
import ImageProcessing.model.ImageProcessingModelImpl;
import ImageProcessing.model.Pixel;

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
    try {
      model.imageState("2x2-red-greyscale");
      fail("Did not input a non-existing image name");
    } catch (IllegalArgumentException e) {
      assertEquals(e.getMessage(), "The specified image does not exist.");
    }
    assertArrayEquals(model.imageState("2x2"), checkImage);
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

  @Test
  public void testCreateRepresentationBlue() {
    model.createRepresentation("2x2", "2x2-blue-greyscale", new BlueBiFunction());
    checkImage[0][0] = new Pixel(0, 0, 0);
    checkImage[0][1] = new Pixel(0, 0, 0);
    checkImage[1][0] = new Pixel(255, 255, 255);
    checkImage[1][1] = new Pixel(100, 100, 100);
    assertArrayEquals(model.imageState("2x2-blue-greyscale"), checkImage);
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

  @Test
  public void testCreateRepresentationValue() {
    model.createRepresentation("2x2", "2x2-value-greyscale", new ValueBiFunction());
    checkImage[0][0] = new Pixel(255, 255, 255);
    checkImage[0][1] = new Pixel(255, 255, 255);
    checkImage[1][0] = new Pixel(255, 255, 255);
    checkImage[1][1] = new Pixel(250, 250, 250);
    assertArrayEquals(model.imageState("2x2-value-greyscale"), checkImage);
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
  public void testSavePPM() {

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
}