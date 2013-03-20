package ca.mcgill.dpm.winter2013.group6.navigator;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.avoidance.ObstacleAvoider;
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
  protected ObstacleAvoider[] avoiderList;

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
      if (getAvoiding() != null) {
        ObstacleAvoider dummby = getAvoiding();
        stop();
        while (dummby.getAvoiding()) {
          try {
            Thread.sleep(100);
          }
          catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
          }
        }
        Sound.buzz();
        turningAngle = getTurningAngle(x, y);
        turnTo(turningAngle);
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

  public void setAvoid(ObstacleAvoider[] avoiderList) {
    this.avoiderList = avoiderList;
  }

  public ObstacleAvoider getAvoiding() {
    if (avoiderList == null) {
      return null;
    }
    for (int i = 0; i < avoiderList.length; i++) {
      if (avoiderList[i].getAvoiding() == true) {
        return avoiderList[i];
      }
    }
    return null;
  }
}
