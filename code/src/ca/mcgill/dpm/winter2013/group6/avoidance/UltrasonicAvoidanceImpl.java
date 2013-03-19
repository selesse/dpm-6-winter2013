package ca.mcgill.dpm.winter2013.group6.avoidance;

import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class UltrasonicAvoidanceImpl extends AbstractObstacleAvoider {
  protected UltrasonicSensor ultrasonicSensor;
  private final int THRESHOLD_DISTANCE = 40;

  public UltrasonicAvoidanceImpl(Odometer odometer, Navigator navigator,
      UltrasonicSensor ultrasonicSensor) {
    super(odometer, navigator);
    this.ultrasonicSensor = ultrasonicSensor;
  }

  @Override
  public void avoidObstacles() {

    if (thereIsAnObstacleInFrontOfUs() && !currentlyAvoiding) {
      setAvoiding(true);
      navigator.stop();
      avoidTheObstacle();
      setAvoiding(false);
    }

  }

  private void avoidTheObstacle() {
    // TODO: figure out how to actually avoid an obstacle intelligently
  }

  private boolean thereIsAnObstacleInFrontOfUs() {
    return (ultrasonicSensor.getDistance() - THRESHOLD_DISTANCE) < 0;
  }

}
