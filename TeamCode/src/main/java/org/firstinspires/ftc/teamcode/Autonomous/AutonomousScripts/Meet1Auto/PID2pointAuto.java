package org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts.Meet1Auto;

import static org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts.Meet1Auto.PinpointPositionHold.P_HEADING;
import static org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts.Meet1Auto.PinpointPositionHold.P_TRANSLATION;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;

import java.util.ArrayList;

public class PID2pointAuto {
    private ArrayList <EditablePose2D> points;
    private int pointIndex = 0;

    public double[] run(double currentX, double currentY, double currentHeading) {
        if (pointIndex >= points.size()) return new double[] {0, 0, 0, 0};
        EditablePose2D pinpoint = points.get(pointIndex);

        double errorX = pinpoint.getX(DistanceUnit.INCH) - currentX;
        double errorY = pinpoint.getY(DistanceUnit.INCH) - currentY;
        double errorHeading = pinpoint.getH() - currentHeading;

        while (errorHeading > Math.PI) errorHeading -= 2 * Math.PI;
        while (errorHeading < -Math.PI) errorHeading += 2 * Math.PI;

        double fx = errorY * P_TRANSLATION;
        double fy = -errorX * P_TRANSLATION;
        double turn = errorHeading * P_HEADING;

        double rotX = fx * Math.cos(currentHeading) + fy * Math.sin(currentHeading);
        double rotY = -fx * Math.sin(currentHeading) + fy * Math.cos(currentHeading);

        double lfPower = rotY + rotX + turn;
        double lrPower = rotY - rotX + turn;
        double rfPower = rotY - rotX - turn;
        double rrPower = rotY + rotX - turn;

        double max = Math.max(Math.max(Math.abs(lfPower), Math.abs(lrPower)),
                    Math.max(Math.abs(rfPower), Math.abs(rrPower)));
        if (max > 1.0) {
            lfPower /= max;
            lrPower /= max;
            rfPower /= max;
            rrPower /= max;
        }



        return new double[] {lfPower, lrPower, rfPower, rrPower};

    }

    public double getPosX(int index) {
        return points.get(index).getX(DistanceUnit.INCH);
    }
    public double getPosY(int index) {
        return points.get(index).getX(DistanceUnit.INCH);
    }
    public void setPosX(int index, double newVal) {points.get(index).setX(newVal, DistanceUnit.INCH);}
    public void setPosY(int index, double newVal) {points.get(index).setY(newVal, DistanceUnit.INCH);}

}
