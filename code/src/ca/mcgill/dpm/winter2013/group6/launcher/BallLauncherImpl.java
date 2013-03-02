package ca.mcgill.dpm.winter2013.group6.launcher;

import lejos.nxt.NXTRegulatedMotor;

/**
 * An implementation of the {@link BallLauncher} interface.
 *
 * @author Alex Selesse
 *
 */
public class BallLauncherImpl extends AbstractBallLauncher {
  public BallLauncherImpl(NXTRegulatedMotor motor, double distanceFromTarget) {
    super(motor, distanceFromTarget);
  }

  @Override
  public void throwBall(double bounceDistance) {
    // rotate it 180 degrees, at a high speed and high acceleration
    int rotateAngle = 180;
    int rotateSpeed = 12000;
    int accelerationSpeed = 7000;

    motor.setAcceleration(accelerationSpeed);
    motor.setSpeed(rotateSpeed);
    motor.rotate(rotateAngle, false);

    // make the thread sleep, then bring it back to its starting point, slowly
    try {
      Thread.sleep(1250);

      motor.stop();
      motor.setAcceleration(3000);
      motor.setSpeed(200);
      motor.rotate(-rotateAngle, false);
    }
    catch (InterruptedException e) {
      // nothing will interrupt this
    }
  }
}
