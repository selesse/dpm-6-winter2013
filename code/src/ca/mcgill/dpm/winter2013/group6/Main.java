package ca.mcgill.dpm.winter2013.group6;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.avoidance.ObstacleAvoider;
import ca.mcgill.dpm.winter2013.group6.avoidance.TouchAvoidanceImpl;
import ca.mcgill.dpm.winter2013.group6.avoidance.UltrasonicAvoidanceImpl;
import ca.mcgill.dpm.winter2013.group6.bluetooth.Bluetooth;
import ca.mcgill.dpm.winter2013.group6.bluetooth.PlayerRole;
import ca.mcgill.dpm.winter2013.group6.bluetooth.Transmission;
import ca.mcgill.dpm.winter2013.group6.launcher.BallLauncher;
import ca.mcgill.dpm.winter2013.group6.launcher.BallLauncherImpl;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.navigator.ObstacleNavigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.util.Coordinate;
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

    UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);
    TouchSensor leftTouchSensor = new TouchSensor(SensorPort.S3);
    TouchSensor rightTouchSensor = new TouchSensor(SensorPort.S4);

    Robot patBot = new Robot(2.71, 2.71, 16.2, leftMotor, rightMotor);

    do {
      // clear the display
      LCD.clear();

      LCD.drawString("< Test | Demo  >", 0, 0);
      LCD.drawString("  Mode | Mode   ", 0, 1);
      LCD.drawString("       |        ", 0, 2);
      LCD.drawString("       |        ", 0, 3);

      buttonChoice = Button.waitForPress();
    }
    while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT
        && buttonChoice != Button.ID_ESCAPE);

    // initialize all the constructors for all the components
    Odometer odometer = new Odometer(patBot);
    InfoDisplay infoDisplay = new InfoDisplay(odometer, ultrasonicSensor, leftTouchSensor,
        rightTouchSensor);
    Navigator navigator = new ObstacleNavigator(odometer, leftMotor, rightMotor, ultrasonicSensor,
        leftTouchSensor, rightTouchSensor);
    Bluetooth bluetooth = new Bluetooth();
    ObstacleAvoider touchAvoidance = new TouchAvoidanceImpl(odometer, navigator, leftTouchSensor,
        rightTouchSensor);
    ObstacleAvoider ultrasonicAvoidance = new UltrasonicAvoidanceImpl(odometer, navigator,
        ultrasonicSensor);

    // extra things you need to set up here
    List<ObstacleAvoider> obstacleAvoiders = new ArrayList<ObstacleAvoider>();
    obstacleAvoiders.add(touchAvoidance);
    obstacleAvoiders.add(ultrasonicAvoidance);

    // initialize all the threads for every component
    Thread odometerThread = new Thread(odometer);
    Thread infoDisplayThread = new Thread(infoDisplay);
    Thread navigatorThread = new Thread(navigator);
    Thread bluetoothThread = new Thread(bluetooth);
    Thread touchAvoidanceThread = new Thread(touchAvoidance);
    Thread ultrasonicAvoidanceThread = new Thread(ultrasonicAvoidance);

    if (buttonChoice == Button.ID_LEFT) {
      // test any component you want here
    }
    else if (buttonChoice == Button.ID_RIGHT) {
      odometerThread.start();
      infoDisplayThread.start();
      bluetoothThread.start();

      // wait until the bluetooth thread finishes before continuing
      try {
        bluetoothThread.join();
      }
      catch (InterruptedException e) {

      }

      Transmission transmission = bluetooth.getTransmission();

      if (transmission.role == PlayerRole.ATTACKER) {
        // go to the ball dispenser coordinates
        navigator
            .setCoordinates(new Coordinate[] { new Coordinate(transmission.bx, transmission.by) });
        BallLauncher ballLauncher = new BallLauncherImpl(ballThrowingMotor, transmission.d1);
        Thread ballLauncherThread = new Thread(ballLauncher);
        touchAvoidanceThread.start();
        ultrasonicAvoidanceThread.start();

        // start the navigation thread, once we're done navigating, throw the
        // ball
        navigatorThread.start();
        try {
          navigatorThread.join();
        }
        catch (InterruptedException e) {
        }
        ballLauncherThread.start();
      }
      else if (transmission.role == PlayerRole.DEFENDER) {
        // TODO defense
      }
    }

    while (Button.waitForPress() != Button.ID_ESCAPE) {
      ;
    }
  }
}
