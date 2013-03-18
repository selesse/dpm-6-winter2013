package ca.mcgill.dpm.winter2013.group6.avoidance;

import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

/**
 * Abstract implementation of {@link ObstacleAvoider}, providing the base
 * functionality an obstacle avoider should.
 * 
 * @author Alex Selesse
 * 
 */
public abstract class AbstractObstacleAvoider implements ObstacleAvoider {
  protected Odometer odometer;
  protected Navigator navigator;

  public AbstractObstacleAvoider(Odometer odometer, Navigator navigator) {
    this.odometer = odometer;
    this.navigator = navigator;
  }

  @Override
  public void run() {
    avoidObstacles();
  }
}