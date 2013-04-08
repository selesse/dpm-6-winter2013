package ca.mcgill.dpm.winter2013.group6.defender;

/**
 * Robot ultrasonic defender. This defender works by continuously calibrating
 * itself. When there's an anomaly in the measurement (i.e. when the enemy robot
 * appears in the field of vision), the robot will face that direction.
 * 
 * @author Alex Selesse
 * 
 */
public interface RobotUltrasonicDefender extends Runnable {

  /**
   * Move a certain amount of degrees in front of you, taking ultrasonic sensor
   * measurements.
   * 
   * @return The ultrasonic sensor values that have been measured.
   */
  int[] calibrate();
}
