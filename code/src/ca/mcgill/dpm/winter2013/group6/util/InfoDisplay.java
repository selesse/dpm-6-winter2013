package ca.mcgill.dpm.winter2013.group6.util;

import lejos.nxt.LCD;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.util.TimerListener;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class InfoDisplay implements TimerListener {
  private Odometer odometer;
  private UltrasonicSensor ultrasonicSensor;
  private TouchSensor leftTouchSensor;
  private TouchSensor rightTouchSensor;

  public InfoDisplay(Odometer odometer) {
    this.odometer = odometer;
  }

  public InfoDisplay(Odometer odometer, UltrasonicSensor ultrasonicSensor,
      TouchSensor leftTouchSensor, TouchSensor rightTouchSensor) {
    this(odometer);
    this.leftTouchSensor = leftTouchSensor;
    this.rightTouchSensor = rightTouchSensor;
    this.ultrasonicSensor = ultrasonicSensor;
  }

  public void timedOut() {
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
    LCD.drawInt((int) ultrasonicSensor.getDistance(), 4, 4);
    LCD.drawString(leftTouchSensor.isPressed() ? "true" : "false", 4, 5);
    LCD.drawString(rightTouchSensor.isPressed() ? "true" : "false", 4, 6);
  }

}
