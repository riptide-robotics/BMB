package org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;

import java.util.ArrayList;

public class Spline {

    private ArrayList<Waypoint> waypoints;
    private ArrayList<Boolean> contiuousRotation;
    private ArrayList<Boolean> contiuousSpeed;
    private ArrayList<SplineSegment> segments;
    private int currentSegment = 0;
    private int numPoints;
    private String log;

    public static double KC = 0.99;

    public Spline(SplineBuilder s) {
        this.waypoints = s.getPath();
        this.contiuousRotation = s.getContinuousRotation();
        this.contiuousSpeed = s.getContinuousSpeed();
        this.segments = new ArrayList<>();
        this.log = "";
        build();
    }

    public static class SplineBuilder {
        private ArrayList<Waypoint> waypoints = new ArrayList<Waypoint>();
        private ArrayList<Boolean> contiuousRotation = new ArrayList<Boolean>();
        private ArrayList<Boolean> contiuousSpeed = new ArrayList<Boolean>();

        public SplineBuilder add() {
            Waypoint point = new Waypoint();
            waypoints.add(point);
            contiuousSpeed.add(true);
            contiuousRotation.add(true);
            return this;
        }

        public SplineBuilder add(EditablePose2D pos) {
            Waypoint point = new Waypoint(pos, 0, 0, -1);
            waypoints.add(point);
            contiuousSpeed.add(true);
            contiuousRotation.add(true);
            return this;
        }

        public SplineBuilder add(EditablePose2D pos, double velocityX, double velocityY) {
            Waypoint point = new Waypoint(pos, velocityX, velocityY, -1);
            waypoints.add(point);
            contiuousSpeed.add(true);
            contiuousRotation.add(true);
            return this;
        }

        public SplineBuilder add(EditablePose2D pos, boolean continueRotation, boolean continueSpeed) {
            Waypoint point = new Waypoint(pos, 0, 0, -1);
            waypoints.add(point);
            contiuousSpeed.add(continueRotation);
            contiuousRotation.add(continueSpeed);
            return this;
        }

        public SplineBuilder add(EditablePose2D pos, double velocityX, double velocityY, boolean continueRotation, boolean continueSpeed) {
            Waypoint point = new Waypoint(pos, velocityX, velocityY, -1);
            waypoints.add(point);
            contiuousSpeed.add(continueRotation);
            contiuousRotation.add(continueSpeed);
            return this;
        }

        public SplineBuilder add(EditablePose2D pos, double velocityX, double velocityY, double goalSpeed) {
            Waypoint point = new Waypoint(pos, velocityX, velocityY, goalSpeed);
            waypoints.add(point);
            contiuousSpeed.add(true);
            contiuousRotation.add(true);
            return this;
        }

        public SplineBuilder add(EditablePose2D pos, double velocityX, double velocityY, double goalSpeed, boolean continueRotation, boolean continueSpeed) {
            Waypoint point = new Waypoint(pos, velocityX, velocityY, goalSpeed);
            waypoints.add(point);
            contiuousSpeed.add(continueRotation);
            contiuousRotation.add(continueSpeed);
            return this;
        }

        public ArrayList<Waypoint> getPath(){
            return waypoints;
        }

        public ArrayList<Boolean> getContinuousRotation(){
            return contiuousRotation;
        }

        public ArrayList<Boolean> getContinuousSpeed(){
            return contiuousSpeed;
        }

        public Spline build(){
            contiuousRotation.remove(contiuousRotation.size() - 1);
            contiuousSpeed.remove(contiuousSpeed.size() - 1);
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
        logNewLine("Define Points: ");
        if(!(waypoints.get(0).fullyDefined() && waypoints.get(numPoints - 1).fullyDefined())) {
            throw new IllegalStateException("Endpoints aren't fully defined: \n" + print());
        }

        for(int i=0; i<numPoints; i++) {
            if(!waypoints.get(i).fullyDefined()) {
                logNewLine("   waypoint" + i + "isn't fully defined.");
                for(int j=i+1; j<numPoints; j++) {
                    if (waypoints.get(j).fullyDefined()) {
                        logNewLine("   up until waypoint ", j-1);
                        defineRange(i-1, j);
                        i = j;
                        break;
                    }
                }
            }
        }
        lineBreak();
    }

