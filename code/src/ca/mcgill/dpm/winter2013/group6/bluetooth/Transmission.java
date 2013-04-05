/**
 * @author Sean Lawlor, Stepan Salenikovich
 * @date November 3, 2011
 * @class ECSE 211 - Design Principle and Methods
 */
package ca.mcgill.dpm.winter2013.group6.bluetooth;

/**
 * Skeleton class to hold data types needed for final project
 * 
 * Simply all variables so can be accessed with
 * 
 * <pre>
 * Transmission t = new Transmission();
 * int fx = t.getFx();
 * </pre>
 * 
 * and so on...
 * 
 * Also the role is an enum, converted from the char transmitted. (It should
 * never be Role.NULL)
 */

public class Transmission {
  /**
   * The role, Defender or Attacker
   */
  private PlayerRole role;
  /**
   * Ball dispenser X tile position (formerly bx)
   */
  private int ballDispenserX;
  /**
   * Ball dispenser Y tile position (formerly by)
   */
  private int ballDispenserY;
  /**
   * Defender zone dimension 1 (formerly w1)
   */
  private int defenderZoneDimension1;
  /**
   * Defender zone dimension 2 (formerly w2)
   */
  private int defenderZoneDimension2;
  /**
   * Forward line distance from goal (formerly d1)
   */
  private int forwardLineDistanceFromGoal;
  /**
   * starting corner, 1 through 4
   */
  private StartCorner startingCorner;

  public PlayerRole getRole() {
    return role;
  }

  public void setRole(PlayerRole role) {
    this.role = role;
  }

  public int getBallDispenserX() {
    return ballDispenserX;
  }

  public void setBallDispenserX(int ballDispenserX) {
    this.ballDispenserX = ballDispenserX;
  }

  public int getBallDispenserY() {
    return ballDispenserY;
  }

  public void setBallDispenserY(int ballDispenserY) {
    this.ballDispenserY = ballDispenserY;
  }

  /**
   * Formerly known as w1: the defender zone dimension #1.
   * 
   * @return
   */
  public int getDefenderZoneDimension1() {
    return defenderZoneDimension1;
  }

  public void setDefenderZoneDimension1(int defenderZoneDimension1) {
    this.defenderZoneDimension1 = defenderZoneDimension1;
  }

  /**
   * Formerly known as w2: the defender zone dimension #2.
   * 
   * @return
   */
  public int getDefenderZoneDimension2() {
    return defenderZoneDimension2;
  }

  public void setDefenderZoneDimension2(int defenderZoneDimension2) {
    this.defenderZoneDimension2 = defenderZoneDimension2;
  }

  /**
   * Formerly known as d1: the forward line distance from the goal.
   * 
   * @return
   */
  public int getForwardLineDistanceFromGoal() {
    return forwardLineDistanceFromGoal;
  }

  public void setForwardLineDistanceFromGoal(int forwardLineDistanceFromGoal) {
    this.forwardLineDistanceFromGoal = forwardLineDistanceFromGoal;
  }

  /**
   * The {@link StartCorner} - 1 through 4.
   * 
   * @return
   */
  public StartCorner getStartingCorner() {
    return startingCorner;
  }

  public void setStartingCorner(StartCorner startingCorner) {
    this.startingCorner = startingCorner;
  }

}
