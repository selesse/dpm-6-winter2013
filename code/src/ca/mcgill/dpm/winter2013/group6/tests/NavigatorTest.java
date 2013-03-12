package ca.mcgill.dpm.winter2013.group6.tests;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import ca.mcgill.dpm.winter2013.group6.navigator.AbstractNavigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class NavigatorTest extends AbstractNavigator {

  public NavigatorTest(Odometer odometer, NXTRegulatedMotor leftMotor, NXTRegulatedMotor rightMotor) {
    super(odometer, leftMotor, rightMotor);
  }

  // testcase id: #1.1.1
  public void rotateTest() {

    Sound.beep();

    turnTo(90);
    try {
      Thread.sleep(1000);
    }
    catch (InterruptedException e) {
      // TODO Auto-generated catch block
      // e.printStackTrace();
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

  // testcase id: #1.1.2
  public void walkTest() {
    travelTo(0, 30);

    try {
      Thread.sleep(1000);
    }
    catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    travelTo(0, 60);

  }

  // testcase id: #1.2.1
  public void travelToTest() {
    travelTo(0, 30);
    travelTo(30, 15);
    travelTo(30, 30);
    travelTo(0, 15);
  }

}
