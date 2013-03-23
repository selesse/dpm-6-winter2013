package ca.mcgill.dpm.winter2013.group6.odometer;

import lejos.nxt.LightSensor;
import lejos.nxt.Motor;
import lejos.util.TimerListener;

public class OdometerCorrection implements TimerListener {
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
  public void timedOut() {
    // if they have the same speed == moving straight, going forward
    if (Motor.A.getRotationSpeed() == Motor.B.getRotationSpeed() && closest90() > 0
        && Motor.A.getRotationSpeed() > 0) {

    }

  }

  public boolean cloestAxis() {
    double x = odometer.getX() % 30.48;
    double y = odometer.getY() % 30.48;
    double theta = odometer.getTheta();
    double speed = Motor.A.getRotationSpeed();
    double xComp = Math.cos(theta);
    double yComp = Math.sin(theta);

  }

  public int getClosestGridLine() {
    double position[] = new double[3];
    this.odometer.getPosition(position, new boolean[] { true, true, true });
    if (Motor.A.getRotationSpeed() != Motor.B.getRotationSpeed()) {
      return -1;
    }

  }

  public int closest90() {
    double range = 5;
    double theta = odometer.getTheta();
    if (0 + range > theta && 0 - range < theta) {
      return 1;
    }
    else if (90 + range > theta && 90 - range < theta) {
      return 1;
    }
    else if (180 + range > theta && 180 - range < theta) {
      return 1;
    }
    else if (270 + range > theta && 270 - range < theta) {
      return 1;
    }
    else {
      return -1;
    }
  }
}
