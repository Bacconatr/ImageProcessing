package imageprocessing.model;

/**
 * Allows the client to gain info about the state of the multiple images that are existing in
 * objects that implement this interface. Likely, the state of those list of images will be sent to
 * a view and represented accordingly.
 */
public interface ImageProcessingState {
  /**
   * Given an imageName, return the state of the image. This method will be likely used by future
   * views to appropriately display the state of images.
   *
   * @param imageName the name of the image whose state will be returned.
   * @return the state of the image.
   */
  ImageProcessingModelImpl.Pixel[][] imageState(String imageName) throws IllegalArgumentException;

  /**
   * Determines the number of images there are. Includes images that have been loaded by the user or
   * new images that have been created by the user.
   *
   * @return the number of images that currently exist.
   */
  int numImages();
}
