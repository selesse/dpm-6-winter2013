package ca.mcgill.dpm.winter2013.group6.localization;

/**
 * Localization interface responsible for determining where we are.
 * 
 * @author Alex Selesse, Arthur Kam
 * 
 */
public interface Localizer extends Runnable {

  /**
   * Do the localization.
   */
  public void localize();
}
