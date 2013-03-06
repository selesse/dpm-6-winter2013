package ca.mcgill.dpm.winter2013.group6.localization;

import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.util.Robot;

public class USLocalizer extends AbstractLocalizer {
  private Odometer odometer;
  private Robot robot;
  private UltrasonicSensor us;
  private Navigator nav;

  public USLocalizer(Odometer odo, UltrasonicSensor usSensor, Navigator nav) {
    this.odometer = odo;
    this.robot = odo.getRobot();
    this.us = usSensor;
    this.nav = nav;
    us.off();
  }

  @Override
  public void doLocalize() {

    // rotate the robot until it sees a wall
    nav.rotate(robot.getRotateSpeed());
    // rotate the robot until it sees a wall
    while (24 < getFilteredData()) {
    }
    nav.stop();

    try {
      Thread.sleep(500);
    }
    catch (InterruptedException e) {
    }

    nav.rotate(robot.getRotateSpeed());
    // rotate till it doesnt see a wall;

    while (26 > getFilteredData()) {
    }
    nav.stop();
    // collect angle A from the odomter
    double angleA = odometer.getTheta();

    try {
      Thread.sleep(500);
    }
    catch (InterruptedException e) {
    }

    nav.rotate(-robot.getRotateSpeed());

    while (24 < getFilteredData()) {
    }
    nav.stop();

    nav.rotate(-robot.getRotateSpeed());
    // rotate till it doesnt see a wall;

    while (26 > getFilteredData()) {
    }
    nav.stop();
    // collect angleB
    double angleB = odometer.getTheta();

    // the cases here are switched since we want it to be 180 degrees from the
    // mid angle calculated
    double angleMid;
    if (angleB < angleA) {
      angleMid = 225 - (angleA + angleB) / 2;

    }
    else {
      angleMid = 45 - (angleA + angleB) / 2;
    }
    nav.turnTo(angleMid + 180);

    odometer.setPosition(new double[] { 0.0, 0.0, 0.0 }, new boolean[] { true, true, true });

  }

  private int getFilteredData() {
    int[] distances = new int[6];

    // do a ping
    for (int i = 0; i < 6; i++) {
      us.ping();

      // wait for the ping to complete
      try {
        Thread.sleep(50);
      }
      catch (InterruptedException e) {
      }

      // there will be a delay here
      distances[i] = us.getDistance();
    }
    sort(distances);
    int distance = distances[3];
    if (distance > 100)
      distance = 100;

    return distance;
  }

  private void sort(int[] array) {
    int length = array.length;
    for (int i = 0; i < length; i++) {
      for (int j = 1; j < length - i; j++) {
        if (array[j - 1] > array[j]) {
          int t = array[j - 1];
          array[j - 1] = array[j];
          array[j] = t;
        }
      }
    }
  }
}
