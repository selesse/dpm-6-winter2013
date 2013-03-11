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

  // default constructor
  public Odometer(Robot robot) {
    x = 0.0;
    y = 0.0;
    theta = 0.0;
    this.robot = robot;
    lock = new Object();
  }

  @Override
  public void run() {
    long updateStart, updateEnd;
    double tachoLeft = 0, tachoRight = 0, centerArcLength = 0, deltaTheta = 0;
    double oldTachoLeft = 0, oldTachoRight = 0;

    while (true) {
      updateStart = System.currentTimeMillis();

      tachoLeft = ((Motor.A.getTachoCount() * Math.PI) / 180.0) - oldTachoLeft;
      tachoRight = ((Motor.B.getTachoCount() * Math.PI) / 180.0) - oldTachoRight;

      oldTachoLeft = ((Motor.A.getTachoCount() * Math.PI) / 180.0);
      oldTachoRight = ((Motor.B.getTachoCount() * Math.PI) / 180.0);

      // The arc length traveled by the center of the robot.
      centerArcLength = ((tachoRight * robot.getRightWheelRadius()) + (tachoLeft * robot
          .getLeftWheelRadius())) / 2.0;

      deltaTheta = ((tachoLeft * robot.getLeftWheelRadius()) - (tachoRight * robot
          .getRightWheelRadius())) / robot.getWidth();

      synchronized (lock) {
        this.x = this.x + (centerArcLength * Math.cos(this.theta + (deltaTheta / 2.0)));
        this.y = this.y + (centerArcLength * Math.sin(this.theta + (deltaTheta / 2.0)));
        this.theta = theta + deltaTheta;
      }

      // this ensures that the odometer only runs once every period
      updateEnd = System.currentTimeMillis();
      if (updateEnd - updateStart < ODOMETER_PERIOD) {
        try {
          Thread.sleep(ODOMETER_PERIOD - (updateEnd - updateStart));
        }
        catch (InterruptedException e) {
        }
      }
    }
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

  public Robot getRobot() {
    return robot;
  }
}
