package imageprocessing.view;

/**
 *
 */
public interface IProcessingImageView {

  /**
   *
   * @param message
   */
  void renderMessage(String message);

  /**
   *
   */
  void introMessage();

  /**
   *
   */
  void menuHelp();
}

/* OH Notes:
the goal of this assignment is to think of everything that the view will need
we might be able to modify these interface but once we publish an interface we can no longer edit
 it, so we should design our view and model to anticipate

 "this is future proofing for whenever the view might need this information"
 as long as we have justification for a public method we can add it

 while it is okay to extend interfaces it may become unwieldy since you will have many interfaces
 it's simpler to just add the methods from the start

 don't anticipate the view. we are anticipating what a future view will need from the model
 */