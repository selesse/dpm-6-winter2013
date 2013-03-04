package ca.mcgill.dpm.winter2013.group6.navigator;

import lejos.nxt.NXTRegulatedMotor;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

/**
 * {@link Navigator} implementation which takes into consideration obstacles.
 *
 * @author Alex Selesse
 *
 */
public class ObstacleNavigator extends NoObstacleNavigator {

  public ObstacleNavigator(Odometer odometer, NXTRegulatedMotor leftMotor,
      NXTRegulatedMotor rightMotor) {
    super(odometer, leftMotor, rightMotor);
  }

  @Override
  public void travelTo(double x, double y) {
    // TODO
  }
}
