package org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline;

import androidx.annotation.NonNull;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;

public class Waypoint extends EditablePose2D {
    /** This is the Waypoint class used for C1 Uniform Cubic Hermite Splines.
     *
     * Each waypoint stores a position and a velocity vector in a Hermite spline.
     * These waypoints will also add a goal velocity and heading for the robot.
     *
    */
    private double goalSpeed; // from 0 to 1
    private double velocityVectorX; //the velocity vector
    private double velocityVectorY;

    public Waypoint() {
        super(0, 0, Math.toRadians(90), DistanceUnit.CM);
        velocityVectorX = 0;
        velocityVectorY = 0;
        goalSpeed = -1;
    }

    public Waypoint(double x, double y, double h) {
        super(x, y, Math.toRadians(h), DistanceUnit.CM);
        velocityVectorX = 0;
        velocityVectorY = 0;
        goalSpeed = -1;
    }

    public Waypoint(double x, double y, double h, double velocityX, double velocityY) {
        super(x, y, Math.toRadians(h), DistanceUnit.CM);
        velocityVectorX = velocityX;
        velocityVectorY = velocityY;
        goalSpeed = -1;
    }

    public Waypoint(double x, double y, double h, double velocityX, double velocityY, double goalSpeed) {
        super(x, y, Math.toRadians(h), DistanceUnit.CM);
        this.goalSpeed = goalSpeed;
        velocityVectorX = velocityX;
        velocityVectorY = velocityY;
    }

    public Waypoint(double x, double y, double h, double velocityX, double velocityY, double goalSpeed, DistanceUnit units) {
        /** The constructor of the Waypoint class.
         *
         * The values -1 or 0 mean that that value of the waypoint is mathematically defined
         * based on other points in the spline.
         *
         * @param x         waypoint x coord
         * @param y         waypoint y coord
         * @param h         waypoint heading
         * @param velocityX x of the velocity of waypoint (of is defined by other points)
         * @param velocityY y of the velocity of waypoint (0 is defined by other points)
         * @param goalSpeed goal speed of the waypoint (-1 means none)
         * @param units     type of units used
         *
         */
        super(x, y, Math.toRadians(h), units);
        this.goalSpeed = goalSpeed;
        velocityVectorX = velocityX;
        velocityVectorY = velocityY;
    }

    public double getGoalSpeed() {
        return goalSpeed;
    }

    public void setGoalSpeed(double goalSpeed) {
        this.goalSpeed = goalSpeed;
    }

    public double getVelocityVectorX() {
        return velocityVectorX;
    }

    public void setVelocityVectorX(double velocityVectorX) {
        this.velocityVectorX = velocityVectorX;
    }

    public double getVelocityVectorY() {
        return velocityVectorY;
    }

    public void setVelocityVectorY(double velocityVectorY) {
        this.velocityVectorY = velocityVectorY;
    }

    public void setVeloctiyVector(double x, double y) {
        this.velocityVectorX = x;
        this.velocityVectorY = y;
    }

    public double[] getVelocityVector() {
        return new double[]{velocityVectorX, velocityVectorY};
    }

    public boolean fullyDefined() {
        return velocityVectorX != 0 && velocityVectorY != 0;
    }

    @NonNull
    @Override
    public String toString() {
        return "Waypoint{" +
                "x=" + getX(DistanceUnit.INCH) + " in, " +
                "y=" + getY(DistanceUnit.INCH) + " in, " +
                "heading=" + Math.toDegrees(getH()) + "°, " +
                "velX=" + this.velocityVectorX + ", " +
                "velY=" + this.velocityVectorY + ", " +
                "goalVelocity=" + this.goalSpeed + " in/sec, " +
                '}';
    }

}
