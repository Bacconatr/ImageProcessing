package imageprocessing.view;

import java.io.IOException;

/**
 *
 */
public class BasicImageProcessingView implements IProcessingImageView {

  private final Appendable appendable;

  /**
   *
   */
  public BasicImageProcessingView() {
    this.appendable = System.out;
  }

  /**
   *
   * @param appendable
   */
  public BasicImageProcessingView(Appendable appendable) {
    this.appendable = appendable;
  }

  /**
   *
   * @param message
   */
  @Override
  public void renderMessage(String message){
    try {
      this.appendable.append(message);
    }
    catch (IOException e) {
      System.out.println(e.getMessage());
    }
  }

  /**
   *
   */
  @Override
  public void introMessage() {

  }

  /**
   *
   */
  @Override
  public void menuHelp() {

  }
}
