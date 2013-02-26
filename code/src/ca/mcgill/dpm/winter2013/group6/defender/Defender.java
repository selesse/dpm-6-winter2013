package ca.mcgill.dpm.winter2013.group6.defender;

import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;

/**
 * Defender version of the robot. Extension of the {@link Navigator} class as it
 * may move around.
 *
 * @author Alex Selesse
 *
 */
public interface Defender extends Navigator {
  /**
   * The method responsible for doing the defending. This will probably call for
   * the robot to lift its arm up to defend the goal.
   */
  public void defend();
}
