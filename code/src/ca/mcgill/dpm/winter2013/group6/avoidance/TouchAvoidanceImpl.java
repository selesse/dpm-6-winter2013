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
    if (obstacleDetected() && !currentlyAvoiding) {
      setAvoiding(true);
      navigator.stop();
      moveBackAndTurnAway();
      setAvoiding(false);
    }
  }

  private void moveBackAndTurnAway() {
    boolean isTurningLeft = leftTouchSensor.isPressed();
    double turningAngle = 45;

    if (!isTurningLeft) {
      turningAngle = -turningAngle;
    }

    navigator.travelStraight(-10);
    navigator.turnTo(turningAngle);
    navigator.travelStraight(10);
  }

  private boolean obstacleDetected() {
    return leftTouchSensor.isPressed() || rightTouchSensor.isPressed();
  }
}
