package ca.mcgill.dpm.winter2013.group6.util;

/**
 * Coordinate helper class for 2 dimensional coordinates, essentially a
 * representation of (x, y).
 * 
 * @author Alex Selesse
 * 
 */
public class Coordinate {
  private double x;
  private double y;

  /**
   * Create a new set of coordinates (x, y).
   * 
   * @param x
   *          X-coordinate
   * @param y
   *          Y-coordinate
   */
  public Coordinate(int x, int y) {
    this.x = x;
    this.y = y;
  }

  /**
   * Return the x value of the coordinate.
   * 
   * @return The x-coordinate.
   */
  public double getX() {
    return x;
  }

  /**
   * Return the y value of the coordinate.
   * 
   * @return The y-coordinate.
   */
  public double getY() {
    return y;
  }

  @Override
  public String toString() {
    return "(" + getX() + ", " + getY() + ")";
  }

  public static Coordinate getCoordinateFromBlock(int x, int y) {
    return new Coordinate((int) (30.5 * x), (int) (30.5 * y));
  }

  public static Coordinate getCoordinateFromBallDispenserLocation(int ballDispenserX,
      int ballDispenserY) {
    double desiredReceivePositionX;
    double desiredReceivePositionY;

    double ballDispenserPositionX = (ballDispenserX * 30.5);
    double ballDispenserPositionY = (ballDispenserY * 30.5);

    if (ballDispenserX < 0) {
      desiredReceivePositionX = (ballDispenserPositionX + 2 * (30.5));
      desiredReceivePositionY = ballDispenserPositionY;
    }
    else if (ballDispenserY < 0) {
      desiredReceivePositionX = ballDispenserPositionX;
      desiredReceivePositionY = (ballDispenserPositionY + 2 * (30.5));
    }
    else if (ballDispenserX > ballDispenserY) {
      desiredReceivePositionX = (ballDispenserPositionX - 2 * (30.5));
      desiredReceivePositionY = ballDispenserPositionY;
    }
    else {
      desiredReceivePositionX = ballDispenserPositionX;
      desiredReceivePositionY = (ballDispenserPositionY - 2 * (30.5));
    }

    return new Coordinate((int) desiredReceivePositionX, (int) desiredReceivePositionY);
  }
}
