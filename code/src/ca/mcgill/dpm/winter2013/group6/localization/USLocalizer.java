package ca.mcgill.dpm.winter2013.group6.localization;

import lejos.nxt.Sound;
import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class USLocalizer extends AbstractLocalizer {
  private UltrasonicSensor us;

  public USLocalizer(Odometer odometer, UltrasonicSensor usSensor, int corner, Navigator navigator) {
    super(odometer, navigator, corner);
    this.us = usSensor;
    us.off();
  }

  @Override
  public void doLocalize() {
    int min = 20;
    int max = 24;
    navigator.setRotateSpeed(robot.getRotateSpeed());
    // rotate till it doesnt see a wall;

    while (max > getFilteredData()) {
    }
    navigator.stop();
    // rotate the robot until it sees a wall
    navigator.setRotateSpeed(robot.getRotateSpeed());
    while (min < getFilteredData()) {
    }
    navigator.stop();
    odometer.setPosition(new double[] { 0, 0, 0 }, new boolean[] { true, true, true });

    // collect angle A from the odomter
    Sound.beep();
    double angleA = odometer.getTheta();

    try {
      Thread.sleep(500);
    }
    catch (InterruptedException e) {
    }

    navigator.setRotateSpeed(robot.getRotateSpeed());
    // rotate till it doesnt see a wall;

    while (max > getFilteredData()) {
    }
    navigator.stop();

    try {
      Thread.sleep(500);
    }
    catch (InterruptedException e) {
    }

    navigator.setRotateSpeed(-robot.getRotateSpeed());

    while (min < getFilteredData()) {
    }
    navigator.stop();
    double angleB = odometer.getTheta();
    Sound.beep();
    navigator.setRotateSpeed(-robot.getRotateSpeed());
    // rotate till it doesnt see a wall;

    while (max > getFilteredData()) {
    }
    navigator.stop();
    // collect angleB

    // the cases here are switched since we want it to be 180 degrees from the
    // mid angle calculated
    double angleMid;
    if (angleB < angleA) {
      angleMid = 45 - (angleA + angleB) / 2;

    }
    else {
      angleMid = 225 - (angleA + angleB) / 2;
    }
    // useful for testing
    /*
     * odometer.setPosition(new double[] { angleA, angleB, angleMid }, new
     * boolean[] { true, true, true });
     */

    navigator.turnTo(angleMid - odometer.getTheta() + 45);

    odometer.setPosition(new double[] { 0, 0, 45 }, new boolean[] { true, true, true });
    // walk to somewhere so it can perform the lightLocalization
    navigator.travelTo(15, 15);

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

  @Override
  public void run() {
    // TODO Auto-generated method stub

  }
}
