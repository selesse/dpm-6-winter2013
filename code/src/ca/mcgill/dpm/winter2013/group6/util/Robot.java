package ca.mcgill.dpm.winter2013.group6.util;

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
  public Robot(double leftWheelRadius, double rightWheelRadius, double width) {
    this.leftWheelRadius = leftWheelRadius;
    this.rightWheelRadius = rightWheelRadius;
    this.width = width;
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
    // FIXME
  }

  /**
   * Return the speed the wheels of the {@link Robot} should rotate at.
   *
   * @return The speed the wheels of the robot should rotate at.
   */
  public int getRotateSpeed() {
    // FIXME
    return 0;
  }
}
