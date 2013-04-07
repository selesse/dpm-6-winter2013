package ca.mcgill.dpm.winter2013.group6.localization;

import lejos.nxt.LightSensor;
import lejos.nxt.Sound;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.util.Coordinate;

/**
 * A {@link Localizer} implementation that uses a {@link LightSensor} for
 * performing its localization.
 * 
 * @author Arthur Kam
 * 
 */
public class SmartLightLocalizer extends LightLocalizer {
  private int sensorAverage = 0;
  private final int THRESHOLD = 55;
  private final double LIGHT_SENSOR_DISTANCE = 11.8;
  private Coordinate coordinates;
  private int max = 3;
  private int count = 0;

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
  public SmartLightLocalizer(Odometer odometer, Navigator navigator, LightSensor lightSensor,
      Coordinate coordinates) {
    super(odometer, navigator, lightSensor, 1);
    this.coordinates = coordinates;
  }

  @Override
  public void localize() {
    if (3 <= count) {
      Sound.beepSequence();
      return;
    }
    navigator.face(45);
    odometer.setPosition(new double[] { 0, 0, 0 }, new boolean[] { true, true, true });
    lightSensor.getFloodlight();
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
    double[] heading = new double[2];
    odometer.getDisplacementAndHeading(heading);
    double startingAngle = heading[1];
    double currAngle;
    boolean error = false;

    while (lineCounter < 4) {

      if (blackLineDetected()) {
        Sound.beep();
        raw[lineCounter] = odometer.getTheta();
        odometer.getDisplacementAndHeading(heading);
        currAngle = heading[1];
        if (currAngle > startingAngle + 360) {
          Sound.buzz();
          error = true;
          break;
        }
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

    // write code to handle situations where it wouldn't work
    // which means it has rotated for 360 degrees but havent scanned all 4 lines
    // yet
    if (error) {

      // seriously i cant fix it at this stage...
      if (lineCounter == 0) {
        return;
      }
      // not very likely but here is an attempt to fix it
      else if (lineCounter == 1) {
        // move to the line
        raw[0] += 180;
        navigator.turnTo(raw[0]);
        navigator.travelStraight(LIGHT_SENSOR_DISTANCE);
        count++;
        localize();
      }
      // two causes in this case
      // one is one axis is off
      // the other is both axis is off
      else if (lineCounter == 2) {
        // if the lines are opposite of each other
        if (Math.abs(raw[1] - raw[0]) > 175) {
          navigator.face(raw[0]);
          navigator.travelStraight(10);
        }
        else {
          double angleMid = Odometer.fixDegAngle(180 - (raw[1] - raw[0]));
          navigator.travelStraight(10);

        }
        count++;
        localize();

      }
      else if (lineCounter == 3) {
        return;
      }

      return;
    }
    // formula modified from the tutorial slides
    double thetaX = (raw[3] - raw[1]) / 2;
    double thetaY = (raw[2] - raw[0]) / 2;
    double newX = -LIGHT_SENSOR_DISTANCE * Math.cos(Math.toRadians(thetaY));
    double newY = -LIGHT_SENSOR_DISTANCE * Math.cos(Math.toRadians(thetaX));
    double newTheta = 180 + thetaX - raw[3];
    newTheta += odometer.getTheta();

    odometer.setPosition(new double[] {
        newX + coordinates.getX(),
        newY + coordinates.getY(),
        newTheta }, new boolean[] { true, true, true });
    count = 0;
    Sound.beep();
    navigator.face(0);
    navigator.travelStraight(coordinates.getY() - odometer.getY());

    Sound.beep();

    return;
  }

  @Override
  public void run() {
    localize();
  }
}
