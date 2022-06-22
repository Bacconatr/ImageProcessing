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
  ImageProcessingModelImpl.Pixel[][] twoByTwo;
  ImageProcessingModelImpl.Pixel[][] threeByThree;
  ImageProcessingModelImpl.Pixel[][] fiveByFive;
  ImageProcessingModelImpl.Pixel[][] greyscale;

  @Before
  public void init() {
    model = new ImageProcessingModelImpl();
    twoByTwo = new ImageProcessingModelImpl.Pixel[2][2];
    twoByTwo[0][0] = new ImageProcessingModelImpl.Pixel(255, 0, 0);
    twoByTwo[0][1] = new ImageProcessingModelImpl.Pixel(0, 255, 0);
    twoByTwo[1][0] = new ImageProcessingModelImpl.Pixel(0, 0, 255);
    twoByTwo[1][1] = new ImageProcessingModelImpl.Pixel(250, 100, 100);
    model.addImage(twoByTwo, "2x2");

    threeByThree = new ImageProcessingModelImpl.Pixel[3][3];
    threeByThree[0][0] = new ImageProcessingModelImpl.Pixel(255, 0, 0);
    threeByThree[0][1] = new ImageProcessingModelImpl.Pixel(0, 255, 0);
    threeByThree[0][2] = new ImageProcessingModelImpl.Pixel(0, 0, 255);
    threeByThree[1][0] = new ImageProcessingModelImpl.Pixel(0, 0, 0);
    threeByThree[1][1] = new ImageProcessingModelImpl.Pixel(255, 255, 255);
    threeByThree[1][2] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    threeByThree[2][0] = new ImageProcessingModelImpl.Pixel(250, 100, 100);
    threeByThree[2][1] = new ImageProcessingModelImpl.Pixel(230, 20, 70);
    threeByThree[2][2] = new ImageProcessingModelImpl.Pixel(94, 232, 255);

    fiveByFive = new ImageProcessingModelImpl.Pixel[5][5];
    fiveByFive[0][0] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[0][1] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[0][2] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[0][3] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[0][4] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[1][0] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[1][1] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[1][2] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[1][3] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[1][4] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[2][0] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[2][1] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[2][2] = new ImageProcessingModelImpl.Pixel(80, 90, 100);
    fiveByFive[2][3] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[2][4] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[3][0] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[3][1] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[3][2] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[3][3] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[3][4] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[4][0] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[4][1] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[4][2] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[4][3] = new ImageProcessingModelImpl.Pixel(100, 100, 100);
    fiveByFive[4][4] = new ImageProcessingModelImpl.Pixel(100, 100, 100);


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
    model.addImage(twoByTwo, "2x2");
    model.flip(FlipType.Vertical, "2x2", "2x2-verticalFlip");


    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(255, 0, 0);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(0, 255, 0);
    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(0, 0, 255);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(250, 100, 100);


    assertArrayEquals(checkImage, model.imageState("2x2-verticalFlip"));

    model = new ImageProcessingModelImpl();
    model.addImage(twoByTwo, "2x2");
    model.flip(FlipType.Horizontal, "2x2", "2x2-verticalFlip");


    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(255, 0, 0);
    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(0, 255, 0);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(0, 0, 255);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(250, 100, 100);

    assertArrayEquals(checkImage, model.imageState("2x2-verticalFlip"));

  }

  // NEEDS

  @Test(expected = IllegalArgumentException.class)
  public void testIllegalFileNameFlip() {
    model = new ImageProcessingModelImpl();
    model.addImage(twoByTwo, "2x2");
    model.flip(FlipType.Horizontal, "helloWorld", "2x2-verticalFlip");

  }

  // NEEDS
  @Test
  public void testAdjustLight() {
    // brighten an image
    model = new ImageProcessingModelImpl();
    model.addImage(twoByTwo, "2x2");
    model.adjustLight(10, "2x2", "2x2-brighten");

    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(255, 10, 10);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(10, 255, 10);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(10, 10, 255);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(255, 110, 110);

    assertArrayEquals(checkImage, model.imageState("2x2-brighten"));

    // darken an image

    model = new ImageProcessingModelImpl();
    model.addImage(twoByTwo, "2x2");
    model.adjustLight(-10, "2x2", "2x2-darken");

    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(245, 0, 0);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(0, 245, 0);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(0, 0, 245);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(240, 90, 90);

    assertArrayEquals(checkImage, model.imageState("2x2-darken"));
  }

  // NEEDS
  @Test(expected = IllegalArgumentException.class)
  public void testIllegalNameAdjustLight() {
    model = new ImageProcessingModelImpl();
    model.addImage(twoByTwo, "2x2");
    model.adjustLight(-10, "iloveood", "2x2-brighten");
  }

  @Test
  public void testHorizontalAndVertical() {

    model = new ImageProcessingModelImpl();
    model.addImage(twoByTwo, "2x2");
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
    model.addImage(twoByTwo, "2x2");
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
  public void testBlur() {
    model = new ImageProcessingModelImpl();
    model.addImage(threeByThree, "3x3");
    model.createRepresentation("3x3", "3x3",
            new FilterBiFunction(ImageControllerImpl.BLUR_KERNEL));

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

    assertArrayEquals(model.imageState("3x3"), blurred);
  }

  @Test
  public void testSharpen() {
    model = new ImageProcessingModelImpl();
    model.addImage(fiveByFive, "5x5");

    model.createRepresentation("5x5", "5x5",
            new FilterBiFunction(ImageControllerImpl.SHARPEN_KERNEL));

    ImageProcessingModelImpl.Pixel[][] sharpen = new ImageProcessingModelImpl.Pixel[5][5];

    sharpen[0][0] = new ImageProcessingModelImpl.Pixel(115, 113, 112);
    sharpen[0][1] = new ImageProcessingModelImpl.Pixel(152, 151, 150);
    sharpen[0][2] = new ImageProcessingModelImpl.Pixel(115, 113, 112);
    sharpen[0][3] = new ImageProcessingModelImpl.Pixel(152, 151, 150);
    sharpen[0][4] = new ImageProcessingModelImpl.Pixel(115, 113, 112);
    sharpen[1][0] = new ImageProcessingModelImpl.Pixel(152, 151, 150);
    sharpen[1][1] = new ImageProcessingModelImpl.Pixel(207, 210, 212);
    sharpen[1][2] = new ImageProcessingModelImpl.Pixel(157, 160, 162);
    sharpen[1][3] = new ImageProcessingModelImpl.Pixel(207, 210, 212);
    sharpen[1][4] = new ImageProcessingModelImpl.Pixel(152, 151, 150);
    sharpen[2][0] = new ImageProcessingModelImpl.Pixel(115, 113, 112);
    sharpen[2][1] = new ImageProcessingModelImpl.Pixel(157, 160, 162);
    sharpen[2][2] = new ImageProcessingModelImpl.Pixel(80, 90, 100);
    sharpen[2][3] = new ImageProcessingModelImpl.Pixel(157, 160, 162);
    sharpen[2][4] = new ImageProcessingModelImpl.Pixel(115, 113, 112);
    sharpen[3][0] = new ImageProcessingModelImpl.Pixel(152, 151, 150);
    sharpen[3][1] = new ImageProcessingModelImpl.Pixel(207, 210, 212);
    sharpen[3][2] = new ImageProcessingModelImpl.Pixel(157, 160, 162);
    sharpen[3][3] = new ImageProcessingModelImpl.Pixel(207, 210, 212);
    sharpen[3][4] = new ImageProcessingModelImpl.Pixel(152, 151, 150);
    sharpen[4][0] = new ImageProcessingModelImpl.Pixel(115, 113, 112);
    sharpen[4][1] = new ImageProcessingModelImpl.Pixel(152, 151, 150);
    sharpen[4][2] = new ImageProcessingModelImpl.Pixel(115, 113, 112);
    sharpen[4][3] = new ImageProcessingModelImpl.Pixel(152, 151, 150);
    sharpen[4][4] = new ImageProcessingModelImpl.Pixel(115, 113, 112);

    assertArrayEquals(sharpen, model.imageState("5x5"));
  }


}