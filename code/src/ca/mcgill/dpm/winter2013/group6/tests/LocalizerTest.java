package ca.mcgill.dpm.winter2013.group6.tests;

import lejos.nxt.LightSensor;
import lejos.nxt.UltrasonicSensor;
import ca.mcgill.dpm.winter2013.group6.localization.AbstractLocalizer;
import ca.mcgill.dpm.winter2013.group6.localization.LightLocalizer;
import ca.mcgill.dpm.winter2013.group6.localization.Localizer;
import ca.mcgill.dpm.winter2013.group6.localization.USLocalizer;
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
    Localizer tester = new LightLocalizer(odometer, navigator, corner, ls);
    tester.doLocalize();
  }

  public void USLocalizerTest() {
    Localizer tester = new USLocalizer(odometer, us, corner, navigator);
    tester.doLocalize();
  }

  @Override
  public void doLocalize() {
    USLocalizerTest();
    lightLocalizerTest();
    int destX = (corner == 2 || corner == 3) ? (int) 30.5 * 10 : 0;
    int destY = (corner == 3 || corner == 4) ? (int) 30.5 * 10 : 0;
    navigator.travelTo(destX, destY);

  }
}
