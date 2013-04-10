package ca.mcgill.dpm.winter2013.group6.util;

import lejos.nxt.NXTRegulatedMotor;

/**
 * Robot class, representing the physical manifestation of our robot. Contains
 * characteristics such as wheel radius, width between wheels, and current
 * displacement and heading information.
 * 
 * @author Alex Selesse
 * 
 */
public class Robot {

  private double leftWheelRadius, rightWheelRadius, width;
  NXTRegulatedMotor rightMotor, leftMotor;
  private final int ROTATE_SPEED = 150;
  private final int FORWARD_SPEED = 175;

  /**
   * Base constructor of robot specifying its wheel and width values.
   * 
   * @param leftWheelRadius
   *          The radius of the left wheel.
   * @param rightWheelRadius
   *          The radius of the right wheel.
   * @param width
   *          The distance between the left and right wheels.
   */
  public Robot(double leftWheelRadius, double rightWheelRadius, double width,
      NXTRegulatedMotor leftMotor, NXTRegulatedMotor rightMotor) {
    this.leftWheelRadius = leftWheelRadius;
    this.rightWheelRadius = rightWheelRadius;
    this.width = width;
    this.leftMotor = leftMotor;
    this.rightMotor = rightMotor;
  }

  /**
   * Return the radius (in cm) of the left wheel of the robot.
   * 
   * @return The radius of the left wheel (in cm).
   */
  public double getLeftWheelRadius() {
    return leftWheelRadius;
  }

  /**
   * Return the radius (in cm) of the right wheel of the robot.
   * 
   * @return The radius of the right wheel (in cm).
   */
  public double getRightWheelRadius() {
    return rightWheelRadius;
  }

  /**
   * Get the width (i.e. distance from the left wheel to the right wheel) of the
   * robot.
   * 
   * @return The distance from the left wheel to the right wheel.
   */
  public double getWidth() {
    return width;
  }

  /**
   * Set the displacement and the heading to the dDH parameter.
   * 
   * @param dDH
   *          An array that will be modified with the values corresponding to
   *          the displacement and the heading.
   */
  public void getDisplacementAndHeading(double[] dDH) {
    int leftTacho, rightTacho;
    leftTacho = leftMotor.getTachoCount();
    rightTacho = rightMotor.getTachoCount();

    dDH[0] = (leftTacho * leftWheelRadius + rightTacho * rightWheelRadius) * Math.PI / 360.0;
    dDH[1] = (leftTacho * leftWheelRadius - rightTacho * rightWheelRadius) / width;
  }

  /**
   * Return the speed the wheels of the {@link Robot} should rotate at.
   * 
   * @return The speed the wheels of the robot should rotate at.
   */
  public int getRotateSpeed() {
    return ROTATE_SPEED;
  }

  /**
   * Return the speed the wheels of the {@link Robot} should move forward at.
   * 
   * @return The speed the wheels of the robot should move forward at.
   */
  public int getForwardSpeed() {
    return FORWARD_SPEED;
  }
}
