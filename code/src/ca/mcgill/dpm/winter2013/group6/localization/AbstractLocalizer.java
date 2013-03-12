package ca.mcgill.dpm.winter2013.group6.localization;

import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;
import ca.mcgill.dpm.winter2013.group6.util.Robot;

public abstract class AbstractLocalizer implements Localizer {
  protected Odometer odometer;
  protected Navigator navigator;
  protected Robot robot;

  public AbstractLocalizer(Odometer odometer, Navigator navigator) {
    this.odometer = odometer;
    this.navigator = navigator;
    this.robot = odometer.getRobot();
  }

}