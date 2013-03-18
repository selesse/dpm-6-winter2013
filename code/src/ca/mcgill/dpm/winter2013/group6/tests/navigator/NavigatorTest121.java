package ca.mcgill.dpm.winter2013.group6.tests.navigator;

import lejos.nxt.NXTRegulatedMotor;
import ca.mcgill.dpm.winter2013.group6.navigator.NoObstacleNavigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.util.Coordinate;

public class NavigatorTest121 extends NoObstacleNavigator {

  public NavigatorTest121(Odometer odometer, NXTRegulatedMotor leftMotor,
      NXTRegulatedMotor rightMotor) {
    super(odometer, leftMotor, rightMotor);
    this.waypoints = new Coordinate[] {
        new Coordinate(0, 30),
        new Coordinate(30, 15),
        new Coordinate(30, 30),
        new Coordinate(0, 15),
        new Coordinate(0, 0) };
  }
}
