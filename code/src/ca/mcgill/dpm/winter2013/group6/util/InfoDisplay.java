package ca.mcgill.dpm.winter2013.group6.util;

import lejos.nxt.LCD;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

/**
 * InfoDisplay class that takes care of displaying some debugging information on
 * the NXT LCD screen.
 * 
 * @author Arthur Kam
 */
public class InfoDisplay implements Runnable {
  private Odometer odometer;
  private UltrasonicSensor ultrasonicSensor;
  private TouchSensor leftTouchSensor;
  private TouchSensor rightTouchSensor;

  /**
   * Initialize the InfoDisplay with the objects you want to keep track of.
   * 
   * @param odometer
   *          The odometer - display the (X, Y, theta).
   * @param ultrasonicSensor
   *          The ultrasonic sensor - display the distance.
   * @param leftTouchSensor
   *          Display whether or not the left sensor is pressed.
   * @param rightTouchSensor
   *          Display whether or not the right sensor is pressed.
   */
  public InfoDisplay(Odometer odometer, UltrasonicSensor ultrasonicSensor,
      TouchSensor leftTouchSensor, TouchSensor rightTouchSensor) {
    this.odometer = odometer;
    this.leftTouchSensor = leftTouchSensor;
    this.rightTouchSensor = rightTouchSensor;
    this.ultrasonicSensor = ultrasonicSensor;
  }

  @Override
  public void run() {
    LCD.clear();
    LCD.drawString("X: ", 0, 0);
    LCD.drawString("Y: ", 0, 1);
    LCD.drawString("H: ", 0, 2);
    LCD.drawString(Double.toString(odometer.getX()), 3, 0);
    LCD.drawString(Double.toString(odometer.getY()), 3, 1);
    LCD.drawInt((int) odometer.getTheta(), 3, 2);
    LCD.drawString("US: ", 0, 4);
    LCD.drawString("TL: ", 0, 5);
    LCD.drawString("TR: ", 0, 6);
    LCD.drawInt(ultrasonicSensor.getDistance(), 4, 4);
    LCD.drawString(leftTouchSensor.isPressed() ? "true" : "false", 4, 5);
    LCD.drawString(rightTouchSensor.isPressed() ? "true" : "false", 4, 6);
  }

}
