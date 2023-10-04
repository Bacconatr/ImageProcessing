import org.junit.Before;
import org.junit.Test;

import java.awt.*;

import imageprocessing.controller.Features;
import imageprocessing.controller.GuiScriptController;
import imageprocessing.controller.IProcessingImageController;
import imageprocessing.model.ImageProcessingExtraFeatures;
import imageprocessing.model.ImageProcessingExtraFeaturesImpl;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.view.IJFrameView;
import imageprocessing.view.IProcessingImageView;
import imageprocessing.view.JFrameViewImpl;

import static org.junit.Assert.*;

/**
 *
 */
public class GuiScriptControllerTest {

  ImageProcessingExtraFeatures model;
  IJFrameView view;
  Features controller;
  StringBuilder viewLog;
  StringBuilder modelLog;

  @Before
  public void init() {
    viewLog = new StringBuilder();
    modelLog = new StringBuilder();

    model = new ConfirmInputsExtraFeatures(modelLog);
    view = new ConfirmInputsGUIView(viewLog);
    controller = new GuiScriptController(model);
    controller.setView(view);
  }

  @Test
  public void testSetView() {

  }

  @Test
  public void testImageBrightness() {

  }

  @Test
  public void testColorChanging() {

  }

  @Test
  public void testFlipImage() {

  }

  @Test
  public void testComponentOptions() {

  }

  @Test
  public void testLoadImageToDisplay() {

  }

  @Test
  public void testSaveCurrentImage() {

  }

  @Test
  public void testColorHistogram() {

  }

  @Test
  public void testExitProgram() {

  }

}