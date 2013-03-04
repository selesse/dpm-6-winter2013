package ca.mcgill.dpm.winter2013.group6.navigator;

import lejos.nxt.NXTRegulatedMotor;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.util.Robot;

/**
 * Abstract implementation of the Navigator class, providing functionality that
 * both {@link NoObstacleNavigator} and {@link ObstacleNavigator} will inherit.
 *
 * @author Alex Selesse
 *
 */
public abstract class AbstractNavigator implements Navigator {
  protected Odometer odometer;
  protected boolean isNavigating;
  protected Robot robot;
  protected NXTRegulatedMotor leftMotor;
  protected NXTRegulatedMotor rightMotor;

  public AbstractNavigator(Odometer odometer, NXTRegulatedMotor leftMotor,
      NXTRegulatedMotor rightMotor) {
    this.odometer = odometer;
    this.leftMotor = leftMotor;
    this.rightMotor = rightMotor;
    this.isNavigating = false;
    this.robot = odometer.getRobot();
  }

  /**
   * Get the turning angle given an (x, y) coordinate. Takes care of finding the
   * shortest angle to turn to.
   *
   * @param desiredX
   *          The x-coordinate you want to go to.
   * @param desiredY
   *          The y-coordinate you want to go to.
   * @return The angle, in degrees, that you need to turn to if you want to go
   *         to (x, y).
   */
  public double getTurningAngle(double desiredX, double desiredY) {
    double x = desiredX - odometer.getX();
    double y = desiredY - odometer.getY();
    double odometerTheta = odometer.getTheta() * 180 / Math.PI;
    double turningAngle = 0.0;

    if (x > 0) {
      turningAngle = 180 / Math.PI * Math.atan(y / x);
    }
    else if (x < 0 && y > 0) {
      turningAngle = 180 / Math.PI * (Math.atan(y / x) + Math.PI);
    }
    else if (x < 0 && y < 0) {
      turningAngle = 180 / Math.PI * (Math.atan(y / x) - Math.PI);
    }

    if ((turningAngle - odometerTheta) < -180) {
      turningAngle = (turningAngle - odometerTheta) + 360;
    }
    else if ((turningAngle - odometerTheta) > 180) {
      turningAngle = (turningAngle - odometerTheta) - 360;
    }
    else {
      turningAngle = (turningAngle - odometerTheta);
    }

    return turningAngle;
  }

  @Override
  public void turnTo(double theta) {
    leftMotor.setSpeed(robot.getRotateSpeed());
    rightMotor.setSpeed(robot.getRotateSpeed());
    leftMotor.rotate(convertAngle(robot.getLeftWheelRadius(), robot.getWidth(), theta), true);
    rightMotor.rotate(-convertAngle(robot.getRightWheelRadius(), robot.getWidth(), theta), false);
  }

  public int convertDistance(double radius, double distance) {
    return (int) ((180.0 * distance) / (Math.PI * radius));
  }

  public int convertAngle(double radius, double width, double angle) {
    return convertDistance(radius, Math.PI * width * angle / 360.0);
  }

  @Override
  public boolean isNavigating() {
    return isNavigating;
  }
}
