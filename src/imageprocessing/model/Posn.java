package imageprocessing.model;

/**
 * A simple Posn. Represents a position with an x and y value on a 2d plane. The coordinates of this
 * posn are in (y,x) rather than (x,y) to represents rows and columns.
 */
public class Posn {
  private final int y;
  private final int x;

  /**
   * Constructs a Posn. Is reordered from (x,y) to (y,x) to think of it as in terms of rows and
   * columns.
   *
   * @param y the y value of this posn (the row).
   * @param x the x value of this posn (the column).
   */
  public Posn(int y, int x) {
    this.y = y;
    this.x = x;
  }

  /**
   * Provides the y value of this posn.
   *
   * @return the y value.
   */
  public int getY() {
    return this.y;
  }

  /**
   * Provides the x value of this posn.
   *
   * @return the x value.
   */
  public int getX() {
    return this.x;
  }
}
