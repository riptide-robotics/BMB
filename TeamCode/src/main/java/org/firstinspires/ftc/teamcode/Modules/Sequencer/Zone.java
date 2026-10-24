package org.firstinspires.ftc.teamcode.Modules.Sequencer;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;
import org.firstinspires.ftc.teamcode.Robot;

import java.util.Arrays;

public class Zone {

    public EditablePose2D[] pos;


    public Robot robot = null;

    public Zone(Robot robot, String name, EditablePose2D... pos) {

        if (pos.length < 3)
            throw new IllegalArgumentException("Zone " + name + "doesn't have enough points! Make sure they form a polygon.");

        this.pos = pos;
        this.robot = robot;
    }

    public Zone(String name, EditablePose2D... pos) {

        if (pos.length < 3)
            throw new IllegalArgumentException("Zone " + name + "doesn't have enough points! Make sure they form a polygon.");

        this.pos = pos;
    }

    public Zone (Robot robot, String name, Double... coords) {

        this.pos = new EditablePose2D[coords.length / 2];
        this.robot = robot;

        if (coords.length < 6)
            throw new IllegalArgumentException("Zone " + name + "doesn't have enough points! Make sure they form a polygon.");

        if (coords.length % 2 != 0)
            throw new IllegalArgumentException("Zone " + name + "has invalid coordinates! There is an odd number of provided doubles.");

        for (int i = 0; i < coords.length; i += 2)
            pos[i/2] = new EditablePose2D( coords[i], coords[i+1], 0, DistanceUnit.INCH);
    }

    public Zone (String name, Double... coords) {

        this.pos = new EditablePose2D[coords.length / 2];

        if (coords.length % 2 != 0)
            throw new IllegalArgumentException("Zone " + name + "has invalid coordinates! There is an odd number of provided doubles.");

        if (coords.length < 6)
            throw new IllegalArgumentException("Zone " + name + "doesn't have enough points! Make sure they form a polygon.");

        for (int i = 0; i < coords.length; i += 2)
            pos[i/2] = new EditablePose2D(coords[i], coords[i+1], 0,DistanceUnit.INCH);
    }

    public Zone(String name, Double[][] coords) {
        int err = 0;

        for (int i = 0; i < coords.length; i++) {
            if (coords[i].length != 2) {
                err++;
                continue;
            }
            pos[i] = new EditablePose2D(coords[i][0], coords[i][1], 0, DistanceUnit.INCH);
        }

        if (err > 0) throw new IllegalArgumentException("Zone " + name + "has invalid coordinates! " + err + (err == 1 ? "coordinate":"coordinates") + " has incorrect. ");
        throw new IllegalArgumentException("Zone " + name + "doesn't have enough points! Make sure they form a polygon.");
    }


    public boolean botInZone(Robot robot) {
        this.robot = robot;
        return botInZone();
    }

    public boolean botInZone() {

        boolean within = false;
        EditablePose2D robotpos = robot.getDrivetrain().getCurrPos();

        double xb = robotpos.getX(DistanceUnit.INCH);
        double yb = robotpos.getY(DistanceUnit.INCH);

        for (int i = 0; i < pos.length; i++) {

            EditablePose2D pos1 = pos[i];
            EditablePose2D pos2 = pos[(i + 1) % pos.length];

            double x1 = pos1.getX(DistanceUnit.INCH);
            double y1 = pos1.getY(DistanceUnit.INCH);
            double x2 = pos2.getX(DistanceUnit.INCH);
            double y2 = pos2.getY(DistanceUnit.INCH);

            // first section not technically necessary but good for optimization
            if (y1 > yb != y2 > yb && (
                    (xb < x1 && xb < x2)
                    || xb < (x1 + ((yb-y1) / (y2-y1)) * (x2-x1)))) {
                within = !within;
            }
        }
        return within;
//        return false;
    }
}
