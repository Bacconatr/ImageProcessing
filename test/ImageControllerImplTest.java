import org.junit.Before;
import org.junit.Test;

import java.io.StringReader;

import imageprocessing.controller.IProcessingImageController;
import imageprocessing.controller.ImageControllerImpl;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ImageProcessingModelImpl;
import imageprocessing.view.BasicImageProcessingView;
import imageprocessing.view.IProcessingImageView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for ImageControllerImpl.
 */
public class ImageControllerImplTest {

  ImageProcessingModel model;
  IProcessingImageView view;
  IProcessingImageController controller;
  Readable in;
  Appendable out;
  StringBuilder log;

  // ImageProcessingModel mock;

  @Before
  public void init() {
    in = new StringReader("q");
    out = new StringBuilder();
    model = new ImageProcessingModelImpl();
    view = new BasicImageProcessingView(out);
    controller = new ImageControllerImpl(model, view, in);
    log = new StringBuilder();

    // mock = new ConfirmInputsImageProcessingModel(log);

  }

  // CONSTRUCTOR TESTS

  @Test(expected = IllegalArgumentException.class)
  public void testNullModel() {
    model = null;
    controller = new ImageControllerImpl(model, view, in);
  }


  @Test(expected = IllegalArgumentException.class)
  public void testNullView() {
    view = null;
    controller = new ImageControllerImpl(model, view, in);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testNullAppendable() {
    in = null;
    controller = new ImageControllerImpl(model, view, in);
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
    assertEquals("res/2by2.ppm k; ", log.toString());

  }

  // INPUT TESTING

  @Test
  public void testLoadInputs() {
    in = new StringReader("load res/2by2.ppm k q");
    recordInputs(in, log);
    assertEquals("res/2by2.ppm k; ", log.toString());
  }

  @Test
  public void testSaveInputs() {
    in = new StringReader("save res/2by2.ppm k q");
    recordInputs(in, log);
    assertEquals("res/2by2.ppm k; ", log.toString());

  }

  @Test
  public void testSaveInputsWithLoad() {
    in = new StringReader("load res/2by2.ppm k save res/inputsTestResult.ppm k q");
    recordInputs(in, log);
    assertEquals("res/2by2.ppm k; res/inputsTestResult.ppm k; ", log.toString());
  }

  @Test
  public void testBrightenInputs() {
    in = new StringReader("load res/2by2.ppm k brighten 10 k k-brighten q");
    recordInputs(in, log);
    assertEquals("res/2by2.ppm k; 10 k k-brighten; ", log.toString());
  }

  @Test
  public void testDarkenInputs() {
    in = new StringReader("load res/2by2.ppm k darken 10 k k-darken q");
    recordInputs(in, log);
    assertEquals("res/2by2.ppm k; -10 k k-darken; ", log.toString());
  }

  @Test
  public void testRedInputs() {
    in = new StringReader("load res/2by2.ppm k red-component k k-redComponent q");
    recordInputs(in, log);
    assertEquals("res/2by2.ppm k; k k-redComponent; ", log.toString());

  }

  @Test
  public void testGreenInputs() {
    in = new StringReader("load res/2by2.ppm k green-component k k-greenComponent q");
    recordInputs(in, log);
    assertEquals("res/2by2.ppm k; k k-greenComponent; ", log.toString());
  }

  @Test
  public void testBlueInputs() {
    in = new StringReader("load res/2by2.ppm k blue-component k k-blueComponent q");
    recordInputs(in, log);
    assertEquals("res/2by2.ppm k; k k-blueComponent; ", log.toString());
  }

  @Test
  public void testValueInputs() {
    in = new StringReader("load res/2by2.ppm k value-component k k-valueComponent q");
    recordInputs(in, log);
    assertEquals("res/2by2.ppm k; k k-valueComponent; ", log.toString());
  }

  @Test
  public void testIntensityInputs() {

    in = new StringReader("load res/2by2.ppm k intensity-component k k-intensityComponent q");
    recordInputs(in, log);
    assertEquals("res/2by2.ppm k; k k-intensityComponent; ", log.toString());

  }

  @Test
  public void testHorizontalFlipInputs() {
    in = new StringReader("load res/2by2.ppm k horizontal-flip k k-horizontalFlip q");
    recordInputs(in, log);
    // also checks that the correct enum was sent to the model (Horizontal)
    assertEquals("res/2by2.ppm k; Horizontal k k-horizontalFlip; ", log.toString());
  }

  @Test
  public void testVerticalFlipInputs() {

    in = new StringReader("load res/2by2.ppm k vertical-flip k k-verticalFlip q");
    recordInputs(in, log);
    // also checks that the correct enum was sent to the model (Vertical)
    assertEquals("res/2by2.ppm k; Vertical k k-verticalFlip; ", log.toString());

  }

  @Test
  public void testLumaInputs() {

    in = new StringReader("load res/2by2.ppm k luma-component k k-lumaComponent q");
    recordInputs(in, log);
    assertEquals("res/2by2.ppm k; k k-lumaComponent; ", log.toString());

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
    in = new StringReader("load res/2by2.ppm k save res/2by2.ppm k q");
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
    assertEquals("load command successful.", actual);
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
  public void testInvalidInputsComponent() {
    in = new StringReader("load res/2by2.ppm k red-component eaf hello \n q");
    executeProgram(in);
    String actual = out.toString().split("\n")[2];
    assertEquals(
            "The arguments for the command were not valid. The file for this component " +
                    "grey-scaling was not found.", actual);
  }

  @Test
  public void testValidInputsAfterInvalidAndLineSpacer() {
    in = new StringReader("load res/2by2.ppm k red-component eaf hello\n" +
            " red-component k k-red q");
    executeProgram(in);
    String[] str = out.toString().split("\n");
    assertEquals(
            "The arguments for the command were not valid. The file for this component " +
                    "grey-scaling was not found.\n" +
                    "red-component command successful.",
            str[2] + "\n" + str[3]);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidInputsAdjustLight() {

    in = new StringReader("load res/2by2.ppm k brighten hello eaf hello q");
    executeProgram(in);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidInputsFlipping() {

    in = new StringReader("load res/2by2.ppm k horizontal-flip eaf hello q");
    executeProgram(in);
  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidInputsSave() {

    in = new StringReader("save k k q");
    executeProgram(in);


  }

  @Test(expected = IllegalStateException.class)
  public void testInvalidInputsLoad() {

    in = new StringReader("load k k q");
    executeProgram(in);
  }


  // Helper to quickly execute the program given user inputs
  private void executeProgram(Readable in) {
    controller = new ImageControllerImpl(model, view, in);
    controller.startProcessing();
  }

  // Tests the user inputs using a mock
  private void recordInputs(Readable in, StringBuilder log) {
    model = new ConfirmInputsImageProcessingModel(log);
    view = new BasicImageProcessingView(out);
    controller = new ImageControllerImpl(model, view, in);
    controller.startProcessing();
  }
}