package ca.mcgill.dpm.winter2013.group6.avoidance;

import lejos.nxt.TouchSensor;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.util.PlayingField;

/**
 * An obstacle avoider that has two touch sensors. When either of the sensor is
 * pressed, it will back up and turn away (depending on which sensor is
 * touched).
 * 
 * @author Alex Selesse
 * 
 */
public class TouchAvoidanceImpl extends AbstractObstacleAvoider {
  protected TouchSensor leftTouchSensor;
  protected TouchSensor rightTouchSensor;

  public TouchAvoidanceImpl(Odometer odometer, Navigator navigator, PlayingField playingField,
      TouchSensor leftTouchSensor, TouchSensor rightTouchSensor) {
    super(odometer, navigator, playingField);
    this.leftTouchSensor = leftTouchSensor;
    this.rightTouchSensor = rightTouchSensor;
  }

  @Override
  public void avoidObstacles() {
    if (obstacleDetected() && !isCurrentlyAvoiding) {
      this.isCurrentlyAvoiding = true;
      moveBackAndTurnAway();
      this.isCurrentlyAvoiding = false;
    }
  }

  private void moveBackAndTurnAway() {
    boolean leftTouchSensorHasBeenPressed = leftTouchSensor.isPressed();
    // default direction: turn left
    double turningAngle = -45;

    // if we touch the left touch sensor we should move away to the right
    if (leftTouchSensorHasBeenPressed) {
      turningAngle = -turningAngle;
    }

    navigator.travelStraight(-10);
    navigator.turnTo(turningAngle);
    navigator.travelStraight(18);
  }

  private boolean obstacleDetected() {
    return leftTouchSensor.isPressed() || rightTouchSensor.isPressed();
  }
}
