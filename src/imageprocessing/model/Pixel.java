package imageprocessing.model;

import java.util.Objects;

/**
 * Represents a pixel of an image. This pixel contains RGB values with a range of 0-255 (8 bit).
 */
public class Pixel {
  private int red;
  private int green;
  private int blue;

  /**
   * Constructs a Pixel.
   *
   * @param red the red value of this pixel.
   * @param green the green value of this pixel.
   * @param blue the blue value of this pixel.
   */
  public Pixel(int red, int green, int blue) {
    if (red < 0 || green < 0 || blue < 0) {
      throw new IllegalArgumentException("A color value cannot be negative");
    }
    if (red > 255 || green > 255 || blue > 255) {
      throw new IllegalArgumentException("A color value cannot be greater than 255");
    }
    this.red = red;
    this.green = green;
    this.blue = blue;
  }

  /**
   * Provides the red value of this pixel.
   *
   * @return the red value.
   */
  public int getRed() {
    return red;
  }

  /**
   * Provides the green value of this pixel.
   *
   * @return the green value.
   */
  public int getGreen() {
    return green;
  }

  /**
   * Provides the blue value of this pixel.
   *
   * @return the blue value.
   */
  public int getBlue() {
    return blue;
  }

  /**
   * Adjusts the red value of this pixel.
   *
   * @param red the value that the red should be set to.
   */
  public void setRed(int red) {
    this.red = red;
  }

  /**
   * Adjusts the green value of this pixel.
   *
   * @param green the value that the green should be set to.
   */
  public void setGreen(int green) {
    this.green = green;
  }

  /**
   * Adjusts the blue value of this pixel.
   *
   * @param blue the value that the blue should be set to.
   */
  public void setBlue(int blue) {
    this.blue = blue;
  }

  /**
   * Determines if this is equal to the provided object.
   *
   * @param o the object to be compared to.
   * @return true if this is equal to the object provided, false otherwise.
   */
  @Override
  public boolean equals(Object o) {
    if (o == this) {
      return true;
    }
    if (!(o instanceof Pixel)) {
      return false;
    }
    Pixel other = (Pixel) o;
    return this.red == other.red
            && this.green == other.green
            && this.blue == other.blue;
  }

  /**
   * Computes the hash code for this Pixel object.
   *
   * @return the hashcode using the fields of this Pixel.
   */
  @Override
  public int hashCode() {
    return Objects.hash(this.red, this.green, this.blue);
  }

}
