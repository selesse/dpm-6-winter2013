package ca.mcgill.dpm.winter2013.group6.tests.odometry;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import ca.mcgill.dpm.winter2013.group6.navigator.NoObstacleNavigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class OdometryTest111 extends NoObstacleNavigator {

  public OdometryTest111(Odometer odometer, NXTRegulatedMotor leftMotor,
      NXTRegulatedMotor rightMotor) {
    super(odometer, leftMotor, rightMotor);
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
