package ca.mcgill.dpm.winter2013.group6.launcher;

import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.NXTRegulatedMotor;

/**
 * {@link Runnable} class that, when started, will launch the ball into the
 * goal.
 *
 * @author Alex Selesse
 *
 */
public class BallLauncher implements Runnable {

  /**
   * Distance from the target that we'll be shooting from.
   */
  private double distanceFromTarget;
  /**
   * One motor responsible for doing the launching of the ball.
   */
  private NXTRegulatedMotor motor;

  /**
   * Start a ball launcher given one motor and the distance from the target.
   *
   * @param motor
   *          The motor that will be propelling the ball.
   * @param distanceFromTarget
   *          The distance from the target that we're launching the ball at.
   */
  public BallLauncher(NXTRegulatedMotor motor, double distanceFromTarget) {
    this.motor = motor;
    this.distanceFromTarget = distanceFromTarget;
  }

  @Override
  public void run() {
    // stop the motors, find the distance from which to bounce
    motor.stop();
    throwBall(distanceFromTarget);
  }
  
  public void launchTest(){
    int angle;
    int acc=4500;
    int speed=1000000000;
    motor.setAcceleration(400);
    motor.setSpeed(speed);

    for(int i =0;i<4;i++){

        //set the different cases here
        angle = 180+i*3;
        acc = 4500;

        motor.rotateTo(-angle,true);
        int buttonChoice;
        //wait for the button press and also displays the launching info
        do {
            LCD.clear();
            LCD.drawString("start:", 0, 0);
            LCD.drawInt(angle, 0, 1);
            LCD.drawString("acc:", 0, 2);
            LCD.drawInt(acc, 0, 3);

            buttonChoice=Button.waitForAnyPress();
        }
        while(buttonChoice !=Button.ID_ENTER &&buttonChoice!=Button.ID_ESCAPE);

        if (buttonChoice==Button.ID_ESCAPE){
            System.exit(0);
        }
        motor.setAcceleration(acc);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //not sure if u guys have an odometer class
        //motor.rotate((int) (-1*odo.getTheta()),false;
        motor.rotate(angle);

    }
}
  private void throwBall(double bounceDistance) {
    // rotate it 180 degrees, at a high speed and high acceleration
    int rotateAngle = 180;
    int rotateSpeed = 12000;
    int accelerationSpeed = 7000;

    motor.setAcceleration(accelerationSpeed);
    motor.setSpeed(rotateSpeed);
    motor.rotate(rotateAngle, false);

    // make the thread sleep, then bring it back to its starting point, slowly
    try {
      Thread.sleep(1250);

      motor.stop();
      motor.setAcceleration(3000);
      motor.setSpeed(200);
      motor.rotate(-rotateAngle, false);
    }
    catch (InterruptedException e) {
      // nothing will interrupt this
    }
  }
}
