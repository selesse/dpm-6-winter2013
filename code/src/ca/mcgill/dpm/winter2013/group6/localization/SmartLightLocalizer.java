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
  private final double LIGHT_SENSOR_DISTANCE = 11.8;
  private Coordinate coordinate;
  private int count = 0;

  /**
   * The constructor the smart light localizer class.
   * 
   * @param odometer
   *          The {@link Odometer} we'll be reading from.
   * @param navigator
   *          The navigator class that will be used by the localizer.
   * @param lightSensor
   *          The {@link LightSensor} object.
   * @param coordinate
   *          The coordinate that the localizer will think it is localizing in
   *          reference to (must be an intersection).
   */
  public SmartLightLocalizer(Odometer odometer, Navigator navigator, LightSensor lightSensor,
      Coordinate coordinate) {
    super(odometer, navigator, lightSensor, 1);
    this.coordinate = coordinate;
  }

  public SmartLightLocalizer(Odometer odometer, Navigator navigator, LightSensor lightSensor) {
    super(odometer, navigator, lightSensor, 1);
    this.coordinate = new Coordinate(0, 0);
  }

  public void setCoordinate(Coordinate coordinate) {
    if (coordinate != null) {
      this.coordinate = coordinate;
    }
  }

  @Override
  public void localize() {
    if (3 <= count) {
      Sound.beepSequence();
      count = 0;
      return;
    }
    navigator.face(45);
    odometer.setPosition(new double[] { 0, 0, 0 }, new boolean[] { true, true, true });
    lightSensor.setFloodlight(true);
    int lineCounter = 0;

    double[] rawDetectedLineValues = new double[4];

    // Filter the light sensor
    try {
      Thread.sleep(300);
    }
    catch (InterruptedException e) {
    }
    // Rotate and clock the 4 grid lines
    calibrateSensorAverage();

    navigator.setMotorRotateSpeed(-robot.getRotateSpeed() - 150);

    // Detect the four lines
    double[] heading = new double[2];
    odometer.getDisplacementAndHeading(heading);
    double startingAngle = heading[1];
    double currAngle = startingAngle;
    boolean error = false;

    while (lineCounter < 4) {

      if (blackLineDetected()) {
        Sound.beep();
        rawDetectedLineValues[lineCounter] = odometer.getTheta();
        odometer.getDisplacementAndHeading(heading);
        currAngle += heading[1];
        // its negative since we are rotating the negative direction
        if (currAngle + 360 < startingAngle) {
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

    if (error) {
      // if we haven't found any lines, abandon ship
      if (lineCounter == 0) {
        navigator.face(45);
        navigator.travelStraight(15);
        count++;
        localize();
        return;
      }
      // not very likely
      else if (lineCounter == 1) {
        // move to the line
        rawDetectedLineValues[0] += 180;
        navigator.turnTo(rawDetectedLineValues[0]);
        navigator.travelStraight(LIGHT_SENSOR_DISTANCE);
        count++;
        // calling it again here
        localize();
      }
      // two causes in this case: 1. one axis is off, 2. both axes are off
      else if (lineCounter == 2) {
        // if the lines are opposite of each other
        if (Math.abs(rawDetectedLineValues[1] - rawDetectedLineValues[0]) > 175) {
          navigator.face(rawDetectedLineValues[0]);
          navigator.travelStraight(10);
        }
        else {
          navigator.face(rawDetectedLineValues[1] - rawDetectedLineValues[0] + 180 + 45);
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
    // happens if it is displaced too far right from the y axis
    if (rawDetectedLineValues[0] > 60) {
      double swap = rawDetectedLineValues[1];
      double swap2 = rawDetectedLineValues[2];
      rawDetectedLineValues[1] = rawDetectedLineValues[0];
      rawDetectedLineValues[2] = swap;
      swap = rawDetectedLineValues[3];
      rawDetectedLineValues[3] = swap2;
      rawDetectedLineValues[0] = swap;

    }
    // formula modified from the tutorial slides
    double thetaX = (rawDetectedLineValues[3] - rawDetectedLineValues[1]) / 2;
    double thetaY = (rawDetectedLineValues[2] - rawDetectedLineValues[0]) / 2;
    double newX = -LIGHT_SENSOR_DISTANCE * Math.cos(Math.toRadians(thetaY));
    double newY = -LIGHT_SENSOR_DISTANCE * Math.cos(Math.toRadians(thetaX));
    double newTheta = 180 + thetaX - rawDetectedLineValues[3];
    newTheta += odometer.getTheta();

    odometer.setPosition(new double[] {
        newX + coordinate.getX(),
        newY + coordinate.getY(),
        newTheta }, new boolean[] { true, true, true });
    count = 0;

    // travel to the coordinate
    navigator.face(0);
    navigator.travelStraight(-newY);
    navigator.face(90);
    navigator.travelStraight(-newX);

    Sound.twoBeeps();

    return;
  }

  @Override
  public void run() {
    localize();
  }
}
