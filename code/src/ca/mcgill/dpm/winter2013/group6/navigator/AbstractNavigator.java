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

    double dTheta = theta - odometer.getTheta();
    leftMotor.setSpeed(robot.getRotateSpeed());
    rightMotor.setSpeed(robot.getRotateSpeed());
    // uses the optimDegree() to optimize the turning degree
    dTheta = optimDegree(dTheta);

    leftMotor.rotate(convertAngle(robot.getLeftWheelRadius(), robot.getWidth(), dTheta), true);
    rightMotor.rotate(-convertAngle(robot.getRightWheelRadius(), robot.getWidth(), dTheta), false);

  }

  public void turn(double speed) {
    this.setSpeed(speed, -speed);
  }

  public void walk(double distance) {

    leftMotor.setSpeed(robot.getForwardSpeed());
    rightMotor.setSpeed(robot.getForwardSpeed());

    leftMotor.rotate(convertDistance(robot.getLeftWheelRadius(), distance));
    rightMotor.rotate(convertDistance(robot.getRightWheelRadius(), distance));

  }

  public int convertDistance(double radius, double distance) {
    return (int) ((180.0 * distance) / (Math.PI * radius));
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

  @Override
  public void rotate(double speed) {
    setSpeed(speed, -speed);
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
}
