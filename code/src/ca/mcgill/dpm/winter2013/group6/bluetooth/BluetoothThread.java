package ca.mcgill.dpm.winter2013.group6.bluetooth;

/**
 * Bluetooth thread that hosts a {@link BluetoothConnection}.
 *
 * @author Alex Selesse
 *
 */
public class BluetoothThread implements Runnable {
  @Override
  public void run() {
    BluetoothConnection connection = new BluetoothConnection();
    connection.printTransmission();
  }
}
