import org.junit.Before;
import org.junit.Test;

import imageprocessing.view.BasicImageProcessingView;
import imageprocessing.view.IProcessingImageView;

import static org.junit.Assert.assertEquals;

/**
 * Tests for BasicImageProcessingView.
 */
public class ProcessingImageViewImplTest {

  Appendable out;
  IProcessingImageView view;

  @Before
  public void init() {
    out = new StringBuilder();
    view = new BasicImageProcessingView(out);
  }

  @Test(expected = IllegalArgumentException.class)
  public void testConstructorNullParameters() {
    out = null;
    view = new BasicImageProcessingView(out);
  }

  @Test
  public void testRenderMessage() {
    view.renderMessage("OOD :)");
    assertEquals("OOD :)", out.toString());
  }

  @Test
  public void testIntroMessage() {
    view.introMessage();
    assertEquals("Welcome to this image processing program.", out.toString());
  }
}