    public void defineRange(int start, int end) {
        int numSegmentPoints = end-start+1;
        double[] alpha = new double[numSegmentPoints];
        double[][] beta = new double[numSegmentPoints][2];
        double[][] velocities = new double[numSegmentPoints][2];

        velocities[0] = waypoints.get(start).getVelocityVector();
        velocities[numSegmentPoints-1] = waypoints.get(end).getVelocityVector();

        alpha[0] = 4.0;
        beta[0][0] = (waypoints.get(2).getX(DistanceUnit.INCH) - waypoints.get(0).getX(DistanceUnit.INCH)) * 3.0 - velocities[0][0];
        beta[0][1] = (waypoints.get(2).getY(DistanceUnit.INCH) - waypoints.get(0).getY(DistanceUnit.INCH)) * 3.0 - velocities[0][1];

        for (int i=1; i < numSegmentPoints-1; i++) {
            alpha[i] = 4.0 - (1.0 / alpha[i-1]);

            double dx = (waypoints.get(i+1).getX(DistanceUnit.INCH) - waypoints.get(i-1).getX(DistanceUnit.INCH)) * 3.0;
            beta[i][0] = dx - (beta[i-1][0] / alpha[i-1]);

            double dy = (waypoints.get(i+1).getY(DistanceUnit.INCH) - waypoints.get(i-1).getY(DistanceUnit.INCH)) * 3.0;
            beta[i][1] = dy - (beta[i-1][1] / alpha[i-1]);
        }

        beta[numSegmentPoints-1][0] -= velocities[numSegmentPoints-1][0];
        beta[numSegmentPoints-1][1] -= velocities[numSegmentPoints-1][1];

        velocities[numSegmentPoints-1][0] = beta[numSegmentPoints-1][0] / alpha[numSegmentPoints - 1];
        velocities[numSegmentPoints-1][1] = beta[numSegmentPoints-1][1] / alpha[numSegmentPoints - 1];
        for (int i=numSegmentPoints-2; i>=0; i--) {
            velocities[i][0] = (beta[i][0] - velocities[i+1][0]) / alpha[i];
            velocities[i][1] = (beta[i][1] - velocities[i+1][1]) / alpha[i];
        }

        for (int i=1; i<numSegmentPoints-1; i++) {
            waypoints.get(start+i+1).setVeloctiyVector(velocities[i][0], velocities[i][1]);
        }
    }

    public void buildSegments() {
        logNewLine("Building Segments...");
        for (int i=0; i<numPoints-1; i++) {
            segments.add(new SplineSegment(waypoints.get(i), waypoints.get(i+1)));
        }
        lineBreak();
    }

    public double[] follow(EditablePose2D pos, double heading) {
        logNewLine("Follow: ");

        if(done()) {
            logNewLine("   done.");
            return new double[]{0.0, 0.0, 0.0};
        }

        // get corresponding point
        double[] correspondingPoint = segments.get(currentSegment).getCorrespondingPoint(pos);

        log("   point: {", correspondingPoint[1]);
        log(", ", correspondingPoint[2]);
        logNewLine("}");

        boolean pastSeg = pastSegment(
                pos,
                correspondingPoint[0]
//                SplineSegment.dist(
//                        segments.get(currentSegment).getEndPoint(false).getX(DistanceUnit.INCH),
//                        segments.get(currentSegment).getEndPoint(false).getY(DistanceUnit.INCH),
//                        pos.getX(DistanceUnit.INCH),
//                        pos.getY(DistanceUnit.INCH)
//                )
        );
        logNewLine("   Past Segment: ", pastSeg);
        if(pastSeg) {currentSegment++;}

        if(done()) {
            logNewLine("   done.");
            return new double[]{0.0, 0.0, 0.0};
        }

        // get curvature of point
        double curvature = segments.get(currentSegment).getCurvature(correspondingPoint[0]);
        double curvatureFactor = curvature == 0? 0: Math.sqrt(1 / (1 + KC * Math.abs(curvature)));
        logNewLine("   Curvature Factor: ", curvatureFactor);

        // dx and dy
        double dx = correspondingPoint[1] - pos.getX(DistanceUnit.INCH);
        double dy = correspondingPoint[2] - pos.getY(DistanceUnit.INCH);

        if (Math.abs(dx) < 1e-12) {
            dx = 0.0;
        }
        if (Math.abs(dy) < 1e-12) {
            dy = 0.0;
        }

        log("   d: {", dx);
        log(", ", dy);
        logNewLine("}");

        // find all the forces (3) on the robot
        double forwardAngle = Math.atan2(
                segments.get(currentSegment).getSlopeY(correspondingPoint[0]),
                segments.get(currentSegment).getSlopeX(correspondingPoint[0])
        );
        double[] forwardForce = new double[] {Math.cos(forwardAngle), Math.sin(forwardAngle)};

        double[] curvatureForce = new double[] {-Math.cos(forwardAngle) * curvatureFactor / 10000000, -Math.sin(forwardAngle) * curvatureFactor / 10000000};

        double[] crossTrackForce = new double[] {
                Math.clamp(dx * Math.sqrt(Math.sqrt(correspondingPoint[3]) / 5), -1, 1),
                Math.clamp(dy * Math.sqrt(Math.sqrt(correspondingPoint[3]) / 5), -1, 1)};

        // return the net force and rotation or wheel powers, need to decide
        double netForceX = forwardForce[0] + curvatureForce[0] + crossTrackForce[0];
        double netForceY = forwardForce[1] + curvatureForce[1] + crossTrackForce[1];
        double netForceAngle = Math.atan2(netForceY, netForceX);

        double[] netForce = new double[]{
                netForceX * Math.abs(Math.cos(netForceAngle)),
                netForceY * Math.abs(Math.sin(netForceAngle))
        };
        double netMagnitude = Math.hypot(netForce[0], netForce[1]);

        double goalAngle = segments.get(currentSegment).getStartPoint(false).getH() +
                (segments.get(currentSegment).getEndPoint(false).getH() - segments.get(currentSegment).getStartPoint(false).getH()) * correspondingPoint[0];
        double rotationForce = (goalAngle - heading) / (100000);

        if (netMagnitude < 1e-12) {return new double[]{0.0, 0.0, rotationForce};}

        log("   Net Force: {", netForce[0] / netMagnitude);
        log(", ", netForce[1] / netMagnitude);
        log(", ", netForceAngle);
        logNewLine(" rad}; magnitude: ", netMagnitude);

        lineBreak();
        if (Math.abs(netForce[0] / netMagnitude) < 1e-12) {
            netForce[0] = 0.0;
        }

        if (Math.abs(netForce[1] / netMagnitude) < 1e-12) {
            netForce[1] = 0.0;
        }

        return new double[] {netForce[0] / netMagnitude, netForce[1] / netMagnitude, rotationForce};
    }

