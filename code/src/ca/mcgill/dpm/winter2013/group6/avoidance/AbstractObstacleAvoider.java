package ca.mcgill.dpm.winter2013.group6.avoidance;

import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public abstract class AbstractObstacleAvoider implements ObstacleAvoider {
  protected Odometer odometer;

  public AbstractObstacleAvoider(Odometer odometer) {
    this.odometer = odometer;
  }
}