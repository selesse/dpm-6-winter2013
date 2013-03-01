package ca.mcgill.dpm.winter2013.group6.launcher;

import lejos.nxt.NXTRegulatedMotor;

/**
 * Abstract ball launcher class created to specify the base constructor that
 * implementations of BallLauncher will adhere to. Useful for easily creating
 * test classes.
 *
 * @author Alex Selesse
 *
 */
public abstract class AbstractBallLauncher implements BallLauncher {
  /**
   * One motor responsible for doing the launching of the ball.
   */
  protected NXTRegulatedMotor motor;
  /**
   * Distance from the target that we'll be shooting from.
   */
  protected double distanceFromTarget;

  /**
   * Initialize a ball launcher given one motor and the distance from the
   * target.
   *
   * @param motor
   *          The motor that will be propelling the ball.
   * @param distanceFromTarget
   *          The distance from the target that we're launching the ball at.
   */
  public AbstractBallLauncher(NXTRegulatedMotor motor, double distanceFromTarget) {
    this.motor = motor;
    this.distanceFromTarget = distanceFromTarget;
  }

  @Override
  public void run() {
    motor.stop();
    throwBall(distanceFromTarget);
  }
}
