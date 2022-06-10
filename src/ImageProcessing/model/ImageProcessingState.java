package ImageProcessing.model;

/**
 * Allows the client to gain info about the state of the multiple images that are existing in
 * objects that implement this interface. Likely, the state of those list of images will be sent
 * to a view and represented accordingly.
 */
public interface ImageProcessingState {
  /**
   * Given an imageName, return the state of the image. This method will be likely used by future
   * views to appropriately display the state of images.
   *
   * @param imageName the name of the image whose state will be returned.
   * @return the state of the image.
   */
  Pixel[][] imageState(String imageName) throws IllegalArgumentException;

  /**
   *
   *
   * @return
   */
  int numImages();

  // OH Notes:
  // blind yourself about how the class has been implemented
  // think of yourself as a client and ask if you have all the information that I need,
  // regardless of the fields

  // what is the purpose statement of this model?
  // we should have a strong purpose statement and the methods will follow from that

  // hopefully our interface will serve our needs in the future, but if there's truly a different
  // feature then we extend since it doesn't fall under this models purpose statement.

  // anything that could fall under the purpose of this interface we add a method since the
  // client will likely need it

  // Concrete:
  // if part of our purpose statement is saying we are representing multiple images that the user
  // can modify and perform operations on. Then yes, querying how many images there are is
  // something relevant to that purpose.

  // remember that interfaces are separated from the internal implementation
  // interface should be separate from the implementation
  // e.g. if we needed to change from a 2d pixel array to something else
}
