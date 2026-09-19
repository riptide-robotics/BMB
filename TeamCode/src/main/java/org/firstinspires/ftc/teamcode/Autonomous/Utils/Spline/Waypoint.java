package org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;

import Pose2D;

public class Waypoint extends EditablePose2D {
    /** This is the Waypoint class used for C1 Uniform Cubic Hermite Splines.
     *
     * Each waypoint stores a position and a velocity vector in a Hermite spline.
     * These waypoints will also add a goal velocity and heading for the robot.
     *
     * @param
     *
    */
    private double goalVelocity; // from 0 to 1
    private double velocityVectorx; //the velocity vector

    public Waypoint() {
        super(0, 0, Math.toRadians(90), DistanceUnit.CM);
        goalVelocity
    }
}
