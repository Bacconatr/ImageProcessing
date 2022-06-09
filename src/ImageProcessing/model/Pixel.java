package ImageProcessing.model;

/**
 *
 */
public class Pixel {
  private int red;
  private int green;
  private int blue;

  /**
   *
   * @param red
   * @param green
   * @param blue
   */
  public Pixel(int red, int green, int blue) {
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   *
   * @return
   */
  public int getRed() {
    return red;
  }

  /**
   *
   * @return
   */
  public int getGreen() {
    return green;
  }

  /**
   *
   * @return
   */
  public int getBlue() {
    return blue;
  }

  /**
   *
   * @param red
   */
  public void setRed(int red) {
    this.red = red;
  }

  /**
   *
   * @param green
   */
  public void setGreen(int green) {
    this.green = green;
  }

  /**
   *
   * @param blue
   */
  public void setBlue(int blue) {
    this.blue = blue;
  }

}
