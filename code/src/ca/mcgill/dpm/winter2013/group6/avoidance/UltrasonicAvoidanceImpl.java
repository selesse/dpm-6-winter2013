package ca.mcgill.dpm.winter2013.group6.avoidance;

import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

/**
 * Ultrasonic obstacle avoider. Our current hardware version of the robot has an
 * ultrasonic sensor that will allow it to detect obstacles in front of it.
 * 
 * @author Alex Selesse
 * 
 */
public class UltrasonicAvoidanceImpl extends AbstractObstacleAvoider {
  protected UltrasonicSensor ultrasonicSensor;
  private final int THRESHOLD_DISTANCE = 20;

  public UltrasonicAvoidanceImpl(Odometer odometer, Navigator navigator,
      UltrasonicSensor ultrasonicSensor) {
    super(odometer, navigator);
    this.ultrasonicSensor = ultrasonicSensor;
  }

  @Override
  public void avoidObstacles() {
    if (thereIsAnObstacleInFrontOfUs() && !isAvoiding) {
      this.isAvoiding = true;
      navigator.stop();
      avoidTheObstacle();
      this.isAvoiding = false;
    }
  }

  private void avoidTheObstacle() {
    if (navigator.getCoordinateHeading() == null) {
      return;
    }
    boolean closerToLeft = odometer.getX() > navigator.getCoordinateHeading().getX();

    // turn left
    double turningAngle = 70;
    if (!closerToLeft) {
      turningAngle = -70;
    }
    navigator.turnTo(turningAngle);
    navigator.travelStraight(45);
  }

  private boolean thereIsAnObstacleInFrontOfUs() {
    return (ultrasonicSensor.getDistance() - THRESHOLD_DISTANCE) < 0;
  }

}
