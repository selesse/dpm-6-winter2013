package ca.mcgill.dpm.winter2013.group6.tests.navigator;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.navigator.ObstacleNavigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.util.Coordinate;

public class NavigatorTest121 extends ObstacleNavigator {

  public NavigatorTest121(Odometer odometer, NXTRegulatedMotor leftMotor,
      NXTRegulatedMotor rightMotor, UltrasonicSensor ultrasonicSensor, TouchSensor leftTouchSensor,
      TouchSensor rightTouchSensor) {
    super(odometer, leftMotor, rightMotor, ultrasonicSensor, leftTouchSensor, rightTouchSensor);
    this.waypoints = new Coordinate[] {
        new Coordinate(0, 30),
        new Coordinate(30, 15),
        new Coordinate(30, 30),
        new Coordinate(0, 15),
        new Coordinate(0, 0) };
  }

}
