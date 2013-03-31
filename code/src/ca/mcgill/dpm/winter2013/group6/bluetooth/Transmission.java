/*
 * @author Sean Lawlor
 * @date November 3, 2011
 * @class ECSE 211 - Design Principle and Methods
 */
package ca.mcgill.dpm.winter2013.group6.bluetooth;

/*
 * Skeleton class to hold datatypes needed for final project
 *
 * Simply all public variables so can be accessed with
 * Transmission t = new Transmission();
 * int d1 = t.d1;
 *
 * and so on...
 *
 * Also the role is an enum, converted from the char transmitted. (It should never be
 * Role.NULL)
 */

public class Transmission {
  private int goalX;
  private int goalY;

  public void setGoalX(int goalX) {
    this.goalX = goalX;
  }

  public void setGoalY(int goalY) {
    this.goalY = goalY;
  }

  public int getGoalX() {
    return (int) (goalX * 30.5);
  }

  public int getGoalY() {
    return (int) (goalY * 30.5);
  }
}
