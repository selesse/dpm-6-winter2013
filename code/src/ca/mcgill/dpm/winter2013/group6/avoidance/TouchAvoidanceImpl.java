package ca.mcgill.dpm.winter2013.group6.avoidance;

import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
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
      navigator.getLeftMotor().forward();
      navigator.getRightMotor().forward();
    }
  }

  private void moveBackAndTurnAway() {
    boolean leftTouchSensorHasBeenPressed = leftTouchSensor.isPressed();
    // default direction: turn left
    double turningAngle = -45;

    // if we touch the left touch sensor we should move to the right
    if (leftTouchSensorHasBeenPressed) {
      turningAngle = -turningAngle;
    }

    turningAngle = getBoundaryBasedTurningAngle(turningAngle);
    // i suspect this fixes the bug when its not backing sometimes
    UltrasonicSensor us = new UltrasonicSensor(SensorPort.S1);
    us.continuous();

    navigator.travelStraight(-10);
    navigator.turnTo(turningAngle);
    navigator.travelStraight(18);
  }

  private boolean obstacleDetected() {
    return leftTouchSensor.isPressed() || rightTouchSensor.isPressed();
  }

}