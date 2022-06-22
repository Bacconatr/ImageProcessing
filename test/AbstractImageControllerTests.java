import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import imageprocessing.controller.IProcessingImageController;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ImageProcessingModelImpl;
import imageprocessing.view.BasicImageProcessingView;
import imageprocessing.view.IProcessingImageView;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 * Abstract Tests for Image Controllers. Made to reduce test code duplication and easily test future
 * controllers.
 */
public abstract class AbstractImageControllerTests {

  ImageProcessingModel model;
  IProcessingImageView view;
  IProcessingImageController controller;
  Readable in;
  Appendable out;
  StringBuilder log;
  ImageProcessingModelImpl.Pixel[][] checkImage;
  // ImageProcessingModel mock;

  public abstract IProcessingImageController builder(ImageProcessingModel model,
                                                     IProcessingImageView view, Readable in);

  @Before
  public void init() {
    in = new StringReader("q");
    out = new StringBuilder();
    model = new ImageProcessingModelImpl();
    view = new BasicImageProcessingView(out);
    controller = builder(model, view, in);
    log = new StringBuilder();

    // mock = new ConfirmInputsImageProcessingModel(log);
    checkImage = new ImageProcessingModelImpl.Pixel[2][2];
    checkImage[0][0] = new ImageProcessingModelImpl.Pixel(255, 0, 0);
    checkImage[0][1] = new ImageProcessingModelImpl.Pixel(0, 255, 0);
    checkImage[1][0] = new ImageProcessingModelImpl.Pixel(0, 0, 255);
    checkImage[1][1] = new ImageProcessingModelImpl.Pixel(250, 100, 100);
  }

  // CONSTRUCTOR TESTS

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    model = null;
    controller = builder(model, view, in);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    view = null;
    controller = builder(model, view, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    in = null;
    controller = builder(model, view, in);
  }

  @Test
  public void testConstructorAndFields() {
    // confirms the model starts with no images in it since no user input has gone to it through
    // the controller yet
    assertEquals(0, model.numImages());
    // checks that when the view is initialized nothing has been appended to the out
    assertEquals("", out.toString());
    // checks that no other input besides the one provided exists. (confirms that the readable is
    // initialized correctly).
    in = new StringReader("load res/2by2.ppm k q");
    recordInputs(in, log);
    assertEquals("", log.toString());

  }

  // INPUT TESTING

  @Test
  public void testBrightenInputs() {
    in = new StringReader("load res/2by2.ppm k brighten 10 k k-brighten q");
    recordInputs(in, log);
    assertEquals("10 k k-brighten; ", log.toString());
  }

  @Test
  public void testDarkenInputs() {
    in = new StringReader("load res/2by2.ppm k darken 10 k k-darken q");
    recordInputs(in, log);
    assertEquals("-10 k k-darken; ", log.toString());
  }

  @Test
  public void testRedInputs() {
    in = new StringReader("load res/2by2.ppm k red-component k k-redComponent q");
    recordInputs(in, log);
    assertEquals("k k-redComponent; ", log.toString());

  }

  @Test
  public void testGreenInputs() {
    in = new StringReader("load res/2by2.ppm k green-component k k-greenComponent q");
    recordInputs(in, log);
    assertEquals("k k-greenComponent; ", log.toString());
  }

  @Test
  public void testBlueInputs() {
    in = new StringReader("load res/2by2.ppm k blue-component k k-blueComponent q");
    recordInputs(in, log);
    assertEquals("k k-blueComponent; ", log.toString());
  }

  @Test
  public void testValueInputs() {
    in = new StringReader("load res/2by2.ppm k value-component k k-valueComponent q");
    recordInputs(in, log);
    assertEquals("k k-valueComponent; ", log.toString());
  }

  @Test
  public void testIntensityInputs() {

    in = new StringReader("load res/2by2.ppm k intensity-component k k-intensityComponent q");
    recordInputs(in, log);
    assertEquals("k k-intensityComponent; ", log.toString());

  }

