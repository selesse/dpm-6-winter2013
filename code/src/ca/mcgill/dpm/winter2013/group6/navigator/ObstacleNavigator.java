package ca.mcgill.dpm.winter2013.group6.navigator;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

/**
 * {@link Navigator} implementation which takes into consideration obstacles.
 * 
 * @author Alex Selesse
 * 
 */
public class ObstacleNavigator extends NoObstacleNavigator {
  protected UltrasonicSensor uSensor;
  protected TouchSensor leftSensor;
  protected TouchSensor rightSensor;

  public ObstacleNavigator(Odometer odometer, NXTRegulatedMotor leftMotor,
      NXTRegulatedMotor rightMotor, UltrasonicSensor uSensor, TouchSensor leftSensor,
      TouchSensor rightSensor) {
    super(odometer, leftMotor, rightMotor);
    this.uSensor = uSensor;
    this.leftSensor = leftSensor;
    this.rightSensor = rightSensor;
  }

  @Override
  public void travelTo(double x, double y) {
    double turningAngle = getTurningAngle(x, y);
    turnTo(turningAngle);

    // Travel straight.

    // Keep running until we're within an acceptable threshold.
    while (((x - odometer.getX() > THRESHOLD || x - odometer.getX() < -THRESHOLD))
        || ((y - odometer.getY() > THRESHOLD || y - odometer.getY() < -THRESHOLD))) {
      if (pause) {

      }
      else {
        leftMotor.setSpeed(robot.getForwardSpeed());
        rightMotor.setSpeed(robot.getForwardSpeed());
        leftMotor.forward();
        rightMotor.forward();

      }

      ;
    }
    stop();
  }
}
