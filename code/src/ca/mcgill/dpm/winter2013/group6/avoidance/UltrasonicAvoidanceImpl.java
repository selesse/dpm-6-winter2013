package ca.mcgill.dpm.winter2013.group6.avoidance;

import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class UltrasonicAvoidanceImpl extends AbstractObstacleAvoider {

  protected UltrasonicSensor ultrasonicSensor;

  public UltrasonicAvoidanceImpl(Odometer odometer, Navigator navigator,
      UltrasonicSensor ultrasonicSensor) {
    super(odometer, navigator);
    this.ultrasonicSensor = ultrasonicSensor;
  }

  @Override
  public void avoidObstacles() {
    // TODO Auto-generated method stub

  }

}
