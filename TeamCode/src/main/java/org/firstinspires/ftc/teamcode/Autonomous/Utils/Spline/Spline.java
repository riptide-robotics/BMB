package org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;

import java.util.ArrayList;

public class Spline {

    private ArrayList<Waypoint> waypoints;
    private ArrayList<SplineSegment> segments;
    private int currentSegment = 0;
    private int numPoints;

    public Spline(SplineBuilder s) {
        this.waypoints = s.getPath();
        this.segments = new ArrayList<>();
        build();
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

    public Spline build() {
        numPoints = waypoints.size();;
        definePoints();
        buildSegments();
        return this;
    }

    public void definePoints() {
        if(!waypoints.get(0).fullyDefined() || !waypoints.get(numPoints - 1).fullyDefined()) {
            throw new IllegalStateException("Endpoints aren't fully defined");
        }

        for(int i=0; i<numPoints; i++) {
            if(!waypoints.get(i).fullyDefined()) {
                for(int j=i+1; j<numPoints; j++) {
                    if (waypoints.get(j).fullyDefined()) {
                        defineRange(i-1, j);
                        i = j;
                        break;
                    }
                }
            }
        }
    }

    public void defineRange(int start, int end) {
        int segmentPoints = end-start+1;
        double[] alpha = new double[segmentPoints];
        double[][] beta = new double[segmentPoints][2];
        double[][] velocities = new double[segmentPoints][2];

        velocities[0] = waypoints.get(start).getVelocityVector();
        velocities[segmentPoints-1] = waypoints.get(end).getVelocityVector();

        alpha[1] = 4.0;
        beta[1][0] = (waypoints.get(2).getX(DistanceUnit.INCH) - waypoints.get(0).getX(DistanceUnit.INCH)) * 3.0 - velocities[0][0];
        beta[1][1] = (waypoints.get(2).getY(DistanceUnit.INCH) - waypoints.get(0).getY(DistanceUnit.INCH)) * 3.0 - velocities[0][1];

        for (int i=2; i< segmentPoints; i++) {
            alpha[i] = 4.0 - (1.0 / alpha[i-1]);

            double dx = (waypoints.get(i+1).getX(DistanceUnit.INCH) - waypoints.get(i-1).getX(DistanceUnit.INCH)) * 3.0;
            beta[i][0] = dx - (beta[i-1][0] / alpha[i-1]);

            double dy = (waypoints.get(i+1).getY(DistanceUnit.INCH) - waypoints.get(i-1).getY(DistanceUnit.INCH)) * 3.0;
            beta[i][1] = dy - (beta[i-1][1] / alpha[i-1]);
        }

        beta[segmentPoints-1][0] -= velocities[segmentPoints-1][0];
        beta[segmentPoints-1][1] -= velocities[segmentPoints-1][1];

        velocities[segmentPoints-1][0] = beta[segmentPoints-1][0] / alpha[segmentPoints - 1];
        velocities[segmentPoints-1][1] = beta[segmentPoints-1][1] / alpha[segmentPoints - 1];
        for (int i=segmentPoints-2; i>0; i--) {
            velocities[i][0] = (beta[i][0] - velocities[i+1][0]) / alpha[i];
            velocities[i][1] = (beta[i][1] - velocities[i+1][1]) / alpha[i];
        }

        for (int i=1; i<segmentPoints-1; i++) {
            waypoints.get(start+i+1).setVeloctiyVector(velocities[i][0], velocities[i][1]);
        }
    }

    public void buildSegments() {
        for (int i=0; i<numPoints-1; i++) {
            segments.add(new SplineSegment(waypoints.get(i), waypoints.get(i+1)));
        }
    }

    public double[] follow(EditablePose2D pos) {

        return new double[]{0, 0, 0, 0};
    }

}
