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

    if (thereIsAnObstacle()) {
      // do not give it control
      navigator.stop();
      moveBackAndTurnABit();
      navigator.travelTo(0, 30);

    }

  }

  private void moveBackAndTurnABit() {
    boolean isTurningLeft = leftTouchSensor.isPressed();
    double turningAngle = 45;

    if (!isTurningLeft) {
      turningAngle = -45;
    }

    // back up
    // move motors a brick

    navigator.turnTo(turningAngle);

  }

  private boolean thereIsAnObstacle() {
    return leftTouchSensor.isPressed() || rightTouchSensor.isPressed();
  }
}
