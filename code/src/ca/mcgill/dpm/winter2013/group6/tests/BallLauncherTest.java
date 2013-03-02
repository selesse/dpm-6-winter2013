package ca.mcgill.dpm.winter2013.group6.tests;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.NXTRegulatedMotor;
import ca.mcgill.dpm.winter2013.group6.launcher.AbstractBallLauncher;

public class BallLauncherTest extends AbstractBallLauncher {

  public BallLauncherTest(NXTRegulatedMotor motor, double distanceFromTarget) {
    super(motor, distanceFromTarget);
  }

  @Override
  public void run() {
    throwBall(distanceFromTarget);
  }

  @Override
  public void throwBall(double distanceFromTarget) {
    int angle;
    int acc = 4500;
    int speed = 1000000000;
    motor.setAcceleration(400);
    motor.setSpeed(speed);

    for (int i = 0; i < 4; i++) {

      // set the different cases here
      angle = 180 + i * 3;
      acc = 4500;

      motor.rotateTo(-angle, true);
      int buttonChoice;
      // wait for the button press and also displays the launching info
      do {
        LCD.clear();
        LCD.drawString("start:", 0, 0);
        LCD.drawInt(angle, 0, 1);
        LCD.drawString("acc:", 0, 2);
        LCD.drawInt(acc, 0, 3);

        buttonChoice = Button.waitForPress();
      }
      while (buttonChoice != Button.ID_ENTER && buttonChoice != Button.ID_ESCAPE);

      if (buttonChoice == Button.ID_ESCAPE) {
        System.exit(0);
      }
      motor.setAcceleration(acc);

      try {
        Thread.sleep(1000);
      }
      catch (InterruptedException e) {
        e.printStackTrace();
      }
      motor.rotate(angle);
    }
  }

}
