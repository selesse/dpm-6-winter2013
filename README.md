DPM - Group 6 Repository
========================

### Software Guidelines

Every component that we want to implement is in its own separate package:

* `attacker` : The attacking robot that will fetch the ball, navigate
  somewhere, and throw the ball into the objective.
* `avoidance` : The avoidance package is responsible for avoiding any
  obstacles.
* `bluetooth` : The bluetooth package is responsible for the communication to
  the robot.
* `defender` : The defender package is responsible for the defense part of the
  competition.
* `launcher` : The package responsible for doing the launching of the ball.
* `localization` : The package responsible for doing the localization.
  Includes a LightLocalizer and an UltrasonicLocalizer.
* `navigator` : The package responsible for robot navigation. Includes
  navigators that can operate with obstacle or without obstacle support.
* `odometer` : The package responsible for the robot odometry.
* `tests` : The package responsible for running all the tests. Every test in
  here should either implement or extend a class from another package.
* `util` : The package responsible for miscellaneous, utility classes. One
  such example is the Robot class that contains information specific to our
  robot.

### How to Write a Test / Test a Component

Every package has an interface:

* `attacker` : RobotAttacker
* `avoidance` : ObstacleAvoider
* `defender` : RobotDefender
* `launcher` : BallLauncher
* `localization` : Localizer
* `navigator` : Navigator
* `odometer` : Odometer

To test packages that don't have abstract classes:

    public class RobotAttackerTest implements RobotAttacker {
      @Override
      public void throwBall() {
        // TODO: your implementation of the test here
      }
    }

If the package *does* have an abstract class:

    public class RobotAttackerTest extends AbstractRobotAttacker {
      public RobotAttackerTest( /* constructor arguments */ ) {
        super( /* constructor arguments */ );
      }

      @Override
      public void throwBall() {
        // TODO: your implementation here
      }
    }

Ideally, you'll want to use an abstract class if you have any common logic
between implementations. For an example, look at BallLauncherTest.

### How to Test Your Tests

`Main.java`:

    public class Main {
      public static void main(String[] args) {
        // code asking to select left or right arrow

        if (buttonChoice == Button.ID.LEFT) {
          // initialize the attacker, taking advantage of polymorphism
          RobotAttacker attacker = new RobotAttackerTest( /*constructor*/ );

          // start the thread
          attacker.run();
        }
      }
    }
