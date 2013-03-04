package ca.mcgill.dpm.winter2013.group6.avoidance;

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

  public AbstractObstacleAvoider(Odometer odometer) {
    this.odometer = odometer;
  }
}