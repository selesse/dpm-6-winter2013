package ca.mcgill.dpm.winter2013.group6.tests.odometry;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.navigator.ObstacleNavigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class OdometryTest112 extends ObstacleNavigator {

  public OdometryTest112(Odometer odometer, NXTRegulatedMotor leftMotor,
      NXTRegulatedMotor rightMotor, UltrasonicSensor ultrasonicSensor, TouchSensor leftTouchSensor,
      TouchSensor rightTouchSensor) {
    super(odometer, leftMotor, rightMotor, ultrasonicSensor, leftTouchSensor, rightTouchSensor);
  }

  @Override
  public void run() {
    travelTo(0, 30);

    try {
      Thread.sleep(1000);
    }
    catch (InterruptedException e) {
    }
    travelTo(0, 60);
  }

}
