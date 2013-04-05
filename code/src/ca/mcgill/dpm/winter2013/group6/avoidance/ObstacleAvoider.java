package ca.mcgill.dpm.winter2013.group6.avoidance;

import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;

/**
 * The obstacle avoidance thread responsible for avoiding collisions. Our
 * preliminary designs suggest that we'll be using bumpers to navigate through
 * the obstacles.
 * 
 * @author Alex Selesse
 * 
 */
public interface ObstacleAvoider extends Runnable {
  /**
   * Avoid obstacles by performing a check every few seconds, will need to have
   * some measure of control over the {@link Navigator}.
   */
  public void avoidObstacles();

  /**
   * Verify whether whether we are currently avoiding.
   * 
   * @return True if we're performing obstacle avoidance maneuvers, otherwise
   *         false.
   */
  public boolean isCurrentlyAvoiding();

}
