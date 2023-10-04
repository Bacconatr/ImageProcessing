import imageprocessing.controller.IProcessingImageController;
import imageprocessing.controller.ImageControllerImpl;
import imageprocessing.model.ImageProcessingExtraFeatures;
import imageprocessing.model.ImageProcessingModel;
import imageprocessing.view.IProcessingImageView;

/**
 * Tests for ImageControllerImpl.
 */
public class ImageControllerImplTest extends AbstractImageControllerTests {

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
    return new ImageControllerImpl(model, view, in);
  }
}