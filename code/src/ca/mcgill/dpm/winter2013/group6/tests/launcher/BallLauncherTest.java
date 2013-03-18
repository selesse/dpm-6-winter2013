package ca.mcgill.dpm.winter2013.group6.tests.launcher;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.NXTRegulatedMotor;
import ca.mcgill.dpm.winter2013.group6.launcher.AbstractBallLauncher;

public class BallLauncherTest extends AbstractBallLauncher {

  public BallLauncherTest(NXTRegulatedMotor motor, double distanceFromTarget) {
    super(motor, distanceFromTarget);
  }

  @Override
  public void throwBall(double distanceFromTarget) {
    int angleOne = 135;
    int angleTwo = -75;
    int acc = 750;
    int speed = 100000;
    motor.getTachoCount();
    motor.setAcceleration(400);
    motor.setSpeed(speed);

    for (int i = 0; i == 0;) {

      // set the different cases here

      acc = 4500;

      motor.rotateTo(angleOne, true);
      try {
        Thread.sleep(1500);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
      motor.stop();
      int buttonChoice;
      // wait for the button press and also displays the launching info
      do {
        LCD.clear();
        LCD.drawString("start:", 0, 0);
        LCD.drawInt(angleOne, 0, 1);
        LCD.drawString("acc:", 0, 2);
        LCD.drawInt(acc, 0, 3);

        buttonChoice = Button.waitForPress();
      }
      while (buttonChoice != Button.ID_ENTER && buttonChoice != Button.ID_ESCAPE);

      if (buttonChoice == Button.ID_ESCAPE) {
        System.exit(0);
      }
      motor.setSpeed(speed);
      motor.setAcceleration(acc);

      try {
        Thread.sleep(1000);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
      motor.rotate(angleTwo);
      motor.setAcceleration(400);
    }
  }

}
