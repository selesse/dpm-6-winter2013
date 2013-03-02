package ca.mcgill.dpm.winter2013.group6.launcher;

/**
 * BallLauncher interface - define methods that BallLaunchers will need to
 * implement, such as throwing the ball.
 *
 * @author Alex Selesse
 *
 */
public interface BallLauncher extends Runnable {
  public void throwBall(double bounceDistance);
}
