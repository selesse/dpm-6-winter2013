package ca.mcgill.dpm.winter2013.group6.odometer;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.Sound;

public class OdometerCorrection implements Runnable {
  private Odometer odometer;
  private LightSensor lightSensor;
  private static final int THRESHOLD = 50;
  private int sensorAverage = 0;
  private static final double LIGHT_SENSOR_DISTANCE = 11.6;
  private boolean isRunning;
  private static final int PERIOD = 25;

  public OdometerCorrection(Odometer odometer, LightSensor lightSensor) {
    this.odometer = odometer;
    this.lightSensor = lightSensor;

  }

  @Override
  public void run() {
    isRunning = true;
    while (isRunning) {
      // if they have the same speed == moving straight, going forward
      if (blackLineDetected() && Motor.A.getRotationSpeed() == Motor.B.getRotationSpeed()
          && getQuadrantRobotIsFacing() > 0 && Motor.A.getRotationSpeed() > 0) {
        Sound.beep();
        boolean closerToXAxis = (isCloserToXAxis());
        double closestGridLine = getClosestGridLine(closerToXAxis);
        if (closerToXAxis) {
          odometer.setX(closestGridLine);
        }
        else {
          odometer.setY(closestGridLine);
        }
      }
    }
  }

  /**
   * This function will determine which axis it is approaching. Takes into
   * consideration of the current heading of the robot
   * 
   * @return true if x is closer, else y
   */
  public boolean isCloserToXAxis() {
    double x = odometer.getX() % 30.48;
    double y = odometer.getY() % 30.48;
    if (x > 30.48 / 2) {
      x -= 30.48;
    }
    if (y > 30.48 / 2) {
      y -= 30.48;
    }
    double theta = Math.toRadians(odometer.getTheta());
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

  /**
   * The function gets the closest gridline in terms of displacement from the
   * robot
   * 
   * @param axis
   *          The axis you would like to get
   * @return The closest grid line using the given axis
   */
  public double getClosestGridLine(boolean axis) {
    double location = axis ? odometer.getX() : odometer.getY();
    double degree = Math.toRadians(odometer.getTheta());
    // x-axis
    if (axis) {
      location = location - LIGHT_SENSOR_DISTANCE * Math.cos(degree);
    }
    else {
      location = location - LIGHT_SENSOR_DISTANCE * Math.sin(degree);
    }
    int mult = ((int) (location / 30.48));
    if (Math.abs(mult * 30.48 - location) > Math.abs((mult + 1) * 30.48 - location)) {
      return mult * 30.48 + 30.48;
    }
    return mult * 30.48;
  }

  /**
   * 
   * @return the direction the robot is facing, returns a -1 if its not decisive
   */
  public int getQuadrantRobotIsFacing() {
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
    this.isRunning = true;
  }

  public void stop() {
    this.isRunning = false;
  }

  private boolean blackLineDetected() {
    return (lightSensor.readNormalizedValue() < sensorAverage - THRESHOLD);
  }

}
