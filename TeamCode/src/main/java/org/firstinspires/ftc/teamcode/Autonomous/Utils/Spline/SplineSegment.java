package org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline;

import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;

public class SplineSegment {
    private Waypoint startPoint;
    private Waypoint endPoint;

    public SplineSegment(Waypoint startPoint, Waypoint endPoint) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public EditablePose2D getCorrespondingPoint(EditablePose2D pos) {
        a3 = 2 * p_start - 2 * p_end + v_start + v_end
        a2 = -3 * p_start + 3 * p_end - 2 * v_start - v_end
        a1 = v_start
        a0 = p_start
        return null;
    }

    public EditablePose2D getPointOnCurve(double t) {
        double t2 = t * t;
        double t3 = t2 * t;

        double h1 =  2.0f * t3 - 3.0f * t2 + 1.0f;
        double h2 = -2.0f * t3 + 3.0f * t2;
        double h3 = t3 - 2.0f * t2 + t;
        double h4 = t3 - t2;

        double x = h1 * startPoint.getX(DistanceUnit.INCH) + h2 * endPoint.getX(DistanceUnit.INCH) + h3 * mA + h4 * mB;
        double y =
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

}
