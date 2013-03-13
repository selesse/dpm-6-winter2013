package ca.mcgill.dpm.winter2013.group6;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.Motor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.tests.NavigatorTest;
import ca.mcgill.dpm.winter2013.group6.util.InfoDisplay;
import ca.mcgill.dpm.winter2013.group6.util.Robot;

/**
 * Entrypoint to the application. Will start the robot to be either attacker or
 * defender.
 * 
 * @author Alex Selesse
 * 
 */
public class Main {
  public static void main(String[] args) {
    int buttonChoice;
    NXTRegulatedMotor ballThrowingMotor = new NXTRegulatedMotor(MotorPort.C);
    NXTRegulatedMotor leftMotor = new NXTRegulatedMotor(MotorPort.A);
    NXTRegulatedMotor rightMotor = new NXTRegulatedMotor(MotorPort.B);

    Robot patBot = new Robot(2.71, 2.71, 16.2, leftMotor, rightMotor);

    do {
      // clear the display
      LCD.clear();

      LCD.drawString("< Left | Right >", 0, 0);
      LCD.drawString("  Mode | Mode   ", 0, 1);
      LCD.drawString("       |        ", 0, 2);
      LCD.drawString("       |        ", 0, 3);

      buttonChoice = Button.waitForPress();
    }
    while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT);

    // start the odometer
    Odometer odometer = new Odometer(patBot);

    // if we press the left button, launch the test application
    if (buttonChoice == Button.ID_LEFT) {
      odometer.start();
      InfoDisplay display = new InfoDisplay(odometer, new UltrasonicSensor(SensorPort.S1),
          new TouchSensor(SensorPort.S3), new TouchSensor(SensorPort.S4));
      Motor.A.flt(true);
      Motor.B.flt(false);
    }
    else {
      odometer.start();
      InfoDisplay display = new InfoDisplay(odometer, new UltrasonicSensor(SensorPort.S1),
          new TouchSensor(SensorPort.S3), new TouchSensor(SensorPort.S4));
      // Navigator navigator = new NoObstacleNavigator(odometer, leftMotor,
      // rightMotor);
      NavigatorTest tester = new NavigatorTest(odometer, leftMotor, rightMotor);
      tester.travelToTest();

      /*
       * Localizer tester = new LocalizerTest(odometer, navigator, new
       * UltrasonicSensor(SensorPort.S1), new LightSensor(SensorPort.S2),
       * buttonChoice); // set here which routine to run tester.doLocalize();
       */

    }

    while (Button.waitForPress() != Button.ID_ESCAPE) {
      ;
    }
    System.exit(0);
  }
}