  @Test
  public void testHorizontalFlipInputs() {
    in = new StringReader("load res/2by2.ppm k horizontal-flip k k-horizontalFlip q");
    recordInputs(in, log);
    // also checks that the correct enum was sent to the model (Horizontal)
    assertEquals("Horizontal k k-horizontalFlip; ", log.toString());
  }

  @Test
  public void testVerticalFlipInputs() {

    in = new StringReader("load res/2by2.ppm k vertical-flip k k-verticalFlip q");
    recordInputs(in, log);
    // also checks that the correct enum was sent to the model (Vertical)
    assertEquals("Vertical k k-verticalFlip; ", log.toString());

  }

  @Test
  public void testLumaInputs() {

    in = new StringReader("load res/2by2.ppm k luma-component k k-lumaComponent q");
    recordInputs(in, log);
    assertEquals("k k-lumaComponent; ", log.toString());

  }

  // OUTPUT TESTING

  @Test
  public void testWelcomeMessage() {
    in = new StringReader("q");
    executeProgram(in);
    String actual = out.toString().split("\n")[0];
    assertEquals("Welcome to this image processing program.", actual);

  }

  @Test
  public void testProgramEndMessage() {

    in = new StringReader("q");
    executeProgram(in);
    String actual = out.toString().split("\n")[1];
    assertEquals("Thank you for using the program!", actual);


  }

  @Test
  public void testLoadOutputs() {
    in = new StringReader("load res/2by2.ppm k q ");
    executeProgram(in);
    String actual = out.toString().split("\n")[1];
    assertEquals("load command successful.", actual);

  }

  @Test
  public void testSaveOutputs() {
    in = new StringReader("load res/2by2.ppm k save res/2by2-test.ppm k q");
    executeProgram(in);
    String actual = out.toString().split("\n")[2];
    assertEquals("save command successful.", actual);
  }

  @Test
  public void testComponentOutputs() {
    in = new StringReader("load res/2by2.ppm k red-component k k-red q");
    executeProgram(in);
    String actual = out.toString().split("\n")[2];
    assertEquals("red-component command successful.", actual);
  }

  @Test
  public void testAdjustLightOutputs() {
    in = new StringReader("load res/2by2.ppm k brighten 10 k k-bright q");
    executeProgram(in);
    String actual = out.toString().split("\n")[2];
    assertEquals("brighten command successful.", actual);
  }

  @Test
  public void testFlipOutputs() {
    in = new StringReader("load res/2by2.ppm k horizontal-flip k k-flip q");
    executeProgram(in);
    String actual = out.toString().split("\n")[2];
    assertEquals("horizontal-flip command successful.", actual);
  }


  @Test
  public void testQuitImmediately() {
    in = new StringReader("q");
    executeProgram(in);
    String actual = out.toString().split("\n")[1];
    assertEquals("Thank you for using the program!", actual);
  }

  @Test
  public void testQuitAfterThreeArguments() {
    // for brighten

    in = new StringReader("load res/2by2.ppm k brighten 10 k kbrighten q");
    executeProgram(in);
    String actual = out.toString().split("\n")[3];
    assertEquals("Thank you for using the program!", actual);


    // for darken

    in = new StringReader("load res/2by2.ppm k darken 10 k kbrighten q");
    executeProgram(in);
    actual = out.toString().split("\n")[3];
    assertEquals("Thank you for using the program!", actual);


  }

