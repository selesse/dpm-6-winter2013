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
  public void run() {
    // explicitly stop the motor, just in case
    for (int i = 0; i < 5; i++) {
      super.run();
      try {
        Thread.sleep(3450);
      }
      catch (InterruptedException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    }
  }

  @Override
  public void throwBall(double distanceFromTarget) {
    // rotate it 180 degrees, at a high speed and high acceleration
    int rotateAngle = 130;
    int rotateSpeed = 2000;
    int accelerationSpeed = 2000;

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
  }
}
