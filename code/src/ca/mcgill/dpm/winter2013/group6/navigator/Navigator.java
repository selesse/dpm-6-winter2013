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
   * Moves the robot to (x, y) coordinates.
   * 
   * @param x
   *          The x-coordinate we're going to.
   * @param y
   *          The y-coordinate we're going to.
   */
  void travelTo(double x, double y);

  /**
   * Moves the robot to the particular {@link Coordinate} given.
   * 
   * @param point
   *          The point we're going to.
   */
  void travelTo(Coordinate point);

  /**
   * Makes the robot walk {@code distance} centimeters.
   * 
   * @param distance
   *          The <b>signed</b> distance in which the robot should be traveling.
   */
  void travelStraight(double distance);

  /**
   * Turn the robot by "theta" degrees.
   * 
   * @param theta
   *          The degrees we want to turn to.
   */

  void turnTo(double theta);

  /**
   * Turn the robot so that it faces (x, y).
   * 
   * @param x
   * @param y
   */
  void turnTo(double x, double y);

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
  void setMotorRotateSpeed(int rotateSpeed);

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

  /**
   * Make the robot face an absolute angle.
   * 
   * @param theta
   *          The absolute angle for which the robot will face.
   */
  void face(double theta);

  /**
   * Return the current coordinate heading (or null if there is none yet).
   * 
   * @return
   */
  Coordinate getCurrentHeading();

  /**
   * Turns the robot to <code>theta</code>, setting the robot acceleration to
   * <code>robotAcceleration</code> and setting the rotation speed to
   * <code>robotRotateSpeed</code>.
   * 
   * @param theta
   * @param robotAcceleration
   * @param robotRotateSpeed
   */
  void turnTo(double theta, int robotAcceleration, int robotRotateSpeed);

  void turnTo(Coordinate goalCoordinate);

}
