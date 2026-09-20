package org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline;

import org.firstinspires.ftc.teamcode.Autonomous.Utils.Path;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;

import java.util.ArrayList;

public class Spline {

    private ArrayList<Waypoint> waypoints;

    public Spline(SplineBuilder s) {
        this.waypoints = s.getPath();
    }

    public static class SplineBuilder {
        private ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();

        public SplineBuilder add() {
            Waypoint point = new Waypoint();
            waypoints.add(point);
            return this;
        }

        public SplineBuilder add(EditablePose2D pos) {
            Waypoint point = new Waypoint(pos, 0, 0, -1);
            waypoints.add(point);
            return this;
        }

        public SplineBuilder add(EditablePose2D pos, double velocityX, double velocityY) {
            Waypoint point = new Waypoint(pos, velocityX, velocityY, -1);
            waypoints.add(point);
            return this;
        }

        public SplineBuilder add(EditablePose2D pos, double velocityX, double velocityY, double goalSpeed) {
            Waypoint point = new Waypoint(pos, velocityX, velocityY, goalSpeed);
            waypoints.add(point);
            return this;
        }

        public ArrayList<Waypoint> getPath(){
            return waypoints;
        }

        public Spline build(){
            return new Spline(this);
        }
    }
}
