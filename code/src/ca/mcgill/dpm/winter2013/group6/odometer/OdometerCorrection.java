package ca.mcgill.dpm.winter2013.group6.odometer;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;

public class OdometerCorrection implements Runnable {
  private Odometer odometer;
  private LightSensor ls;
  private static int threshold = 50;
  private int sensorAverage = 0;
  private static final double LIGHT_SENSOR_DISTANCE = 11.6;

  public OdometerCorrection(Odometer odometer, LightSensor lightSensor) {
    this.odometer = odometer;
    this.ls = lightSensor;

  }

  @Override
  public void run() {
    // TODO Auto-generated method stub

  }

  public static int axis() {

  }

  public int closestGridLine() {
    double position[] = new double[3];
    this.odometer.getPosition(position, new boolean[] { true, true, true });
    if (Motor.A.getRotationSpeed() != Motor.B.getRotationSpeed()) {
      return -1;
    }

  }
}
