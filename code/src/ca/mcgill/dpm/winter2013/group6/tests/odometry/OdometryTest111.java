package ca.mcgill.dpm.winter2013.group6.tests.odometry;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.navigator.ObstacleNavigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class OdometryTest111 extends ObstacleNavigator {

  public OdometryTest111(Odometer odometer, NXTRegulatedMotor leftMotor,
      NXTRegulatedMotor rightMotor, UltrasonicSensor ultrasonicSensor, TouchSensor leftTouchSensor,
      TouchSensor rightTouchSensor) {
    super(odometer, leftMotor, rightMotor, ultrasonicSensor, leftTouchSensor, rightTouchSensor);
  }

  @Override
  public void run() {
    Sound.beep();

    turnTo(90);
    try {
      Thread.sleep(1000);
    }
    catch (InterruptedException e) {
    }

    turnTo(180);
    try {
      Thread.sleep(1000);
    }
    catch (InterruptedException e) {

    }

    turnTo(270);
    try {
      Thread.sleep(1000);
    }
    catch (InterruptedException e) {

    }
    turnTo(0);
  }

}
