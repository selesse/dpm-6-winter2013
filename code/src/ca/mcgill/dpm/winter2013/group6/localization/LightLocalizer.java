package ca.mcgill.dpm.winter2013.group6.localization;

import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

/**
 * A {@link Localizer} implementation that uses a {@link LightSensor} for
 * performing its localization.
 * 
 * @author Alex Selesse
 * 
 */
public class LightLocalizer extends AbstractLocalizer {
  private LightSensor lightSensor;
  private int sensorAverage = 0;
  private final int THRESHOLD = 55;
  private final double LIGHT_SENSOR_DISTANCE = 11.6;

  /**
   * The constructor the light localizer class.
   * 
   * @param odometer
   *          The {@link Odometer} we'll be reading from.
   * @param navigator
   *          The navigator class that will be used by the localizer.
   * @param lightSensor
   *          The {@link LightSensor} object.
   * @param corner
   *          The corner number the localizer will be localizing. Corners are
   *          numbered from 1 to 4.
   */
  public LightLocalizer(Odometer odometer, Navigator navigator, LightSensor lightSensor, int corner) {
    super(odometer, navigator, corner);
    this.lightSensor = lightSensor;
  }

  @Override
  public void localize() {
    odometer.setPosition(new double[] { 0, 0, 0 }, new boolean[] { true, true, true });
    lightSensor.setFloodlight(true);

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
        try {
          // sleeping to avoid counting the same line twice
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
      newY = -newX;
      newX += 10 * 30.48 - newY;
    }
    else if (corner == 3) {
      newTheta -= 180;
      newX += 10 * 30.48 - newX;
      newY += 10 * 30.48 - newY;
    }
    else {
      newTheta += 90;
      newY += 10 * 30.48 - newX;
      newX = newY;
    }
    odometer.setPosition(new double[] { newX, newY, newTheta }, new boolean[] { true, true, true });
    lightSensor.setFloodlight(false);
  }

  /**
   * calibrates the sensor average using the current conditions
   * 
   * @return The average value for the light sensor over 5 reads.
   */
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

  /**
   * Helper method to detect the black line
   * 
   * @return True if there is a black line detected, else returns a false.
   */
  private boolean blackLineDetected() {
    return (lightSensor.readNormalizedValue() < sensorAverage - THRESHOLD);
  }
}
