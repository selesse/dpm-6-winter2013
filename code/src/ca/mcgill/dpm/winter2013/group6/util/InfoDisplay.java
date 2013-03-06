package ca.mcgill.dpm.winter2013.group6.util;

import lejos.nxt.LCD;
import lejos.nxt.TouchSensor;
import lejos.nxt.UltrasonicSensor;
import lejos.util.Timer;
import lejos.util.TimerListener;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class InfoDisplay implements TimerListener {
  public static final int LCD_REFRESH = 100;
  private Odometer odo;
  private Timer lcdTimer;
  private UltrasonicSensor us;
  private TouchSensor leftSensor;
  private TouchSensor rightSensor;
  // arrays for displaying data
  private double[] pos;

  public InfoDisplay(Odometer odo) {
    this.odo = odo;
    this.lcdTimer = new Timer(LCD_REFRESH, this);

    // initialise the arrays for displaying data
    pos = new double[3];

    // start the timer
    lcdTimer.start();
  }

  public InfoDisplay(Odometer odo, UltrasonicSensor us, TouchSensor leftSensor,
      TouchSensor rightSensor) {
    this(odo);
    this.leftSensor = leftSensor;
    this.rightSensor = rightSensor;
    this.us = us;

  }

  public void timedOut() {
    odo.getPosition(pos);
    LCD.clear();
    LCD.drawString("X: ", 0, 0);
    LCD.drawString("Y: ", 0, 1);
    LCD.drawString("H: ", 0, 2);
    LCD.drawInt((int) (pos[0]), 3, 0);
    LCD.drawInt((int) (pos[1]), 3, 1);
    LCD.drawInt((int) pos[2], 3, 2);
    LCD.drawString("US: ", 0, 4);
    LCD.drawString("TL: ", 0, 5);
    LCD.drawString("TR: ", 0, 6);
    LCD.drawInt((int) us.getDistance(), 4, 4);
    LCD.drawString(leftSensor.isPressed() ? "true" : "false", 4, 5);
    LCD.drawString(rightSensor.isPressed() ? "true" : "false", 4, 6);

  }
}
