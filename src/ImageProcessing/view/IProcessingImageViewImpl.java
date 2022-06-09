package ImageProcessing.view;

import java.io.IOException;

/**
 *
 */
public class IProcessingImageViewImpl implements IProcessingImageView {

  private final Appendable appendable;

  /**
   *
   */
  public IProcessingImageViewImpl() {
    this.appendable = System.out;
  }

  /**
   *
   * @param appendable
   */
  public IProcessingImageViewImpl(Appendable appendable) {
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
