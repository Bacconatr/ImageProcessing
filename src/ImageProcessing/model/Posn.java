package ImageProcessing.model;

/**
 * Represents a position with an x and y value on a 2d plane.
 */
public class Posn {
  private final int y;
  private final int x;

  /**
   *
   * @param y
   * @param x
   */
  public Posn(int y, int x) {
    this.y = y;
    this.x = x;
  }

  /**
   *
   * @return
   */
  public int getY() {
    return this.y;
  }

  /**
   *
   * @return
   */
  public int getX() {
    return this.x;
  }
}
