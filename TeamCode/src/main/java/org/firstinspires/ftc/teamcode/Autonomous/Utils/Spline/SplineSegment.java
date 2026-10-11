package org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;
import org.ejml.simple.SimpleMatrix;
import org.ejml.data.Complex_F64;

public class SplineSegment {
    private Waypoint startPoint;
    private Waypoint endPoint;

    public SplineSegment(Waypoint startPoint, Waypoint endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public EditablePose2D getCorrespondingPoint(EditablePose2D pos) {
        double a_3X = 2 * startPoint.getX(DistanceUnit.INCH) - 2 * endPoint.getX(DistanceUnit.INCH) + startPoint.getVelocityVectorX() + endPoint.getVelocityVectorX();
        double a_2X = -3 * startPoint.getX(DistanceUnit.INCH) + 3 * endPoint.getX(DistanceUnit.INCH) - 2 * startPoint.getVelocityVectorX() - endPoint.getVelocityVectorX();
        double a_1X = startPoint.getVelocityVectorX();
        double a_0X = startPoint.getX(DistanceUnit.INCH);

        double a_3Y = 2 * startPoint.getY(DistanceUnit.INCH) - 2 * endPoint.getY(DistanceUnit.INCH) + startPoint.getVelocityVectorY() + endPoint.getVelocityVectorY();
        double a_2Y = -3 * startPoint.getY(DistanceUnit.INCH) + 3 * endPoint.getY(DistanceUnit.INCH) - 2 * startPoint.getVelocityVectorY() - endPoint.getVelocityVectorY();
        double a_1Y = startPoint.getVelocityVectorY();
        double a_0Y = startPoint.getY(DistanceUnit.INCH);

        double dx = pos.getX(DistanceUnit.INCH) - a_0X;
        double dy = pos.getY(DistanceUnit.INCH) - a_0Y;

        double c5 = -3 * (a_3X * a_3X + a_3Y * a_3Y);
        double c4 = -5 * (a_2X * a_3X + a_2Y * a_3Y);
        double c3 = -4 * (a_1X * a_3X + a_1Y *a_3Y) - 2 * (a_2X * a_2X + a_2Y * a_2Y);
        double c2 =  3 * (dx * a_3X + dy * a_3Y) - 3 * (a_1X * a_2X + a_1Y * a_2Y);
        double c1 =  2 * (dx * a_2X + dy * a_2Y) - (a_1X * a_1X + a_1Y * a_1Y);
        double c0 = (dx * a_1X + dy * a_1Y);

        if (Math.abs(c5) < 1e-12) {
            c5 = 1e-12;
        }

        return null;
    }

    public EditablePose2D getPointOnCurve(double t) {
        return new EditablePose2D(getX(t), getY(t), 0, DistanceUnit.INCH);
    }

    public double getX(double t) {
        double t2 = t * t;
        double t3 = t2 * t;

        double h1 =  2.0f * t3 - 3.0f * t2 + 1.0f;
        double h2 = -2.0f * t3 + 3.0f * t2;
        double h3 = t3 - 2.0f * t2 + t;
        double h4 = t3 - t2;

        return h1 * startPoint.getX(DistanceUnit.INCH) + h2 * endPoint.getX(DistanceUnit.INCH) + h3 * startPoint.getVelocityVectorX() + h4 * endPoint.getVelocityVectorX();
    }

    public double getY(double t) {
        double t2 = t * t;
        double t3 = t2 * t;

        double h1 =  2.0f * t3 - 3.0f * t2 + 1.0f;
        double h2 = -2.0f * t3 + 3.0f * t2;
        double h3 = t3 - 2.0f * t2 + t;
        double h4 = t3 - t2;

        return h1 * startPoint.getY(DistanceUnit.INCH) + h2 * endPoint.getY(DistanceUnit.INCH) + h3 * startPoint.getVelocityVectorY() + h4 * endPoint.getVelocityVectorY();
    }

}
