package ca.mcgill.dpm.winter2013.group6.odometer;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.nxt.Sound;

public class OdometerCorrection implements Runnable {
  private Odometer odometer;
  private LightSensor lightSensor;
  private static final int THRESHOLD = 55;
  private int sensorAverage = 0;
  private static final double LIGHT_SENSOR_DISTANCE = 11.8;
  private boolean isRunning;

  public OdometerCorrection(Odometer odometer, LightSensor lightSensor) {
    this.odometer = odometer;
    this.lightSensor = lightSensor;

  }

  @Override
  public void run() {
    isRunning = true;
    calibrateSensorAverage();
    lightSensor.setFloodlight(true);
    Sound.twoBeeps();
    while (isRunning) {
      // if they have the same speed == moving straight, going forward
      if (blackLineDetected()) {

        boolean axisToAdjust = !(isCloserToXAxis());
        // this part will get the closest gridline location for the opposite
        // axis
        double closestGridLine = getClosestGridLine(axisToAdjust);
        // if x axis that was detected, you adjust the y coordinate
        if (axisToAdjust) {
          odometer.setX(closestGridLine);
          Sound.buzz();
        }
        else {
          odometer.setY(closestGridLine);
          Sound.beep();
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
    double degree = odometer.getTheta();
    // determines the extact location in a tile
    double x = (odometer.getX() - LIGHT_SENSOR_DISTANCE * Math.sin(degree)) % 30.48;
    double y = (odometer.getY() - LIGHT_SENSOR_DISTANCE * Math.cos(degree)) % 30.48;
    if (x > 30.48 / 2) {
      x -= 30.48;
    }
    if (y > 30.48 / 2) {
      y -= 30.48;
    }
    double theta = Math.toRadians(odometer.getTheta());
    double speed = Motor.A.getRotationSpeed();
    // 2 * Math.PI * 2.71 * speed / 360 = cm/s
    double xComp = Math.sin(theta) * 2 * Math.PI * 2.71 * speed / 360;
    double yComp = Math.cos(theta) * 2 * Math.PI * 2.71 * speed / 360;
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
   *          The axis you would like to get the gridline location for. If
   * @return The closest grid line location using the given axis
   */
  public double getClosestGridLine(boolean axis) {
    double location = axis ? odometer.getX() : odometer.getY();
    double degree = Math.toRadians(odometer.getTheta());
    // if x-axis, get location of the y-axis grid line
    if (axis) {
      location = location - LIGHT_SENSOR_DISTANCE * Math.sin(degree);
    }
    else {
      location = location - LIGHT_SENSOR_DISTANCE * Math.cos(degree);
    }
    //
    int mult = ((int) (location / 30.48));
    if (Math.abs(mult * 30.48 - location) > Math.abs((mult + 1) * 30.48 - location)) {
      return mult * 30.48 + 30.48;
    }
    return mult * 30.48;
  }

  /**
   * 
   * @return A number bigger then zero if it is okay to fix odometry
   */

  public int isOkayToFix() {
    double range = 20;
    double ignoreRange = 5;
    double theta = odometer.getTheta();
    // check if its + or - within -5 to 5, 85-95 etc to discard since its more
    // likely to be wrong
    if (theta % 90 < ignoreRange || theta % 5 > 90 - ignoreRange) {
      return -1;
    }
    // return a positive number if its within -20 to 20
    else if (theta % 90 > range || theta % 5 < 90 - range) {
      return ((int) (theta / 90)) % 4 + 1;
    }
    return -1;

  }

  public void start() {
    this.isRunning = true;
  }

  public void stop() {
    this.isRunning = false;
  }

  private int calibrateSensorAverage() {
    int senValue = 0;
    // collects the average of a 5 samples
    for (int i = 0; i < 5; i++) {
      senValue += lightSensor.readNormalizedValue();
    }
    senValue = senValue / 5;
    this.sensorAverage = senValue;
    return senValue;
  }

  private boolean blackLineDetected() {
    return (lightSensor.readNormalizedValue() < sensorAverage - THRESHOLD);
  }

}
