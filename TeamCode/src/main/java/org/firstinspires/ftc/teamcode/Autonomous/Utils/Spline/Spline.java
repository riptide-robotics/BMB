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
        numPoints = waypoints.size();
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
        if(pastSegment(
                pos,
                SplineSegment.dist(
                        segments.get(currentSegment).getEndPoint().getX(DistanceUnit.INCH),
                        segments.get(currentSegment).getEndPoint().getY(DistanceUnit.INCH),
                        pos.getX(DistanceUnit.INCH),
                        pos.getY(DistanceUnit.INCH)
                )
        )) {currentSegment++;}
        if(done()) {return new double[]{0.0, 0.0, 0.0, 0.0};}

        // get corresponding point
        double[] correspondingPoint = segments.get(currentSegment).getCorrespondingPoint(pos);

        // get curvature of point
        double curvature = segments.get(currentSegment).getCurvature(correspondingPoint[0]);
        double curvatureFactor = curvature == 0? 0: Math.sqrt(1 / curvature);

        // dx and dy
        double dx = correspondingPoint[1] - pos.getX(DistanceUnit.INCH);
        double dy = correspondingPoint[2] - pos.getY(DistanceUnit.INCH);

        // find all the forces (3) on the robot
        double forwardAngle = Math.atan2(segments.get(currentSegment).getSlopeX(correspondingPoint[0]), segments.get(currentSegment).getSlopeY(correspondingPoint[0]));
        double[] forwardForce = new double[] {Math.cos(forwardAngle), Math.sin(forwardAngle)};

        double[] curvatureForce = new double[] {-Math.cos(forwardAngle) * curvatureFactor, -Math.sin(forwardAngle) * curvatureFactor};

        double[] crossTrackForce = new double[] {Math.clamp(dx * Math.sqrt(5 / correspondingPoint[3]), -1, 1), Math.clamp(dy * Math.sqrt(5 / correspondingPoint[3]), -1, 1)};

        // return the net force and rotation or wheel powers, need to decide
        double netForceX = forwardForce[0] + curvatureForce[0] + crossTrackForce[0];
        double netForceY = forwardForce[1] + curvatureForce[1] + crossTrackForce[1];
        double netForceAngle = Math.atan2(netForceX, netForceY);

        double[] netForce = new double[]{netForceX * Math.cos(netForceAngle), netForceY * Math.sin(netForceY)};
        double netMagnitude = SplineSegment.dist(0, 0, netForce[0], netForce[1]);

        double goalAngle = segments.get(currentSegment).getStartPoint().getH() +
                (segments.get(currentSegment).getEndPoint().getH() - segments.get(currentSegment).getStartPoint().getH()) * correspondingPoint[0];

        return new double[] {netForce[0] / netMagnitude, netForce[1] / netMagnitude, (goalAngle - pos.getH()) / (Math.PI / 4)};
    }

    public boolean pastSegment(EditablePose2D pos, double dist) {
        if (dist > 5) {return false;}
        double angleCutoff = segments.get(currentSegment).getEndPoint().getAngle(); // need to get perpendicular for line of passing to next seg
        double perpendicular = angleCutoff + (Math.PI / 2);

        double dx = segments.get(currentSegment).getEndPoint().getX(DistanceUnit.INCH) - pos.getX(DistanceUnit.INCH);
        double dy = segments.get(currentSegment).getEndPoint().getY(DistanceUnit.INCH) - pos.getY(DistanceUnit.INCH);

        double determinant = Math.cos(perpendicular) * dy - Math.sin(perpendicular) * dx; // cross product to get if point above or below line
        double directionSpline = segments.get(currentSegment).getEndPoint().getY(DistanceUnit.INCH) - segments.get(currentSegment).getStartPoint().getY(DistanceUnit.INCH);

        return determinant == 0 || (directionSpline > 0 && determinant > 0) || (directionSpline < 0 && determinant < 0); // on line or below line or above line.
    }

    public boolean done() {return currentSegment >= segments.size();}

    public String print() {
        StringBuilder returnStr = new StringBuilder();
        for(Waypoint i: waypoints) {
            returnStr.append(i.toString()).append("\n");
        }
        return returnStr.toString();
    }

}
