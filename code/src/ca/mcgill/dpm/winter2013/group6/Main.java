package ca.mcgill.dpm.winter2013.group6;

import java.util.ArrayList;
import java.util.List;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.LightSensor;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.SensorPort;
import lejos.nxt.Sound;
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
import ca.mcgill.dpm.winter2013.group6.odometer.OdometerCorrection;
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
  private static NXTRegulatedMotor leftMotor;
  private static NXTRegulatedMotor rightMotor;
  private static NXTRegulatedMotor ballThrowingMotor;
  private static UltrasonicSensor ultrasonicSensor;
  private static LightSensor lightSensor;
  private static TouchSensor leftTouchSensor;
  private static TouchSensor rightTouchSensor;
  private static Robot patBot;
  private static Odometer odometer;
  private static InfoDisplay infoDisplay;
  private static Navigator navigator;
  private static Bluetooth bluetooth;
  private static ObstacleAvoider touchAvoidance;
  private static ObstacleAvoider ultrasonicAvoidance;
  private static Localizer lightLocalizer;
  private static Localizer ultrasonicLocalizer;
  private static OdometerCorrection odometerCorrection;
  private static Thread odometerThread;
  private static Thread infoDisplayThread;
  private static Thread navigatorThread;
  private static Thread bluetoothThread;
  private static Thread touchAvoidanceThread;
  private static Thread ultrasonicAvoidanceThread;
  private static Thread lightLocalizerThread;
  private static Thread ultrasonicLocalizerThread;
  private static Thread odometryCorrectionThread;

  public static void main(String[] args) {
    int buttonChoice;
    // initialize all the motors (left wheel, right wheel, ball thrower)
    leftMotor = new NXTRegulatedMotor(MotorPort.A);
    rightMotor = new NXTRegulatedMotor(MotorPort.B);
    ballThrowingMotor = new NXTRegulatedMotor(MotorPort.C);

    ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);
    lightSensor = new LightSensor(SensorPort.S2);
    leftTouchSensor = new TouchSensor(SensorPort.S3);
    rightTouchSensor = new TouchSensor(SensorPort.S4);

    patBot = new Robot(2.71, 2.71, 15.5, leftMotor, rightMotor);

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

    initializeComponents();
    initializeComponentThreads();
    initializeObstacleAvoiders();

    if (buttonChoice == Button.ID_LEFT) {
      performLeftButtonAction();
    }
    else if (buttonChoice == Button.ID_RIGHT) {
      performRightButtonAction();
    }

    while (Button.waitForPress() != Button.ID_ESCAPE) {
      ;
    }
  }

  private static void performLeftButtonAction() {
    int goalX = (int) (2.0 * 30.5);
    int goalY = (int) (5.0 * 30.5);
    odometerThread.start();
    infoDisplayThread.start();

    try {
      // start and finish ultrasonic localization
      ultrasonicLocalizerThread.start();
      ultrasonicLocalizerThread.join();

      // travel to (15, 15)
      navigator.travelTo(15, 15);
      ;
      // start and finish light localization
      lightLocalizerThread.start();
      lightLocalizerThread.join();

      // odometryCorrectionThread.start();
      navigator.setCoordinates(new Coordinate[] { new Coordinate(goalX, goalY) });
      touchAvoidanceThread.start();
      ultrasonicAvoidanceThread.start();

      navigatorThread.start();
      navigatorThread.join();
      navigator.face(45);
      lightLocalizer = new LightLocalizer(odometer, navigator, lightSensor, 1);
      lightLocalizerThread = new Thread(lightLocalizer);

      lightLocalizerThread.start();
      lightLocalizerThread.join();
      Thread.sleep(100);
      odometer.setPosition(new double[] { odometer.getX() + goalX, odometer.getY() + goalY, 0 },
          new boolean[] { true, true, false });
      Thread.sleep(1500);

      Sound.beep();
      navigator.face(0);

      navigator.travelTo(goalX, goalY);
      navigator.turnTo(goalX, goalY + 5.0 * 30.5);

      Sound.beep();

      BallLauncher ballLauncher = new BallLauncherImpl(ballThrowingMotor, 30);
      Thread ballLauncherThread = new Thread(ballLauncher);

      ballLauncherThread.start();
      ballLauncherThread.join();
      Sound.beep();
      navigator.travelTo(0, 0);

    }
    catch (InterruptedException e) {
    }
  }

  private static void performRightButtonAction() {
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

    navigator.setCoordinates(new Coordinate[] { new Coordinate((transmission.bx), transmission.by
        - (int) (30.5 * 5)) });
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
    Sound.beep();
    navigator.turnTo(transmission.bx, transmission.by);

    // start the ball launching thread, wait for it to finish
    ballLauncherThread.start();
    try {
      ballLauncherThread.join();
    }
    catch (InterruptedException e) {

    }
    // go back to origin
    navigator.travelTo(0, 0);
  }

  private static void initializeComponents() {
    odometer = new Odometer(patBot);
    infoDisplay = new InfoDisplay(odometer, ultrasonicSensor, leftTouchSensor, rightTouchSensor);
    navigator = new ObstacleNavigator(odometer, leftMotor, rightMotor, ultrasonicSensor,
        leftTouchSensor, rightTouchSensor);
    bluetooth = new Bluetooth();
    touchAvoidance = new TouchAvoidanceImpl(odometer, navigator, leftTouchSensor, rightTouchSensor);
    ultrasonicAvoidance = new UltrasonicAvoidanceImpl(odometer, navigator, ultrasonicSensor);
    lightLocalizer = new LightLocalizer(odometer, navigator, lightSensor, 1);
    ultrasonicLocalizer = new UltrasonicLocalizer(odometer, navigator, ultrasonicSensor, 1);
    odometerCorrection = new OdometerCorrection(odometer, lightSensor);
  }

  private static void initializeComponentThreads() {
    odometerThread = new Thread(odometer);
    infoDisplayThread = new Thread(infoDisplay);
    navigatorThread = new Thread(navigator);
    bluetoothThread = new Thread(bluetooth);
    touchAvoidanceThread = new Thread(touchAvoidance);
    ultrasonicAvoidanceThread = new Thread(ultrasonicAvoidance);
    lightLocalizerThread = new Thread(lightLocalizer);
    ultrasonicLocalizerThread = new Thread(ultrasonicLocalizer);
    odometryCorrectionThread = new Thread(odometerCorrection);
  }

  private static void initializeObstacleAvoiders() {
    List<ObstacleAvoider> obstacleAvoiders = new ArrayList<ObstacleAvoider>();
    obstacleAvoiders.add(touchAvoidance);
    obstacleAvoiders.add(ultrasonicAvoidance);
    ((ObstacleNavigator) navigator).setAvoiderList(obstacleAvoiders);
  }
}
