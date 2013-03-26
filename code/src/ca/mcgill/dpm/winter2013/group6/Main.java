package ca.mcgill.dpm.winter2013.group6;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.avoidance.ObstacleAvoider;
import ca.mcgill.dpm.winter2013.group6.avoidance.TouchAvoidanceImpl;
import ca.mcgill.dpm.winter2013.group6.avoidance.UltrasonicAvoidanceImpl;
import ca.mcgill.dpm.winter2013.group6.bluetooth.Bluetooth;
import ca.mcgill.dpm.winter2013.group6.bluetooth.Transmission;
import ca.mcgill.dpm.winter2013.group6.launcher.BallLauncher;
import ca.mcgill.dpm.winter2013.group6.launcher.BallLauncherImpl;
import ca.mcgill.dpm.winter2013.group6.localization.LightLocalizer;
import ca.mcgill.dpm.winter2013.group6.localization.Localizer;
import ca.mcgill.dpm.winter2013.group6.localization.UltrasonicLocalizer;
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
    // initialize all the motors (left wheel, right wheel, ball thrower)
    NXTRegulatedMotor leftMotor = new NXTRegulatedMotor(MotorPort.A);
    NXTRegulatedMotor rightMotor = new NXTRegulatedMotor(MotorPort.B);
    NXTRegulatedMotor ballThrowingMotor = new NXTRegulatedMotor(MotorPort.C);

    // initialize all the sensors
    UltrasonicSensor ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);
    LightSensor lightSensor = new LightSensor(SensorPort.S2);
    TouchSensor leftTouchSensor = new TouchSensor(SensorPort.S3);
    TouchSensor rightTouchSensor = new TouchSensor(SensorPort.S4);

    Robot patBot = new Robot(2.71, 2.71, 16.2, leftMotor, rightMotor);

    // wait for user input
    do {
      LCD.clear();

      LCD.drawString("< Test | Demo  >", 0, 0);
      LCD.drawString("  Mode | Mode   ", 0, 1);
      LCD.drawString("       |        ", 0, 2);
      LCD.drawString("       |        ", 0, 3);

      buttonChoice = Button.waitForPress();
    }
    while (buttonChoice != Button.ID_LEFT && buttonChoice != Button.ID_RIGHT
        && buttonChoice != Button.ID_ESCAPE);

    // initialize all the components
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
    Localizer lightLocalizer = new LightLocalizer(odometer, navigator, lightSensor, 1);
    Localizer ultrasonicLocalizer = new UltrasonicLocalizer(odometer, navigator, ultrasonicSensor,
        1);

    // initialize all the threads for every component
    Thread odometerThread = new Thread(odometer);
    Thread infoDisplayThread = new Thread(infoDisplay);
    Thread navigatorThread = new Thread(navigator);
    Thread bluetoothThread = new Thread(bluetooth);
    Thread touchAvoidanceThread = new Thread(touchAvoidance);
    Thread ultrasonicAvoidanceThread = new Thread(ultrasonicAvoidance);
    Thread lightLocalizerThread = new Thread(lightLocalizer);
    Thread ultrasonicLocalizerThread = new Thread(ultrasonicLocalizer);

    // additional things to set up
    List<ObstacleAvoider> obstacleAvoiders = new ArrayList<ObstacleAvoider>();
    obstacleAvoiders.add(touchAvoidance);
    obstacleAvoiders.add(ultrasonicAvoidance);
    // set the list of ObstacleAvoider objects
    ((ObstacleNavigator) navigator).setAvoiderList(obstacleAvoiders);

    if (buttonChoice == Button.ID_LEFT) {
      odometerThread.start();
      infoDisplayThread.start();

      try {
        // start and finish ultrasonic localization
        ultrasonicLocalizerThread.start();
        ultrasonicLocalizerThread.join();

        // travel to (15, 15)
        navigator.travelTo(15, 15);

        // start and finish light localization
        lightLocalizerThread.start();
        lightLocalizerThread.join();

        navigator.setCoordinates(new Coordinate[] {
            new Coordinate(30, 30),
            new Coordinate(80, 45),
            new Coordinate(0, 0) });

        touchAvoidanceThread.start();
        ultrasonicAvoidanceThread.start();

        navigatorThread.start();
        navigatorThread.join();

        BallLauncher ballLauncher = new BallLauncherImpl(ballThrowingMotor, 30);
        Thread ballLauncherThread = new Thread(ballLauncher);

        navigator.turnTo(30, 200);
        ballLauncherThread.start();
        ballLauncherThread.join();
      }
      catch (InterruptedException e) {
      }
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

      navigator.setCoordinates(new Coordinate[] { new Coordinate((transmission.getGoalX()),
          transmission.getGoalY() - (int) (30.5 * 5)) });
      BallLauncher ballLauncher = new BallLauncherImpl(ballThrowingMotor, 1);
      Thread ballLauncherThread = new Thread(ballLauncher);

      // start and finish ultrasonic localization
      ultrasonicLocalizerThread.start();
      try {
        ultrasonicLocalizerThread.join();
      }
      catch (InterruptedException e) {
        // don't do anything - this thread is not expected to be interrupted
      }

      navigator.travelTo(15, 15);

      // start and finish light localization
      lightLocalizerThread.start();
      try {
        lightLocalizerThread.join();
      }
      catch (InterruptedException e) {
        // don't do anything - this thread is not expected to be interrupted
      }

      ultrasonicSensor.continuous();
      // start the touch avoidance and the ultrasonic avoidance threads
      touchAvoidanceThread.start();
      ultrasonicAvoidanceThread.start();

      // start the navigation thread
      navigatorThread.start();
      try {
        navigatorThread.join();
      }
      catch (InterruptedException e) {
        // don't do anything - this thread is not expected to be interrupted
      }

      navigator.turnTo(transmission.getGoalX(), transmission.getGoalY());

      // start the ball launching thread, wait for it to finish
      ballLauncherThread.start();
      try {
        ballLauncherThread.join();
      }
      catch (InterruptedException e) {

      }
      navigator.travelTo(0, 0);
    }

    while (Button.waitForPress() != Button.ID_ESCAPE) {
      ;
    }
  }
}
