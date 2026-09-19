package org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;

public class Waypoint extends EditablePose2D {
    /** This is the Waypoint class used for C1 Uniform Cubic Hermite Splines.
     *
     * Each waypoint stores a position and a velocity vector in a Hermite spline.
     * These waypoints will also add a goal velocity and heading for the robot.
     *
     * @param
     *
    */
    private double goalSpeed; // from 0 to 1
    private double velocityVectorx; //the velocity vector

    public Waypoint() {
        super(0, 0, Math.toRadians(90), DistanceUnit.CM);
        goalSpeed = -1;
    }

    public Waypoint(double x, double y, double h) {
        super(x, y, Math.toRadians(h), DistanceUnit.CM);
        goalSpeed = -1;
    }

    public Waypoint(double x, double y, double h, double goalSpeed) {
        super(x, y, Math.toRadians(h), DistanceUnit.CM);
        this.goalSpeed = goalSpeed;
    }

    public Waypoint(double x, double y, double h, double goalSpeed, DistanceUnit units) {
        super(x, y, Math.toRadians(90), units);
        this.goalSpeed = goalSpeed;
    }
}
