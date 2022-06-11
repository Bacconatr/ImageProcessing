package imageprocessing.view;

import java.io.IOException;

/**
 * Represents a Basics view that is used in this ImageProcessing program. It is used to render basic
 * messages and an intro message. It does not render the state of the images.
 */
public class BasicImageProcessingView implements IProcessingImageView {

  private final Appendable appendable;

  /**
   * Constructs a BasicImageProcessingView that defaults to System.out.
   */
  public BasicImageProcessingView() {
    this.appendable = System.out;
  }

  /**
   * Constructs a BasicImageProcessingView with the given appendable.
   *
   * @param appendable the appendable that will be appended to.
   */
  public BasicImageProcessingView(Appendable appendable) {
    if (appendable == null) {
      throw new IllegalArgumentException("Cannot provide null parameters to the view.");
    }
    this.appendable = appendable;
  }

  /**
   * Renders a provided message by appending it to the appendable.
   *
   * @param message the message to be rendered.
   */
  @Override
  public void renderMessage(String message) {
    try {
      this.appendable.append(message);
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   * Displays an intro message when the program is started.
   */
  @Override
  public void introMessage() {
    try {
      this.appendable.append("Welcome to this image processing program.");
    } catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }
}
