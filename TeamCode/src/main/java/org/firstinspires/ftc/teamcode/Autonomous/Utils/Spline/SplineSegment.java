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
        return null;
    }

}
