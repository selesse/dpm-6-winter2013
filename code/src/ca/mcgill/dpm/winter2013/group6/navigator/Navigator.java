package ca.mcgill.dpm.winter2013.group6.navigator;

public interface Navigator extends Runnable {
  /**
   * Moves to the robot to (x, y) coordinates.
   * 
   * @param x
   *          The x-coordinate we're going to.
   * @param y
   *          The y-coordinate we're going to.
   */
  void travelTo(double x, double y);

  /**
   * Turn the robot by "theta" degrees.
   * 
   * @param theta
   *          The degrees we want to turn to.
   */
  void turnTo(double theta);

  /**
   * Check whether the robot is currently traveling or rotating
   * 
   * @return True if the robot is traveling or rotating, false otherwise.
   */
  boolean isNavigating();
}
