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
    private boolean defined = false;

    public Waypoint() {
        super(0, 0, Math.toRadians(90), DistanceUnit.CM);
        this.velocityVectorX = 0;
        this.velocityVectorY = 0;
        this.goalSpeed = -1;

        this.defined = false;
    }

//    public Waypoint(double x, double y, double h) {
//        super(x, y, Math.toRadians(h), DistanceUnit.CM);
//        velocityVectorX = 0;
//        velocityVectorY = 0;
//        goalSpeed = -1;
//    }
//
//    public Waypoint(double x, double y, double h, double velocityX, double velocityY) {
//        super(x, y, Math.toRadians(h), DistanceUnit.CM);
//        velocityVectorX = velocityX;
//        velocityVectorY = velocityY;
//        goalSpeed = -1;
//    }

    public Waypoint(EditablePose2D pos) {
        super(pos.getX(pos.getDistanceUnit()), pos.getY(pos.getDistanceUnit()), pos.getH(), pos.getDistanceUnit());
        this.velocityVectorX = 0;
        this.velocityVectorY = 0;
        this.goalSpeed = -1;

        this.defined = false;
    }

    public Waypoint(EditablePose2D pos, double velocityX, double velocityY) {
        super(pos.getX(pos.getDistanceUnit()), pos.getY(pos.getDistanceUnit()), pos.getH(), pos.getDistanceUnit());
        this.velocityVectorX = velocityX;
        this.velocityVectorY = velocityY;
        this.goalSpeed = -1;

        this.defined = true;
    }

    public Waypoint(EditablePose2D pos, double velocityX, double velocityY, double goalSpeed) {
        super(pos.getX(pos.getDistanceUnit()), pos.getY(pos.getDistanceUnit()), pos.getH(), pos.getDistanceUnit());
        this.velocityVectorX = velocityX;
        this.velocityVectorY = velocityY;
        this.goalSpeed = goalSpeed;

        this.defined = true;
    }

    public Waypoint(EditablePose2D pos, double velocityX, double velocityY, double goalSpeed, DistanceUnit units) {
        super(pos.getX(pos.getDistanceUnit()), pos.getY(pos.getDistanceUnit()), pos.getH(), units);
        this.velocityVectorX = velocityX;
        this.velocityVectorY = velocityY;
        this.goalSpeed = goalSpeed;

        this.defined = true;
    }

    public Waypoint(double x, double y, double h, double velocityX, double velocityY, double goalSpeed) {
        super(x, y, Math.toRadians(h), DistanceUnit.CM);
        this.velocityVectorX = velocityX;
        this.velocityVectorY = velocityY;
        this.goalSpeed = goalSpeed;

        this.defined = true;
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

        this.defined = true;
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
        this.defined = true;
    }

    public double getVelocityVectorY() {
        return velocityVectorY;
    }

    public double getAngle() {
        return Math.atan2(velocityVectorY, velocityVectorX);
    }

    public void setVelocityVectorY(double velocityVectorY) {
        this.velocityVectorY = velocityVectorY;
        this.defined = true;
    }

    public void setVeloctiyVector(double x, double y) {
        this.velocityVectorX = x;
        this.velocityVectorY = y;

        this.defined = true;
    }

    public double[] getVelocityVector() {
        return new double[]{velocityVectorX, velocityVectorY};
    }

    public boolean fullyDefined() {
        return defined || !(velocityVectorX == 0 && velocityVectorY == 0);
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
