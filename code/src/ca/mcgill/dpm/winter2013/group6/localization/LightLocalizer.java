package ca.mcgill.dpm.winter2013.group6.localization;

import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.util.Robot;

public class LightLocalizer implements Localizer {
  private Odometer odo;
  private LightSensor ls;
  private static int threshold = 50;
  private int sensorAverage = 0;
  private static final double LIGHT_SENSOR_DISTANCE = 12.5;
  private Navigator nav;
  public static int ROTATION_SPEED = 60;
  public static int FORWARD_SPEED = 60;
  private Robot robot;

  public LightLocalizer(Odometer odo, LightSensor ls, Navigator nav) {
    this.odo = odo;
    this.ls = ls;
    this.nav = nav;
    this.robot = odo.getRobot();

  }

  @Override
  public void doLocalize() {

    int lineCounter = 0;

    double[] raw = new double[4];

    // Filter the light sensor
    try {
      Thread.sleep(1500);
    }
    catch (InterruptedException e) {
    }
    // Rotate and clock the 4 grid lines
    calibrateSensorAverage();

    nav.rotate(robot.getRotateSpeed());

    // Detect the four lines
    while (lineCounter < 4) {

      if (blackLineDetected()) {
        Sound.beep();
        raw[lineCounter] = odo.getTheta();
        lineCounter++;
        try {// sleeping to avoid counting the same line twice
          Thread.sleep(150);
        }
        catch (InterruptedException e) {
        }
      }
    }
    // Stop the robot
    nav.stop();

    // formula modified from the tutorial slides

    double thetaX = (raw[3] - raw[1]) / 2;
    double thetaY = (raw[2] - raw[0]) / 2;
    double newX = -LIGHT_SENSOR_DISTANCE * Math.cos(Math.toRadians(thetaY));
    double newY = -LIGHT_SENSOR_DISTANCE * Math.cos(Math.toRadians(thetaX));
    double newTheta = 180 + thetaX - raw[3];
    newTheta += odo.getTheta();
    newTheta = Odometer.fixDegAngle(newTheta);

    odo.setPosition(new double[] { newX, newY, newTheta }, new boolean[] { true, true, true });

  }

  // calibrates the sensor average using the current conditions
  private int calibrateSensorAverage() {
    int senValue = 0;
    // collects the average of a 5 samples
    for (int i = 0; i < 5; i++) {
      senValue += ls.readNormalizedValue();
    }
    senValue = senValue / 5;
    this.sensorAverage = senValue;
    return senValue;
  }

  // Helper method to detect the black line
  private boolean blackLineDetected() {
    return (ls.readNormalizedValue() < sensorAverage - threshold);
  }

}
