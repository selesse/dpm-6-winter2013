package ca.mcgill.dpm.winter2013.group6.navigator;

/**
 * Navigator interface which defines a set of methods that any robot that can
 * navigate should adhere to. Specifically, going to a set of coordinates and
 * turning.
 * 
 * @author Alex Selesse
 * 
 */
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
   * Moves to the robot to (x, y, omega) coordinates.
   * 
   * @param x
   *          The x-coordinate we're going to.
   * @param y
   *          The y-coordinate we're going to.
   * 
   * @param theta
   *          The angle the robot will travelling to.
   */
  void travelTo(double x, double y, double theta);

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

  void stop();

  void rotate(double speed);

  void walk(double distance);

}
