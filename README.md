DPM - Group 6 Repository
========================

### Software Guidelines

Every component that we want to implement is in its own separate package:

* `avoidance` : The avoidance package is responsible for avoiding any
  obstacles.
* `bluetooth` : The bluetooth package is responsible for the communication to
  the robot.
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

### Components and Packages

Due to the separation of the components, every discrete component (i.e.
package) of our robot has *one* method associated with it. This means that if
you're every looking for the code for a particular component, just go look at
that particular method in whatever class you're interested in.

* `avoidance` : ObstacleAvoider : avoidObstacles()
* `launcher` : BallLauncher : throwBall()
* `localization` : Localizer : localize()
* `navigator` : Navigator : travelTo(), travelStraight(), turnTo()
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
