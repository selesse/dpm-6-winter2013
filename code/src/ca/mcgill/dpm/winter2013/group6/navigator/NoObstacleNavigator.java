package ca.mcgill.dpm.winter2013.group6.navigator;

import lejos.nxt.NXTRegulatedMotor;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

/**
 * {@link Navigator} implementation which does not take into consideration
 * obstacles.
 *
 * @author Alex Selesse
 *
 */
public class NoObstacleNavigator extends AbstractNavigator {

  public NoObstacleNavigator(Odometer odometer, NXTRegulatedMotor leftMotor,
      NXTRegulatedMotor rightMotor) {
    super(odometer, leftMotor, rightMotor);
  }

  @Override
  public void travelTo(double x, double y) {
    // TODO

  }

  @Override
  public void run() {
    // TODO

  }

}