  @Test
  public void testQuitAfterSuccessfulCommand() {

    in = new StringReader("load res/2by2.ppm k q");
    executeProgram(in);
    String actual = out.toString().split("\n")[2];
    assertEquals("Thank you for using the program!", actual);

    in = new StringReader("load res/2by2.ppm k save res/k.ppm k q");
    executeProgram(in);
    actual = out.toString().split("\n")[2];
    assertEquals("Thank you for using the program!", actual);

    in = new StringReader("load res/2by2.ppm k red-component k kred q");
    executeProgram(in);
    actual = out.toString().split("\n")[2];
    assertEquals("Thank you for using the program!", actual);

    in = new StringReader("load res/2by2.ppm k green-component k kgreen q");
    executeProgram(in);
    actual = out.toString().split("\n")[2];
    assertEquals("Thank you for using the program!", actual);

    in = new StringReader("load res/2by2.ppm k blue-component k kblue q");
    executeProgram(in);
    actual = out.toString().split("\n")[2];
    assertEquals("Thank you for using the program!", actual);

    in = new StringReader("load res/2by2.ppm k value-component k kvalue q");
    executeProgram(in);
    actual = out.toString().split("\n")[2];
    assertEquals("Thank you for using the program!", actual);

    in = new StringReader("load res/2by2.ppm k intensity-component k kgreen q");
    executeProgram(in);
    actual = out.toString().split("\n")[2];
    assertEquals("Thank you for using the program!", actual);

    in = new StringReader("load res/2by2.ppm k luma-component k kgreen q");
    executeProgram(in);
    actual = out.toString().split("\n")[2];
    assertEquals("Thank you for using the program!", actual);

    in = new StringReader("load res/2by2.ppm k horizontal-flip k khorizontal q");
    executeProgram(in);
    actual = out.toString().split("\n")[2];
    assertEquals("Thank you for using the program!", actual);

    in = new StringReader("load res/2by2.ppm k vertical-flip k kvertical q");
    executeProgram(in);
    actual = out.toString().split("\n")[2];
    assertEquals("Thank you for using the program!", actual);


  }


  // invalid inputs that should cause an error message to be displayed

  @Test()
  public void testInvalidCommand() {
    in = new StringReader("hello res/2by2.ppm k q");
    executeProgram(in);
    String actual = out.toString().split("\n")[2];
    assertEquals("The command specified was not valid. Please try again.", actual);

  }

  @Test
  public void testComponentInvalidArguments() {
    in = new StringReader("load res/2by2.ppm k red-component eaf hello \n q");
    executeProgram(in);
    String actual = out.toString().split("\n")[2];
    assertEquals(
            "The arguments for the command were not valid. The file for this command" +
                    " was not found.", actual);
  }


  @Test(expected = IllegalStateException.class)
  public void testAdjustLightInvalidArguments() {

    in = new StringReader("load res/2by2.ppm k brighten hello eaf hello q");
    executeProgram(in);
  }

  @Test(expected = IllegalStateException.class)
  public void testFlippingInvalidArguments() {

    in = new StringReader("load res/2by2.ppm k horizontal-flip eaf hello q");
    executeProgram(in);
  }

  // ### New Tests Added after implementing filter and color transformation: sharpen, blur,
  // greyscale, sepia ###


  // #### INPUT CONFIRMATION ####

  @Test
  public void testSharpenInputs() {
    in = new StringReader("load res/2by2.ppm k sharpen k k-sharpen q");
    recordInputs(in, log);
    assertEquals("k k-sharpen; ", log.toString());
  }

  @Test
  public void testBlurInputs() {
    in = new StringReader("load res/2by2.ppm k blur k k-blur q");
    recordInputs(in, log);
    assertEquals("k k-blur; ", log.toString());
  }

  @Test
  public void testGreyscaleInputs() {
    in = new StringReader("load res/2by2.ppm k greyscale k k-greyscale q");
    recordInputs(in, log);
    assertEquals("k k-greyscale; ", log.toString());
  }

  @Test
  public void testSepiaInputs() {
    in = new StringReader("load res/2by2.ppm k sepia k k-sepia q");
    recordInputs(in, log);
    assertEquals("k k-sepia; ", log.toString());
  }

  // #### OUTPUT CONFIRMATION ####

  // INCORRECT ARGUMENTS
  @Test
  public void testInvalidSharpenArguments() {
    in = new StringReader("load res/2by2.ppm k sharpen WRONG k-sharpen \n q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals(
            "The arguments for the command were not valid. The file for this command was not " +
                    "found.",
            str[2]);
  }

  @Test
  public void testInvalidBlurArguments() {
    in = new StringReader("load res/2by2.ppm k blur WRONG k-blur \n q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals(
            "The arguments for the command were not valid. The file for this command was not " +
                    "found.",
            str[2]);
  }

