package ca.mcgill.dpm.winter2013.group6.defender;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;
import ca.mcgill.dpm.winter2013.group6.bluetooth.Transmission;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;

public class RobotUltrasonicDefenderImpl implements RobotUltrasonicDefender {
  private Navigator navigator;
  private UltrasonicSensor ultrasonicSensor;
  private Transmission transmission;
  private final int timesToMeasure = 20;
  private final int measureThreshold = 10;
  private int calibrationTime = 1;

  public RobotUltrasonicDefenderImpl(Navigator navigator, UltrasonicSensor ultrasonicSensor,
      Transmission transmission) {
    this.navigator = navigator;
    this.ultrasonicSensor = ultrasonicSensor;
    this.transmission = transmission;
  }

  @Override
  public void run() {
    // navigator.travelTo(Coordinate.getCoordinateFromBlock(5, 10 -
    // transmission.getDefenderZoneDimension2()));
    int[] firstCalibration = calibrate();
    Delay.msDelay(1000);
    int[] secondCalibration = calibrate();

    List<Integer> difference = getBadPoints(firstCalibration, secondCalibration);

    if (difference.size() > 0) {
      navigator.turnTo(-90, 200, 100);
      navigator.turnTo(chooseWhereToTurnBasedOnDifferences(difference));
    }
  }

  @Override
  public int[] calibrate() {
    final int[] measurements = new int[timesToMeasure];

    navigator.turnTo(-90, 200, 100);
    Delay.msDelay(250);

    for (int i = 0; i < (timesToMeasure - 1); i++) {
      navigator.turnTo((180 / (timesToMeasure - 1)), 200, 100);
      measurements[i] = ultrasonicSensor.getDistance();
      Delay.msDelay(700);
    }

    navigator.turnTo(-90, 200, 100);

    new Thread(new Runnable() {
      @Override
      public void run() {
        try {
          File file = new File("measurements " + calibrationTime++ + ".txt");
          FileOutputStream fos = new FileOutputStream(file);
          for (int i = 0; i < measurements.length; i++) {
            fos.write(("" + measurements[i] + "\n").getBytes());
          }
          fos.flush();
          fos.close();
        }
        catch (Exception e) {
          // This is just debug stuff, so don't worry about it...
        }
      }
    }).start();

    return measurements;
  }

  private double chooseWhereToTurnBasedOnDifferences(List<Integer> difference) {
    int average = difference.size() / 2;

    return (180 / (timesToMeasure - 1) * difference.get(average));
  }

  private List<Integer> getBadPoints(int[] first, int[] second) {
    List<Integer> differences = new ArrayList<Integer>();
    for (int i = 0; i < timesToMeasure; i++) {
      if (second[i] - first[i] > measureThreshold) {
        differences.add(i);
      }
    }

    return differences;
  }
}
