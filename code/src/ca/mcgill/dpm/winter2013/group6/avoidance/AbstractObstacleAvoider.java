package ca.mcgill.dpm.winter2013.group6.avoidance;

import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.util.PlayingField;

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
  protected PlayingField playingField;
  protected boolean isCurrentlyAvoiding;

  public AbstractObstacleAvoider(Odometer odometer, Navigator navigator, PlayingField playingField) {
    this.odometer = odometer;
    this.navigator = navigator;
    this.playingField = playingField;
  }

  @Override
  public void run() {
    while (true) {
      avoidObstacles();
    }
  }

  @Override
  public boolean isCurrentlyAvoiding() {
    return isCurrentlyAvoiding;
  }
}