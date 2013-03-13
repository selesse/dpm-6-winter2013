package ca.mcgill.dpm.winter2013.group6.navigator;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.util.Coordinate;
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
  protected Coordinate[] waypoints;
  private final double THRESHOLD = 2;

  public AbstractNavigator(Odometer odometer, NXTRegulatedMotor leftMotor,
      NXTRegulatedMotor rightMotor) {
    this.odometer = odometer;
    this.leftMotor = leftMotor;
    this.rightMotor = rightMotor;
    this.isNavigating = false;
    this.robot = odometer.getRobot();
  }

  @Override
  public void run() {
    isNavigating = true;
    for (Coordinate coordinate : waypoints) {
      travelTo(coordinate.getX(), coordinate.getY());
    }
    stop();
    isNavigating = false;
  }

  @Override
  public void travelTo(double x, double y) {
    double turningAngle = getTurningAngle(x, y);
    turnTo(turningAngle);

    // Travel straight.
    leftMotor.setSpeed(robot.getForwardSpeed());
    rightMotor.setSpeed(robot.getForwardSpeed());
    leftMotor.forward();
    rightMotor.forward();

    // Keep running until we're within an acceptable threshold.
    while (((x - odometer.getX() > THRESHOLD || x - odometer.getX() < -THRESHOLD))
        || ((y - odometer.getY() > THRESHOLD || y - odometer.getY() < -THRESHOLD))) {

      ;
    }
    stop();
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
    double odometerTheta = odometer.getTheta();
    double turningAngle = 0.0;
    turningAngle = Math.atan2(x, y);
    turningAngle = Math.toDegrees(turningAngle);

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
    Sound.beep();
    theta = optimDegree(theta);
    leftMotor.setSpeed(robot.getRotateSpeed());
    rightMotor.setSpeed(robot.getRotateSpeed());
    leftMotor.rotate(convertAngle(robot, theta), true);
    rightMotor.rotate(-convertAngle(robot, theta), false);
  }

  public void face(double theta) {
    turnTo(theta - odometer.getTheta());
  }

  public int convertDistance(double radius, double distance) {
    return (int) ((180.0 * distance) / (Math.PI * radius));
  }

  public int convertAngle(Robot robot, double angle) {
    return convertAngle(robot.getLeftWheelRadius(), robot.getWidth(), angle);
  }

  public int convertAngle(double radius, double width, double angle) {
    return convertDistance(radius, Math.PI * width * angle / 360.0);
  }

  public void stop() {
    leftMotor.stop(true);
    rightMotor.stop(false);
  }

  public void setSpeed(double leftSpeed, double rightSpeed) {
    leftMotor.setSpeed((int) leftSpeed);
    rightMotor.setSpeed((int) rightSpeed);
    if (leftSpeed > 0) {
      leftMotor.forward();
    }
    else {
      leftMotor.backward();
    }
    if (rightSpeed > 0) {
      rightMotor.forward();
    }
    else {
      rightMotor.backward();
    }
  }

  public void setRotateSpeed(int rotateSpeed) {
    setSpeed(rotateSpeed, -rotateSpeed);
  }

  @Override
  public boolean isNavigating() {
    return isNavigating;
  }

  public double optimDegree(double degree) {
    if (degree > 180) {
      return optimDegree(degree - 360);
    }
    else if (degree < -180) {
      return optimDegree(degree + 360);
    }
    return degree;
  }

  @Override
  public void setCoordinates(Coordinate[] waypoint) {
    this.waypoints = waypoint;
  }
}
