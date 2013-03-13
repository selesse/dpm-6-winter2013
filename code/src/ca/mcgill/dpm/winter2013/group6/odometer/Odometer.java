package ca.mcgill.dpm.winter2013.group6.odometer;

import lejos.nxt.Motor;
import ca.mcgill.dpm.winter2013.group6.util.Robot;

/**
 * An odometer thread implementation, taken from the provided lab 3 code.
 * 
 * @author Alex Selesse
 * 
 */
public class Odometer extends Thread {
  private double x, y, theta;
  private static final long ODOMETER_PERIOD = 25;
  private Object lock;
  private Robot robot;
  private double[] oldDH, dDH;

  // default constructor
  public Odometer(Robot robot) {
    x = 0.0;
    y = 0.0;
    theta = 0.0;
    this.robot = robot;
    lock = new Object();
    this.oldDH = new double[2];
    this.dDH = new double[2];
  }

  @Override
  public void run() {
    Motor.A.resetTachoCount();
    Motor.B.resetTachoCount();
    long updateStart, updateEnd;
    double tachoLeft = 0, tachoRight = 0, centerArcLength = 0, deltaTheta = 0;
    double oldTachoLeft = 0, oldTachoRight = 0;

    while (true) {
      this.getDisplacementAndHeading(dDH);
      dDH[0] -= oldDH[0];
      dDH[1] -= oldDH[1];

      // update the position in a critical region
      synchronized (this) {
        theta += dDH[1];
        theta = fixDegAngle(theta);

        x += dDH[0] * Math.sin(Math.toRadians(theta));
        y += dDH[0] * Math.cos(Math.toRadians(theta));
      }

      oldDH[0] += dDH[0];
      oldDH[1] += dDH[1];
    }
  }

  private void getDisplacementAndHeading(double[] data) {
    int leftTacho, rightTacho;
    leftTacho = Motor.B.getTachoCount();
    rightTacho = Motor.A.getTachoCount();

    data[0] = (leftTacho * robot.getLeftWheelRadius() + rightTacho * robot.getRightWheelRadius())
        * Math.PI / 360.0;
    data[1] = (rightTacho * robot.getRightWheelRadius() - leftTacho * robot.getLeftWheelRadius())
        / robot.getWidth();
  }

  public void getPosition(double[] position, boolean[] update) {
    // ensure that the values don't change while the odometer is running
    synchronized (lock) {
      if (update[0]) {
        position[0] = x;
      }
      if (update[1]) {
        position[1] = y;
      }
      if (update[2]) {
        position[2] = theta;
      }
    }
  }

  public double getX() {
    double result;

    synchronized (lock) {
      result = x;
    }

    return result;
  }

  public double getY() {
    double result;

    synchronized (lock) {
      result = y;
    }

    return result;
  }

  public double getTheta() {
    double result;

    synchronized (lock) {
      result = theta;
    }

    return result;
  }

  public void setPosition(double[] position, boolean[] update) {
    // ensure that the values don't change while the odometer is running
    synchronized (lock) {
      if (update[0]) {
        x = position[0];
      }
      if (update[1]) {
        y = position[1];
      }
      if (update[2]) {
        theta = position[2];
      }
    }
  }

  public void setX(double x) {
    synchronized (lock) {
      this.x = x;
    }
  }

  public void setY(double y) {
    synchronized (lock) {
      this.y = y;
    }
  }

  public void setTheta(double theta) {
    synchronized (lock) {
      this.theta = theta;
    }
  }

  public static double fixDegAngle(double angle) {
    if (angle < 0.0)
      angle = 360.0 + (angle % 360.0);

    return angle % 360.0;
  }

  public Robot getRobot() {
    return robot;
  }
}
