package ca.mcgill.dpm.winter2013.group6.navigator;

import lejos.nxt.NXTRegulatedMotor;
import ca.mcgill.dpm.winter2013.group6.util.Coordinate;

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

  /**
   * Stop the robot from moving.
   */
  void stop();

  /**
   * Change the rotation speed of the robot.
   * 
   * @param rotateSpeed
   *          The speed you want the robot rotate at.
   */
  void setRotateSpeed(int rotateSpeed);

  /**
   * Set the coordinates that we want to travel to.
   * 
   * @param coordinates
   *          An array of coordinates where we're traveling to.
   */
  void setCoordinates(Coordinate[] coordinates);

  /**
   * Get the left motor of the robot.
   * 
   * @return
   */
  NXTRegulatedMotor getLeftMotor();

  /**
   * Get the right motor of the robot.
   * 
   * @return
   */
  NXTRegulatedMotor getRightMotor();

}
