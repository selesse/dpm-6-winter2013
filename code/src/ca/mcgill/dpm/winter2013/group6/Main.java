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
import ca.mcgill.dpm.winter2013.group6.bluetooth.BluetoothReceiver;
import ca.mcgill.dpm.winter2013.group6.localization.LightLocalizer;
import ca.mcgill.dpm.winter2013.group6.localization.Localizer;
import ca.mcgill.dpm.winter2013.group6.localization.SmartLightLocalizer;
import ca.mcgill.dpm.winter2013.group6.localization.UltrasonicLocalizer;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.navigator.ObstacleNavigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.util.Coordinate;
import ca.mcgill.dpm.winter2013.group6.util.InfoDisplay;
import ca.mcgill.dpm.winter2013.group6.util.PlayingField;
import ca.mcgill.dpm.winter2013.group6.util.Robot;

/**
 * Entry point to the application.
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
  private static BluetoothReceiver bluetooth;
  private static ObstacleAvoider touchAvoidance;
  private static ObstacleAvoider ultrasonicAvoidance;
  private static PlayingField playingField;
  private static Localizer lightLocalizer;
  private static Localizer ultrasonicLocalizer;
  private static Localizer smartLightLocalizer;
  private static Thread odometerThread;
  private static Thread infoDisplayThread;
  private static Thread navigatorThread;
  private static Thread bluetoothThread;
  private static Thread touchAvoidanceThread;
  private static Thread ultrasonicAvoidanceThread;
  private static Thread lightLocalizerThread;
  private static Thread ultrasonicLocalizerThread;
  private static Thread smartLocalizerThread;

  public static void main(String[] args) {
    int buttonChoice;

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

    initializeMotorsAndSensors();
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
    odometerThread.start();
    infoDisplayThread.start();

    ultrasonicSensor.continuous();
    touchAvoidanceThread.start();
    ultrasonicAvoidanceThread.start();

    navigator.setCoordinates(new Coordinate[] { new Coordinate(50, 50) });
    navigatorThread.start();
  }

  private static void performRightButtonAction() {
    odometerThread.start();
    infoDisplayThread.start();

    // bluetoothThread.start();

    // wait until the bluetooth thread finishes before continuing
    // try {
    // bluetoothThread.join();
    // }
    // catch (InterruptedException e) {
    // }

    // Transmission transmission = bluetooth.getTransmission();
    // Bluetooth.setPower(false);

    // navigator.setCoordinates(new Coordinate[] { new
    // Coordinate((transmission.getBallDispenserX()),
    // transmission.getBallDispenserY() - (int) (30.5 * 5)) });
    // BallLauncher ballLauncher = new BallLauncherImpl(ballThrowingMotor, 1);
    // Thread ballLauncherThread = new Thread(ballLauncher);

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

    Sound.beep();

    int ballDispenserX = -1;
    // transmission.getBallDispenserX();
    int ballDispenserY = 5;
    // transmission.getBallDispenserY();

    double desiredReceivePositionX;
    double desiredReceivePositionY;

    double ballDispenserPositionX = (ballDispenserX * 30.5);
    double ballDispenserPositionY = (ballDispenserY * 30.5);

    if (ballDispenserX < 0) {

      desiredReceivePositionX = (ballDispenserPositionX + 2 * (30.5));
      desiredReceivePositionY = ballDispenserPositionY;
    }
    else if (ballDispenserY < 0) {

      desiredReceivePositionX = ballDispenserPositionX;
      desiredReceivePositionY = (ballDispenserPositionY + 2 * (30.5));
    }
    else if (ballDispenserX > ballDispenserY) {

      desiredReceivePositionX = (ballDispenserPositionX - 2 * (30.5));
      desiredReceivePositionY = ballDispenserPositionY;
    }
    else {

      desiredReceivePositionX = ballDispenserPositionX;
      desiredReceivePositionY = (ballDispenserPositionY - 2 * (30.5));
    }

    navigator.travelTo(desiredReceivePositionX, desiredReceivePositionY);
    smartLightLocalizer = new SmartLightLocalizer(odometer, navigator, lightSensor, new Coordinate(
        (int) desiredReceivePositionX, (int) desiredReceivePositionY));
    Thread smartLightLocalizerThread = new Thread(smartLightLocalizer);
    smartLightLocalizerThread.start();
    try {
      smartLightLocalizerThread.join();
    }
    catch (InterruptedException e1) {
      // TODO Auto-generated catch block
      e1.printStackTrace();
    }
    navigator.face(0);
    navigator.face(((ObstacleNavigator) navigator).getTurningAngle(ballDispenserPositionX,
        ballDispenserPositionY) + 180);

    ballThrowingMotor.rotate(5);
    ballThrowingMotor.flt(true);

    navigator.travelStraight(-1.2 * 30.5 + 1.5);

    try {
      Thread.sleep(3000);
    }
    catch (InterruptedException e) {
      // TODO Auto-generated catch block
      Sound.beep();
      e.printStackTrace();
    }

    navigator.travelStraight(1.2 * 30.5 - 1.5);
    ballThrowingMotor.rotate(-5);
    ballThrowingMotor.flt(true);

    // start the ball launching thread, wait for it to finish
    /*
     * ballLauncherThread.start(); try { ballLauncherThread.join(); } catch
     * (InterruptedException e) {
     * 
     * } // go back to origin navigator.travelTo(0, 0);
     */
  }

  private static void initializeMotorsAndSensors() {
    leftMotor = new NXTRegulatedMotor(MotorPort.A);
    rightMotor = new NXTRegulatedMotor(MotorPort.B);
    ballThrowingMotor = new NXTRegulatedMotor(MotorPort.C);

    ultrasonicSensor = new UltrasonicSensor(SensorPort.S1);
    lightSensor = new LightSensor(SensorPort.S2);
    leftTouchSensor = new TouchSensor(SensorPort.S3);
    rightTouchSensor = new TouchSensor(SensorPort.S4);
  }

  private static void initializeComponents() {
    patBot = new Robot(2.69, 2.69, 15.7, leftMotor, rightMotor);
    playingField = new PlayingField(10, 10);
    odometer = new Odometer(patBot);
    infoDisplay = new InfoDisplay(odometer, ultrasonicSensor, leftTouchSensor, rightTouchSensor);
    navigator = new ObstacleNavigator(odometer, leftMotor, rightMotor, ultrasonicSensor,
        leftTouchSensor, rightTouchSensor);
    bluetooth = new BluetoothReceiver();
    touchAvoidance = new TouchAvoidanceImpl(odometer, navigator, playingField, leftTouchSensor,
        rightTouchSensor);
    ultrasonicAvoidance = new UltrasonicAvoidanceImpl(odometer, navigator, playingField,
        ultrasonicSensor);
    lightLocalizer = new LightLocalizer(odometer, navigator, lightSensor, 1);
    ultrasonicLocalizer = new UltrasonicLocalizer(odometer, navigator, ultrasonicSensor, 1);
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
    smartLocalizerThread = new Thread(smartLightLocalizer);
  }

  private static void initializeObstacleAvoiders() {
    List<ObstacleAvoider> obstacleAvoiders = new ArrayList<ObstacleAvoider>();
    obstacleAvoiders.add(touchAvoidance);
    obstacleAvoiders.add(ultrasonicAvoidance);
    ((ObstacleNavigator) navigator).setAvoiderList(obstacleAvoiders);
  }
}
