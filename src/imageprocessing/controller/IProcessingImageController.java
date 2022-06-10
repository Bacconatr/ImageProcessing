package imageprocessing.controller;

/**
 * Represents a controller that will handle the calling of image processing (the model) and
 * rendering messages (the view) for this image processing program.
 */
public interface IProcessingImageController {

  /**
   *
   */
  void startProcessing() throws IllegalStateException;
}
