package org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline.Spline;
import org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline.SplineSegment;
import org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline.Waypoint;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;

@Config
@Autonomous(name = "Test Path Init", group = "Test")
public class TestInit extends LinearOpMode {
    private Spline spline;
    Telemetry t = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    @Override
    public void runOpMode() throws InterruptedException {

        spline = new Spline.SplineBuilder()
                .add(new Waypoint(new EditablePose2D(0, 0, 0, DistanceUnit.INCH), 0, 1))
                .add(new Waypoint(new EditablePose2D(0, 10, 0, DistanceUnit.INCH), 0, 1))
                .build();

        EditablePose2D pos1 = new EditablePose2D(0, 5, 0, DistanceUnit.INCH);
        EditablePose2D pos2 = new EditablePose2D(-1, 5, 0, DistanceUnit.INCH);

        waitForStart();

        while(opModeIsActive()) {
            t.addData("Spline: ", spline.print());
            t.addData("Follow1: \n", stringFollowPrint(spline.follow(pos1)));
            t.addData("Past Segment1: ", spline.pastSegment(pos1, SplineSegment.dist(0, 10, 0, 5)));
            t.addData("Follow2: \n", stringFollowPrint(spline.follow(pos2)));
            t.addData("Past Segment2: ", spline.pastSegment(pos2, SplineSegment.dist(0, 10, -1, 5)));
            t.addData("Done", spline.done());
            t.addData("Current Segment: ", spline.getCurrentSegment());
            t.addData("Spline Log:\n", spline.getLog());
            t.addData("\nSegment Log:\n", spline.getSegmentLog());
            t.update();
            spline.clearLog();
            spline.clearSegmentLog();
        }
    }

    public String stringFollowPrint(double[] follow) {
        String returnStr = "";
        returnStr += "    X: " + follow[0] + "\n";
        returnStr += "    Y: " + follow[1] + "\n";
        returnStr += "    Rotate: " + follow[2] + "\n";
        return returnStr;
    }
}
