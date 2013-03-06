package ca.mcgill.dpm.winter2013.group6.tests;

import lejos.nxt.LightSensor;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.localization.LightLocalizer;
import ca.mcgill.dpm.winter2013.group6.localization.Localizer;
import ca.mcgill.dpm.winter2013.group6.localization.USLocalizer;
import ca.mcgill.dpm.winter2013.group6.navigator.NoObstacleNavigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class LocalizerTest implements Runnable {
  Odometer odometer;
  NXTRegulatedMotor leftMotor;
  NXTRegulatedMotor rightMotor;

  public LocalizerTest(Odometer odometer, NXTRegulatedMotor leftMotor, NXTRegulatedMotor rightMotor) {
    this.odometer = odometer;
    this.leftMotor = leftMotor;
    this.rightMotor = rightMotor;
  }

  /*
   * to run this test in main just use :
   * 
   * // LocalizerTest tester = new LocalizerTest(odometer, leftMotor,
   * rightMotor); tester.run();
   */

  @Override
  public void run() {
    USLocalizerTest();

  }

  public void lightLocalizerTest() {
    Localizer test = new LightLocalizer(odometer, new LightSensor(SensorPort.S2),
        new NoObstacleNavigator(odometer, leftMotor, rightMotor));
    test.doLocalize();
  }

  public void USLocalizerTest() {
    Localizer test = new USLocalizer(odometer, new UltrasonicSensor(SensorPort.S1),
        new NoObstacleNavigator(odometer, leftMotor, rightMotor));
    test.doLocalize();
  }
}
