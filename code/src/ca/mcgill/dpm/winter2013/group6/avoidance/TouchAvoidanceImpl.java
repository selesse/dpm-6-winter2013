package ca.mcgill.dpm.winter2013.group6.avoidance;

import lejos.nxt.TouchSensor;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class TouchAvoidanceImpl extends AbstractObstacleAvoider {

  protected TouchSensor leftTouchSensor;
  protected TouchSensor rightTouchSensor;

  public TouchAvoidanceImpl(Odometer odometer, Navigator navigator, TouchSensor leftTouchSensor,
      TouchSensor rightTouchSensor) {
    super(odometer, navigator);
    this.leftTouchSensor = leftTouchSensor;
    this.rightTouchSensor = rightTouchSensor;
  }

  @Override
  public void avoidObstacles() {
    // TODO Auto-generated method stub

  }

}
