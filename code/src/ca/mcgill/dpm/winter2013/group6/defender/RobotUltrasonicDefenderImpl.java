package ca.mcgill.dpm.winter2013.group6.defender;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

import lejos.nxt.UltrasonicSensor;
import lejos.util.Delay;
import ca.mcgill.dpm.winter2013.group6.bluetooth.Transmission;
import ca.mcgill.dpm.winter2013.group6.navigator.Navigator;

/**
 * An implementation of a {@link RobotUltrasonicDefender}.
 * 
 * @author Alex Selesse
 * 
 */
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

    List<Integer> differences = getBadPoints(firstCalibration, secondCalibration);

    if (differences.size() > 2) {
      differences = getLongestSequence(differences);
    }

    if (differences.size() > 0) {
      navigator.turnTo(-90, 200, 100);
      navigator.turnTo(chooseWhereToTurnBasedOnDifferences(differences));
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

    // start a thread to write the file because we don't want to stall
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
          // This is just debug stuff, so we don't mind exceptions
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

  private List<Integer> getLongestSequence(List<Integer> differences) {
    List<List<Integer>> sequences = getAllSequences(differences);

    int maxLength = 0;
    int biggestSequenceIndex = 0;
    int i = 0;
    for (List<Integer> sequence : sequences) {
      if (sequence.size() > maxLength) {
        biggestSequenceIndex = i;
        maxLength = sequence.size();
      }
      i++;
    }

    return sequences.get(biggestSequenceIndex);
  }

  private List<List<Integer>> getAllSequences(List<Integer> differences) {
    List<List<Integer>> sequences = new ArrayList<List<Integer>>();

    List<Integer> sequence = new ArrayList<Integer>();
    for (int i = 0; i < differences.size(); i++) {
      if (i + 2 > differences.size()) {
        sequence.add(differences.get(i));
        sequences.add(sequence);
        break;
      }
      sequence.add(differences.get(i));
      if (differences.get(i + 1) - differences.get(i) != 1) {
        sequences.add(sequence);
        sequence = new ArrayList<Integer>();
      }
    }
    return sequences;
  }

}
