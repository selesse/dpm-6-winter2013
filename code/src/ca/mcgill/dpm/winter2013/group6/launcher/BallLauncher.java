package ca.mcgill.dpm.winter2013.group6.launcher;

import lejos.nxt.NXTRegulatedMotor;

/**
 * {@link Runnable} class that, when started, will launch the ball into the
 * goal.
 *
 * @author Alex Selesse
 *
 */
public class BallLauncher implements Runnable {

  /**
   * Distance from the target that we'll be shooting from.
   */
  private double distanceFromTarget;
  /**
   * One motor responsible for doing the launching of the ball.
   */
  private NXTRegulatedMotor motor;

  /**
   * Start a ball launcher given one motor and the distance from the target.
   *
   * @param motor
   *          The motor that will be propelling the ball.
   * @param distanceFromTarget
   *          The distance from the target that we're launching the ball at.
   */
  public BallLauncher(NXTRegulatedMotor motor, double distanceFromTarget) {
    this.motor = motor;
    this.distanceFromTarget = distanceFromTarget;
  }

  @Override
  public void run() {
    // stop the motors, find the distance from which to bounce
    motor.stop();
    throwBall(distanceFromTarget);
  }

  private void throwBall(double bounceDistance) {
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
