package ca.mcgill.dpm.winter2013.group6.odometer;

import lejos.util.Timer;
import lejos.util.TimerListener;
import ca.mcgill.dpm.winter2013.group6.util.Robot;

/**
 * An odometer thread implementation, taken from the provided lab 3 code.
 * 
 * @author Alex Selesse
 * 
 */
public class Odometer implements TimerListener {
  private final static int DEFAULT_PERIOD = 25;
  private Object lock;
  private double x, y, theta;
  private double[] oldDH, dDH;
  private Robot robot;
  Timer odometerTimer;

  public Odometer(Robot robot, int period, boolean start) {
    // initialise variables
    this.robot = robot;
    x = 0.0;
    y = 0.0;
    theta = 0.0;
    oldDH = new double[2];
    dDH = new double[2];
    lock = new Object();
    this.odometerTimer = new Timer(period, this);

    // start the odometer immediately, if necessary
    if (start) {
      odometerTimer.start();
    }
  }

  public Odometer(Robot robot) {
    this(robot, DEFAULT_PERIOD, false);
  }

  public Odometer(Robot robot, boolean start) {
    this(robot, DEFAULT_PERIOD, start);
  }

  public Odometer(Robot robot, int period) {
    this(robot, period, false);
  }

  public void run() {
    odometerTimer.start();
  }

  @Override
  public void timedOut() {
    robot.getDisplacementAndHeading(dDH);
    dDH[0] -= oldDH[0];
    dDH[1] -= oldDH[1];

    // update the position in a critical region
    synchronized (lock) {
      theta += dDH[1];
      theta = fixDegAngle(theta);

      x += dDH[0] * Math.sin(Math.toRadians(theta));
      y += dDH[0] * Math.cos(Math.toRadians(theta));
    }

    oldDH[0] += dDH[0];
    oldDH[1] += dDH[1];
  }

  /**
   * Get the position by setting the (x, y, theta) values (respectively) into
   * the pos array. Warning: modifies parameter.
   * 
   * @param pos
   *          Array of length 3.
   */
  public void getPosition(double[] pos) {
    synchronized (lock) {
      pos[0] = x;
      pos[1] = y;
      pos[2] = theta;
    }
  }

  /**
   * Returns the x value of the odometer.
   * 
   * @return X value of odometer
   */
  public double getX() {
    synchronized (lock) {
      return x;
    }
  }

  /**
   * Returns the y value of the odometer.
   * 
   * @return Y value of odometer
   */
  public double getY() {
    synchronized (lock) {
      return y;
    }
  }

  /**
   * Gets the theta value of the odometer.
   * 
   * @return Theta
   */
  public double getTheta() {
    synchronized (lock) {
      return theta;
    }
  }

  /**
   * Returns an instance of the robot.
   * 
   * @return The {@link Robot} we all know and love.
   */
  public Robot getRobot() {
    return robot;
  }

  /**
   * Set the value of the position. For example, pos = [x, y, theta],
   * update[true, false, false] will only modify the odometer's x value.
   * 
   * @param pos
   *          Length 3 array corresponding to desired position (if boolean at
   *          that index is set)
   * @param update
   *          Length 3 array corresponding to desired modification of position
   */
  public void setPosition(double[] pos, boolean[] update) {
    synchronized (lock) {
      if (update[0]) {
        x = pos[0];
      }
      if (update[1]) {
        y = pos[1];
      }
      if (update[2]) {
        theta = pos[2];
      }
    }
  }

  public static double fixDegAngle(double angle) {
    if (angle < 0.0) {
      angle = 360.0 + (angle % 360.0);
    }

    return angle % 360.0;
  }

}
