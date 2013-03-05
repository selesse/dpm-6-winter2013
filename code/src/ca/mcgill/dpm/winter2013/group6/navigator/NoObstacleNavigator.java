package ca.mcgill.dpm.winter2013.group6.navigator;

import lejos.nxt.NXTRegulatedMotor;
import lejos.nxt.Sound;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

/**
 * {@link Navigator} implementation which does not take into consideration
 * obstacles.
 * 
 * @author Alex Selesse
 * 
 */
public class NoObstacleNavigator extends AbstractNavigator {
  int FORWARD_SPEED = 500;

  public NoObstacleNavigator(Odometer odometer, NXTRegulatedMotor leftMotor,
      NXTRegulatedMotor rightMotor) {
    super(odometer, leftMotor, rightMotor);
  }

  @Override
  public void travelTo(double x, double y) {

    double xToll = 1, yToll = 1;
    // runs till it is within the tolerance
    while (Math.abs(x - odometer.getX()) > xToll && Math.abs(y - odometer.getY()) > yToll) {

      // Calculate the new destinaton theta to turn
      double desTheta = Math.atan2(x - odometer.getX(), y - odometer.getY());
      desTheta = Math.toDegrees(desTheta);
      // not sure about the correctness of this part
      turnTo(desTheta);
      setSpeed(FORWARD_SPEED, FORWARD_SPEED);
    }
    Sound.beep();
    stop();

  }

  @Override
  public void run() {
    // TODO

  }

  @Override
  public void travelTo(double x, double y, double theta) {
    // testing needed!
    travelTo(x, y);
    turnTo(theta);

  }

}