    public boolean pastSegment(EditablePose2D pos, double u) {
        logNewLine("   Past Segment: ");
        logNewLine("      Too Far: ", u < 0.95);
        if (u < 0.95 || done()) {return false;}
        double angleCutoff = segments.get(currentSegment).getEndPoint(false).getAngle(); // need to get perpendicular for line of passing to next seg
        double perpendicular = angleCutoff + (Math.PI / 2);

        log("      Perpendicular: ", perpendicular);
        logNewLine(" rad");

        double dx = segments.get(currentSegment).getEndPoint(false).getX(DistanceUnit.INCH) - pos.getX(DistanceUnit.INCH);
        double dy = segments.get(currentSegment).getEndPoint(false).getY(DistanceUnit.INCH) - pos.getY(DistanceUnit.INCH);

        if (Math.abs((perpendicular - Math.PI / 2) % (Math.PI)) < 1e-12) {
            log("   Vertical Line: ");
            double directionSpline = segments.get(currentSegment).getEndPoint(false).getX(DistanceUnit.INCH) - segments.get(currentSegment).getStartPoint(false).getX(DistanceUnit.INCH);
            if(directionSpline >= 0) {
                logNewLine("stay right ", dx <= 0);
                return dx <= 0;
            }
            logNewLine("stay left ", dx >= 0);
            return dx >= 0;
        }

        log("      Dist Endpt: {", dx);
        log(", ", dy);
        logNewLine("}");

        double determinant = Math.cos(perpendicular) * dy - Math.sin(perpendicular) * dx; // cross product to get if point above or below line
        double directionSpline = segments.get(currentSegment).getEndPoint(false).getY(DistanceUnit.INCH) - segments.get(currentSegment).getStartPoint(false).getY(DistanceUnit.INCH);

        logNewLine("      Determinant: ", determinant);
        logNewLine("      Direction: ", directionSpline);

        return determinant == 0 || (directionSpline > 0 && determinant > 0) || (directionSpline < 0 && determinant < 0); // on line or below line or above line.
    }

    public boolean done() {return currentSegment >= segments.size();}

    public String getLog() {
        return log;
    }

    private void log(String msg) { log += msg;}

    private void logNewLine(String msg) { log += msg + "\n"; }

    private <T> void log(String msg, T val) {
        log += msg + val.toString();
    }

    private <T> void logNewLine(String msg, T val) {
        log += msg + val.toString() + "\n";
    }

    public void lineBreak() {
        log += "\n";
    }

    public void clearLog() {
        log = "";
    }

    public String getSegmentLog() {
        if(done()) {return "done.";}
        return segments.get(currentSegment).getLog();
    }

    public void clearSegmentLog() {
        if(!done()) {
            segments.get(currentSegment).clearLog();
        }
    }

    public void clearAllSegmentLogs() {
        for(SplineSegment i: segments) {
            i.clearLog();
        }
    }

    public String print() {
        StringBuilder returnStr = new StringBuilder();
        for(Waypoint i: waypoints) {
            returnStr.append(i.toString()).append(",\n");
        }
        return returnStr.toString();
    }

    public int getCurrentSegment() {
        return currentSegment;
    }

    public Waypoint get(int index) {
        if(index >= numPoints) {
            throw new IllegalStateException("Out of range (" + index + " > " + (numPoints-1) + ")");
        }
        return waypoints.get(index);
    }


}