  @Test
  public void testInvalidGreyScaleArguments() {
    in = new StringReader("load res/2by2.ppm k greyscale WRONG k-greyscale \n q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals(
            "The arguments for the command were not valid. The file for this command was not " +
                    "found.",
            str[2]);
  }

  @Test
  public void testInvalidSepiaArguments() {
    in = new StringReader("load res/2by2.ppm k sepia WRONG k-sepia \n q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals(
            "The arguments for the command were not valid. The file for this command was not " +
                    "found.",
            str[2]);
  }

  // VALID ARGUMENTS

  @Test
  public void testValidSharpen() {
    in = new StringReader("load res/2by2.ppm k sharpen k k-sharpen \n q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals(
            "sharpen command successful.",
            str[2]);
  }

  @Test
  public void testValidBlur() {
    in = new StringReader("load res/2by2.ppm k blur k k-blur \n q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals(
            "blur command successful.",
            str[2]);
  }

  @Test
  public void testValidGreyscale() {
    in = new StringReader("load res/2by2.ppm k greyscale k k-greyscale \n q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals(
            "greyscale command successful.",
            str[2]);
  }

  @Test
  public void testValidSepia() {
    in = new StringReader("load res/2by2.ppm k sepia k k-sepia \n q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals(
            "sepia command successful.",
            str[2]);
  }

  // ######

  @Test
  public void testValidInputsAfterInvalidAndLineSpacer() {
    in = new StringReader("load res/2by2.ppm k red-component eaf hello\n" +
            " red-component k k-red q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals(
            "The arguments for the command were not valid. The file for this command" +
                    " was not found.\n" +
                    "red-component command successful.",
            str[2] + "\n" + str[3]);
  }

  @Test
  public void testSaveInvalidPathArgument() {
    in = new StringReader("save k.bmp k q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals("The arguments for the command were not valid. The specified image does not " +
            "exist.", str[1]);
  }

  @Test
  public void testSaveInvalidPPMPathArgument() {
    in = new StringReader("save wrong.ppm k q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals("The arguments for the command were not valid. The specified image does not " +
            "exist.", str[1]);
  }

  @Test
  public void testLoadInvalidPathArgument() {
    in = new StringReader("load k.jpg k q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals(
            "The arguments for the command were not valid. Sorry, the file at the given path was " +
                    "not found.", str[1]);

  }

  @Test
  public void testLoadInvalidPPMPathArgument() {
    in = new StringReader("load wrong.ppm k q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals(
            "The arguments for the command were not valid. Sorry, the file at the given path was " +
                    "not found.", str[1]);

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
    in = new StringReader("load res/9pixel.ppm 9pixel q");
    executeProgram(in);
    // model.readPPM("res/9pixel.ppm", "9pixel");
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
    // checks that if the image name does not exist in the model an error is thrown
    in = new StringReader("save res/2by2-test.ppm 2x2 q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals(
            "The arguments for the command were not valid. The specified image does not exist.",
            str[1]);

    // saves to the saveTester.ppm file with a currently existing image within the model
    // (this will overwrite any previous file that was created when running this test)
    // load the newly saved file into the model and check that it is the same value as the
    // 2 x2 image
    in = new StringReader(
            "load res/saveTester.ppm fileSaveSuccessful save res/saveTester.ppm " +
                    "fileSaveSuccessful q");
    executeProgram(in);
    assertArrayEquals(checkImage, model.imageState("fileSaveSuccessful"));

    // ***** Note that checkImage is the same as 2x2. It is the expected and is defined in
    // the init

  }

  // Helper to quickly execute the program given user inputs
  protected void executeProgram(Readable in) {
    controller = builder(model, view, in);
    controller.startProcessing();
  }

  // Tests the user inputs using a mock
  protected void recordInputs(Readable in, StringBuilder log) {
    model = new ConfirmInputsImageProcessingModel(log);
    view = new BasicImageProcessingView(out);
    controller = builder(model, view, in);
    controller.startProcessing();
  }
}