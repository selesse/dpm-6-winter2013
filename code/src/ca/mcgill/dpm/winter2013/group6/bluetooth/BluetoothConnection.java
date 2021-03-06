/*
 * @author Sean Lawlor
 * @date November 3, 2011
 * @class ECSE 211 - Design Principle and Methods
 */
package ca.mcgill.dpm.winter2013.group6.bluetooth;

import java.io.DataInputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.comm.Bluetooth;
import lejos.nxt.comm.NXTConnection;

/**
 * This class initializes a Bluetooth connection, waits for the data and then
 * allows access to the data after closing the BT channel.
 * 
 * It should be used by calling the constructor which will automatically wait
 * for data without any further user command
 * 
 * Then, once completed, it will allow access to an instance of the Transmission
 * class which has access to all of the data needed
 */
public class BluetoothConnection {
  private Transmission transmission;

  public BluetoothConnection() {
    LCD.clear();
    LCD.drawString("Starting BT connection", 0, 0);

    NXTConnection conn = Bluetooth.waitForConnection();
    DataInputStream dis = conn.openDataInputStream();
    LCD.drawString("Opened DIS", 0, 1);
    this.transmission = ParseTransmission.parse(dis);
    LCD.drawString("Finished Parsing", 0, 2);
    try {
      dis.close();
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    conn.close();
  }

  public Transmission getTransmission() {
    return this.transmission;
  }

  public void printTransmission() {
    try {
      LCD.clear();
      LCD.drawString(("Transmitted Values"), 0, 0);
      LCD.drawString("Start: " + transmission.getStartingCorner().toString(), 0, 1);
      LCD.drawString("Role: " + transmission.getRole().toString(), 0, 2);
      LCD.drawString("bx: " + transmission.getBallDispenserX(), 0, 3);
      LCD.drawString("by: " + transmission.getBallDispenserY(), 0, 4);
      LCD.drawString("w1: " + transmission.getDefenderZoneDimension1(), 0, 5);
      LCD.drawString("w2: " + transmission.getDefenderZoneDimension2(), 0, 6);
      LCD.drawString("d1: " + transmission.getForwardLineDistanceFromGoal(), 0, 7);
    }
    catch (NullPointerException e) {
      LCD.drawString("Bad Trans", 0, 8);
    }
  }

}
