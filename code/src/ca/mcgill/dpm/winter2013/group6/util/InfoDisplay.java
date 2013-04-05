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
    while (true) {
      LCD.drawString("X: " + format(odometer.getX()), 0, 0);
      LCD.drawString("Y: " + format(odometer.getY()), 0, 1);
      LCD.drawString("H: " + format(odometer.getTheta()), 0, 2);

      LCD.drawString("US: " + format(ultrasonicSensor.getDistance()), 0, 4);
      LCD.drawString("TL: " + (leftTouchSensor.isPressed() ? "true" : "false"), 0, 5);
      LCD.drawString("TR: " + (rightTouchSensor.isPressed() ? "true" : "false"), 0, 6);
      try {
        Thread.sleep(25);
      }
      catch (InterruptedException e) {
      }
    }
  }

  private String format(double value) {
    // we pad it with extra whitespace because otherwise trailing numbers stay
    // on the screen
    return Double.toString(value) + "        ";
  }

}
