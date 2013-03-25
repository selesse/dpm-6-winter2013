package ca.mcgill.dpm.winter2013.group6.localization;

import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class LightLocalizer extends AbstractLocalizer {
  private LightSensor ls;
  private static int threshold = 55;
  private int sensorAverage = 0;
  private static final double LIGHT_SENSOR_DISTANCE = 11.6;
  public static int ROTATION_SPEED = 60;
  public static int FORWARD_SPEED = 60;

  public LightLocalizer(Odometer odometer, Navigator navigator, LightSensor lightSensor, int corner) {
    super(odometer, navigator, corner);
    this.ls = lightSensor;
  }

  @Override
  public void localize() {
    odometer.setPosition(new double[] { 0, 0, 0 }, new boolean[] { true, true, true });
    ls.setFloodlight(true);

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

    navigator.setMotorRotateSpeed(-robot.getRotateSpeed());

    // Detect the four lines
    while (lineCounter < 4) {

      if (blackLineDetected()) {
        Sound.beep();
        raw[lineCounter] = odometer.getTheta();
        lineCounter++;
        try {// sleeping to avoid counting the same line twice
          Thread.sleep(150);
        }
        catch (InterruptedException e) {
        }
      }
    }
    // Stop the robot
    navigator.stop();

    // formula modified from the tutorial slides

    double thetaX = (raw[3] - raw[1]) / 2;
    double thetaY = (raw[2] - raw[0]) / 2;
    double newX = -LIGHT_SENSOR_DISTANCE * Math.cos(Math.toRadians(thetaY));
    double newY = -LIGHT_SENSOR_DISTANCE * Math.cos(Math.toRadians(thetaX));
    double newTheta = 180 + thetaX - raw[3];
    newTheta += odometer.getTheta();

    if (corner == 1) {
      // nothing needs to be done
    }
    else if (corner == 2) {
      newTheta -= 90;
      newX += 10 * 30.48;
    }
    else if (corner == 3) {
      newTheta -= 180;
      newX += 10 * 30.48;
      newY += 10 * 30.48;
    }
    else {
      newTheta += 90;
      newY += 10 * 30.48;
    }
    odometer.setPosition(new double[] { newX, newY, newTheta }, new boolean[] { true, true, true });
    ls.setFloodlight(false);

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

  @Override
  public void run() {
    // TODO Auto-generated method stub

  }

}
