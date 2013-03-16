package ca.mcgill.dpm.winter2013.group6.tests;

import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.localization.AbstractLocalizer;
import ca.mcgill.dpm.winter2013.group6.localization.LightLocalizer;
import ca.mcgill.dpm.winter2013.group6.localization.Localizer;
import ca.mcgill.dpm.winter2013.group6.localization.UltrasonicLocalizer;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class LocalizerTest extends AbstractLocalizer {
  UltrasonicSensor us;
  LightSensor ls;

  public LocalizerTest(Odometer odometer, Navigator navigator, UltrasonicSensor us, LightSensor ls,
      int corner) {
    super(odometer, navigator, corner);
    this.us = us;
    this.ls = ls;
  }

  @Override
  public void run() {
    USLocalizerTest();
  }

  public void lightLocalizerTest() {
    Localizer tester = new LightLocalizer(odometer, navigator, ls, corner);
    tester.localize();
    navigator.turnTo((corner - 1) * 90 - odometer.getTheta());

  }

  public void USLocalizerTest() {
    Localizer tester = new UltrasonicLocalizer(odometer, navigator, us, corner);
    tester.localize();
  }

  @Override
  public void localize() {
    USLocalizerTest();
    lightLocalizerTest();
    double destX = (corner == 2 || corner == 3) ? 30.5 * 10 : 0;
    double destY = (corner == 3 || corner == 4) ? 30.5 * 10 : 0;

  }
}
