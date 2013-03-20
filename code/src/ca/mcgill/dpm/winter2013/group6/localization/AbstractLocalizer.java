package ca.mcgill.dpm.winter2013.group6.localization;

import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.util.Robot;

/**
 * The AbstractLocalizer provides a base contructor for other Localizer classes.
 * 
 * @author arthurkam
 */
public abstract class AbstractLocalizer implements Localizer {

  protected Odometer odometer;
  protected Navigator navigator;
  protected Robot robot;
  protected int corner;

  /**
   * Initializes the Localizer using the given parameters
   * 
   * @param odometer
   *          The odometer is used to get the angle and make adjustments to it
   * @param navigator
   *          The navigator is used to control the robot
   * @param corner
   *          A corner number to indicated the starting corner
   */
  public AbstractLocalizer(Odometer odometer, Navigator navigator, int corner) {
    this.odometer = odometer;
    this.navigator = navigator;
    this.robot = odometer.getRobot();
    this.corner = corner;
  }

  @Override
  public void run() {
    localize();
  }

}
