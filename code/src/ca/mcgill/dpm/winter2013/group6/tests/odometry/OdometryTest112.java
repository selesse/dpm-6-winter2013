package ca.mcgill.dpm.winter2013.group6.tests.odometry;

import lejos.nxt.NXTRegulatedMotor;
import ca.mcgill.dpm.winter2013.group6.navigator.NoObstacleNavigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class OdometryTest112 extends NoObstacleNavigator {

  public OdometryTest112(Odometer odometer, NXTRegulatedMotor leftMotor,
      NXTRegulatedMotor rightMotor) {
    super(odometer, leftMotor, rightMotor);
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
