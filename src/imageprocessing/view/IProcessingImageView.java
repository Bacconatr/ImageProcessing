package imageprocessing.view;

/**
 * Represents a view that is able to display necessary messages for a program. Does not support
 * rendering of the image states. Only supports the rendering of basic messages.
 */
public interface IProcessingImageView {

  /**
   * Renders a provided message by appending it to the appendable.
   *
   * @param message the message to be rendered.
   */
  void renderMessage(String message);

  // this could just be private and called in the constructor
  /**
   * Displays an intro message when the program is started.
   */
  void introMessage();
}