package ca.mcgill.dpm.winter2013.group6.avoidance;

import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.util.PlayingField;

/**
 * Abstract implementation of {@link ObstacleAvoider}, providing the base
 * functionality an obstacle avoider should. This class contains a bit of the
 * logic associated with a playing field. Specifically, it can handle boundary
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

  @Override
  public double getBoundaryBasedTurningAngle(double turningAngle) {
    if (isNearLeftBoundary()) {
      if (robotIsMovingUp()) {
        turningAngle = Math.abs(turningAngle);
      }
      else {
        turningAngle = -Math.abs(turningAngle);
      }
    }
    else if (isNearRightBoundary()) {
      if (robotIsMovingUp()) {
        turningAngle = -Math.abs(turningAngle);
      }
      else {
        turningAngle = Math.abs(turningAngle);
      }
    }

    return turningAngle;
  }

  private boolean robotIsMovingUp() {
    return (odometer.getY() - navigator.getCurrentHeading().getY()) < 0;
  }

  public boolean isNearLeftBoundary() {
    return odometer.getX() <= 20 && navigator.getCurrentHeading().getX() <= 20;
  }

  public boolean isNearRightBoundary() {
    double rightBoundary = playingField.getHorizontalLength() - 20;

    return odometer.getX() >= rightBoundary
        && navigator.getCurrentHeading().getX() >= rightBoundary;
  }
}