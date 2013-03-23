package ca.mcgill.dpm.winter2013.group6.odometer;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.util.Timer;
import lejos.util.TimerListener;

public class OdometerCorrection implements TimerListener {
  private Odometer odometer;
  private LightSensor ls;
  private static int threshold = 50;
  private int sensorAverage = 0;
  private static final double LIGHT_SENSOR_DISTANCE = 11.6;
  private Timer timer;
  private static final int PERIOD = 25;

  public OdometerCorrection(Odometer odometer, LightSensor lightSensor) {
    this.odometer = odometer;
    this.ls = lightSensor;
    this.timer = new Timer(PERIOD, this);

  }

  @Override
  public void timedOut() {
    // if they have the same speed == moving straight, going forward
    if (Motor.A.getRotationSpeed() == Motor.B.getRotationSpeed() && closest90() > 0
        && Motor.A.getRotationSpeed() > 0) {
      if (closestAxis()) {

      }
      else {

      }
    }

  }

  /**
   * This function will determine which axis it is approaching
   * 
   * @return true if x is closer, else y
   */
  public boolean closestAxis() {
    double x = odometer.getX() % 30.48;
    double y = odometer.getY() % 30.48;
    if (x > 30.48 / 2) {
      x -= 30.48;
    }
    if (y > 30.48 / 2) {
      y -= 30.48;
    }
    double theta = odometer.getTheta();
    double speed = Motor.A.getRotationSpeed();
    // 2 * Math.PI * 2.71 * speed / 360 = cm/s
    double xComp = Math.cos(theta) * 2 * Math.PI * 2.71 * speed / 360;
    double yComp = Math.sin(theta) * 2 * Math.PI * 2.71 * speed / 360;
    // cm/cm/s gives us which one will be closer in terms of s, so smaller is
    // closer
    x = x / xComp;
    y = y / yComp;
    if (x > 0 && x < y) {
      return true;
    }
    return false;

  }

  public double getClosestGridLine(boolean axis) {
    double location = axis ? odometer.getX() : odometer.getY();
    int direction = closest90();
    if (axis) {
      switch (direction) {
        case 1:
        case 3:
      }
    }
    location = location % 30.48;
    if (location > 30.48 / 2) {
      location -= 30.48;
    }

  }

  /**
   * 
   * @return the direction the robot is facing, returns a -1 if its not decisive
   */
  public int closest90() {
    double range = 5;
    double theta = odometer.getTheta();
    if (0 + range > theta && 0 - range < theta) {
      return 1;
    }
    else if (90 + range > theta && 90 - range < theta) {
      return 2;
    }
    else if (180 + range > theta && 180 - range < theta) {
      return 3;
    }
    else if (270 + range > theta && 270 - range < theta) {
      return 4;
    }
    else {
      return -1;
    }
  }

  public void start() {
    this.timer.start();

  }

  public void stop() {
    this.timer.stop();

  }

}
