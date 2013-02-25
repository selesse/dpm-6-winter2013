package ca.mcgill.dpm.winter2013.group6.odometer;

import ca.mcgill.dpm.winter2013.group6.util.Robot;

public class Odometer implements Runnable {
  public static final int DEFAULT_PERIOD = 25;
  private Object lock;
  private double x, y, theta;
  private double[] oldDH, dDH;
  private Robot robot;

  public Odometer(Robot robot, int period, boolean start) {
    // initialise variables
    this.robot = robot;
    x = 0.0;
    y = 0.0;
    theta = 0.0;
    oldDH = new double[2];
    dDH = new double[2];
    lock = new Object();

    // start the odometer immediately, if necessary
    if (start) {
      run();
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

  // accessors
  public void getPosition(double[] pos) {
    synchronized (lock) {
      pos[0] = x;
      pos[1] = y;
      pos[2] = theta;
    }
  }

  public double getX() {
    synchronized (lock) {
      return x;
    }
  }

  public double getY() {
    synchronized (lock) {
      return y;
    }
  }

  public double getTheta() {
    synchronized (lock) {
      return theta;
    }
  }

  public Robot getTwoWheeledRobot() {
    return robot;
  }

  // mutators
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

  // static 'helper' methods
  public static double fixDegAngle(double angle) {
    if (angle < 0.0) {
      angle = 360.0 + (angle % 360.0);
    }

    return angle % 360.0;
  }

  public static double minimumAngleFromTo(double a, double b) {
    double d = fixDegAngle(b - a);

    if (d < 180.0) {
      return d;
    }
    else {
      return d - 360.0;
    }
  }
}
