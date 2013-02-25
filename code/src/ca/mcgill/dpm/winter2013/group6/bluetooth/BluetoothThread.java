package ca.mcgill.dpm.winter2013.group6.bluetooth;

public class BluetoothThread extends Thread {
  @Override
  public void run() {
    BluetoothConnection connection = new BluetoothConnection();
    connection.printTransmission();
  }
}
