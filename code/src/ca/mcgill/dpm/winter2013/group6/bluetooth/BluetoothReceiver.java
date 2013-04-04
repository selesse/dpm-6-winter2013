package ca.mcgill.dpm.winter2013.group6.bluetooth;

/**
 * Bluetooth thread that hosts a {@link BluetoothConnection}.
 * 
 * @author Alex Selesse
 * 
 */
public class BluetoothReceiver implements Runnable {
  private Transmission transmission;

  @Override
  public void run() {
    BluetoothConnection connection = new BluetoothConnection();
    transmission = connection.getTransmission();
  }

  public Transmission getTransmission() {
    return transmission;
  }
}
