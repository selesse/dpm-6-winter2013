package ca.mcgill.dpm.winter2013.group6.util;

/**
 * Representation of the physical aspects of the playing field.
 * 
 * @author Alex Selesse
 * 
 */
public class PlayingField {
  private int horizontalLength;
  private int verticalLength;
  private final double CM_PER_SQUARE = 30.5;

  public PlayingField(int horizontalSquares, int verticalSquares) {
    this.horizontalLength = (int) (horizontalSquares * CM_PER_SQUARE);
    this.verticalLength = (int) (horizontalSquares * CM_PER_SQUARE);
  }

  /**
   * @return The horizontal length of the playing field (in centimeters).
   */
  public int getHorizontalLength() {
    return horizontalLength;
  }

  /**
   * @return The vertical length of the playing field (in centimeters).
   */
  public int getVerticalLength() {
    return verticalLength;
  }
}
