package ca.mcgill.dpm.winter2013.group6.tests;

import ca.mcgill.dpm.winter2013.group6.localization.AbstractLocalizer;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;
import ca.mcgill.dpm.winter2013.group6.odometer.Odometer;

public class LocalizerTest extends AbstractLocalizer {

  public LocalizerTest(Odometer odometer, Navigator navigator) {
    super(odometer, navigator);
  }

  @Override
  public void run() {
    USLocalizerTest();
  }

  public void lightLocalizerTest() {
  }

  public void USLocalizerTest() {
  }

  @Override
  public void doLocalize() {
    // TODO Auto-generated method stub

  }
}
