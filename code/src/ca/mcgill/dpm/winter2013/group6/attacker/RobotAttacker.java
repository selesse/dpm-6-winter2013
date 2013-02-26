package ca.mcgill.dpm.winter2013.group6.attacker;

import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;

/**
 * Robot attacker class that is responsible for navigating, receiving balls from
 * the dispenser, navigating to the target, and throwing the ball into the
 * target.
 *
 * @author Alex Selesse
 *
 */
public interface RobotAttacker extends Navigator {

  /**
   * Throws the ball, presumably letting it bouncing once on the ground, then
   * entering the target.
   */
  public void throwBall();
}
