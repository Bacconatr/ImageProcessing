import org.junit.Test;

import java.io.StringReader;

import imageprocessing.controller.IProcessingImageController;
import imageprocessing.controller.ImageControllerAdvancedImpl;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.model.ImageProcessingModelImpl;
import imageprocessing.view.IProcessingImageView;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

/**
 * Tests for ImageControllerAdvancedImpl.
 */
public class ImageControllerAdvancedImplTest extends AbstractImageControllerTests {

  /**
   * Given a model, view, and input create a new image controller.
   *
   * @param model the model passed to this controller. In this case it is an ImageProcessingModel.
   * @param view  the view passed to this controller. In this case it is an IProcessingImageView
   * @param in    the Readable passed to this controller.
   * @return the appropriate ImageController.
   */
  @Override
  public IProcessingImageController builder(ImageProcessingModel model, IProcessingImageView view,
                                            Readable in) {
    return new ImageControllerAdvancedImpl(model, view, in);
  }

  // loading an image
  @Test
  public void testLoadImage() {
    in = new StringReader("load res/res2/2x2.png 2x2 q");
    executeProgram(in);
    String actual = out.toString().split("\\n")[1];
    assertEquals("load command successful.", actual);

    assertEquals(checkImage, model.imageState("2x2"));
  }

  // We have confirmed that load image works, so we can use it now in the tests for saving an image

  // saving an image
  // ALSO TESTS LOADING A PPM AND THEN SAVING AN IMAGE WORKS
  @Test
  public void testSaveImage() {
    in = new StringReader("load res/2by2.ppm 2x2 save res/res2/2x2-saveTest.png 2x2 load " +
            "res/res2/2x2-saveTest.png new q");
    executeProgram(in);
    String actual = out.toString().split("\\n")[2];
    assertEquals("save command successful.", actual);

    assertEquals(checkImage, model.imageState("new"));
  }


  // loading an image but saving as a ppm
  @Test
  public void testLoadImageSavePPM() {
    in = new StringReader("load res/res2/2x2.png 2x2 save res/res2/2by2-test.ppm 2x2 load " +
            "res/res2/2by2-test.ppm new q");
    executeProgram(in);
    assertEquals(checkImage, model.imageState("new"));
  }

  // testing loading from a png but saving as a bmp
  @Test
  public void testLoadImageSaveDifferentImageFormat() {
    in = new StringReader("load res/res2/2x2.png 2x2 save res/res2/2by2-test.bmp 2x2 load " +
            "res/res2/2by2-test.bmp new q");
    executeProgram(in);
    assertEquals(checkImage, model.imageState("new"));
  }

  // testing loading from a jpg but saving as a bmp
  @Test
  public void testLoadJPGSaveDifferentImageFormat() {
    in = new StringReader("load res/res2/2x2.jpg 2x2 save res/res2/2by2-test.bmp 2x2 load " +
            "res/res2/2by2-test.bmp new q");
    executeProgram(in);
    assertEquals(checkImage, model.imageState("new"));
  }

  @Test
  public void testSaveInvalidImageFormat() {
    in = new StringReader("load res/2by2.ppm 2x2 save 2x2.txt 2x2 q");
    executeProgram(in);
    String actual = out.toString().split("\n")[2];
    assertEquals("The arguments for the command were not valid. The filepath provided was not a " +
            "valid image.", actual);
  }

  // Testing the main method that runs the entire program
  // (just checking to see if the program is actually run and does something)
  @Test
  public void testScripting() {
    // runs a script file that should flip horizontally and vertically and should also darken
    // with 10 as the input and then brighten by 10 right after
    ImageProcessingProgram.main(new String[]{"-file", "res/testScript.txt"});
    // this also ensures that the file exists
    in = new StringReader("load res/SCRIPT_OUTPUT_TEST.png test q");
    executeProgram(in);
    // ensures that the operations were performed correctly (and incorrect inputs were handled
    // appropriately and did not cause the program to end)
    ImageProcessingModelImpl.Pixel[][] expectedImage = new ImageProcessingModelImpl.Pixel[2][2];
    expectedImage[0][0] = new ImageProcessingModelImpl.Pixel(250, 100, 100);
    expectedImage[0][1] = new ImageProcessingModelImpl.Pixel(10, 10, 255);
    expectedImage[1][0] = new ImageProcessingModelImpl.Pixel(10, 255, 10);
    expectedImage[1][1] = new ImageProcessingModelImpl.Pixel(255, 10, 10);
    assertArrayEquals(expectedImage, model.imageState("test"));
  }

}