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
  protected boolean currentlyAvoiding;

  public AbstractObstacleAvoider(Odometer odometer, Navigator navigator) {
    this.odometer = odometer;
    this.navigator = navigator;
    this.currentlyAvoiding = true;
  }

  @Override
  public void run() {
    while (currentlyAvoiding) {
      avoidObstacles();
    }
  }

  @Override
  public void setAvoiding(boolean avoiding) {
    this.currentlyAvoiding = avoiding;
  }
}