package ca.mcgill.dpm.winter2013.group6.launcher;

import lejos.nxt.NXTRegulatedMotor;

/**
 * An implementation of the {@link BallLauncher} interface.
 * 
 * @author Alex Selesse
 * 
 */
public class BallLauncherImpl extends AbstractBallLauncher {
  public BallLauncherImpl(NXTRegulatedMotor ballThrowingMotor, double distanceFromTarget) {
    super(ballThrowingMotor, distanceFromTarget);
  }

  @Override
  public void throwBall(double distanceFromTarget) {
    // rotate it 180 degrees, at a high speed and high acceleration
    int rotateAngle = 122;
    int rotateSpeed = 5000;
    int accelerationSpeed = 5000;

    ballThrowingMotor.setAcceleration(accelerationSpeed);
    ballThrowingMotor.setSpeed(rotateSpeed);
    ballThrowingMotor.rotate(rotateAngle, false);

    // make the thread sleep, then bring it back to its starting point, slowly
    try {
      Thread.sleep(1250);

      ballThrowingMotor.stop();
      ballThrowingMotor.setAcceleration(3000);
      ballThrowingMotor.setSpeed(200);
      ballThrowingMotor.rotate(-rotateAngle, false);
    }
    catch (InterruptedException e) {
      // nothing should interrupt this
    }
    ballThrowingMotor.flt(false);
  }
}
