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
  protected boolean isRunning;
  protected boolean isAvoiding;

  public AbstractObstacleAvoider(Odometer odometer, Navigator navigator) {
    this.odometer = odometer;
    this.navigator = navigator;
    this.isRunning = true;
  }

  @Override
  public void run() {
    while (isRunning) {
      avoidObstacles();
    }
  }

  @Override
  public void setRunning(boolean avoiding) {
    this.isRunning = avoiding;
  }

  @Override
  public boolean isAvoiding() {
    return isAvoiding;
  }
}