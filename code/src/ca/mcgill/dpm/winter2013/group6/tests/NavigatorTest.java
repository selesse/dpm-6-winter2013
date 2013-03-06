package ca.mcgill.dpm.winter2013.group6.tests;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.navigator.NoObstacleNavigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class NavigatorTest {

  /**
   * @param args
   */
  Navigator nav;
  NXTRegulatedMotor leftMotor;
  NXTRegulatedMotor rightMotor;

  public NavigatorTest(Odometer odometer, NXTRegulatedMotor leftMotor, NXTRegulatedMotor rightMotor) {
    this.nav = new NoObstacleNavigator(odometer, leftMotor, rightMotor);
    this.leftMotor = leftMotor;
    this.rightMotor = rightMotor;

  }

  public void run() {
    walkTest();

  }

  // testcase id: #1.1.1
  public void rotateTest() {

    Sound.beep();

    nav.turnTo(90);
    try {
      Thread.sleep(1000);
    }
    catch (InterruptedException e) {
      // TODO Auto-generated catch block
      // e.printStackTrace();
    }

    nav.turnTo(180);
    try {
      Thread.sleep(1000);
    }
    catch (InterruptedException e) {
      // TODO Auto-generated catch block
      // e.printStackTrace();
    }

    nav.turnTo(270);
    try {
      Thread.sleep(1000);
    }
    catch (InterruptedException e) {
      // TODO Auto-generated catch block
      // e.printStackTrace();

    }

    nav.turnTo(0);

  }

  // testcase id: #1.1.2
  public void walkTest() {
    nav.travelTo(0, 30);
    try {
      Thread.sleep(1000);
    }
    catch (InterruptedException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    nav.travelTo(0, 60);
  }

  // testcase id: #1.2.1
  public void travelToTest() {
    nav.travelTo(0, 30, 0);
    nav.travelTo(30, 15, 45);
    nav.travelTo(30, 30, 100);
    nav.travelTo(0, 15, 250);
    nav.travelTo(0, 0, 0);
  }
}
