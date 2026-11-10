package org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline.Spline;
import org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline.SplineSegment;
import org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline.Waypoint;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;
import org.firstinspires.ftc.teamcode.Robot;

@Config
@Autonomous(name = "Test Moving Forward and Backward", group = "Test")
public class TestFB extends LinearOpMode {
    public Robot robot;
    private Spline spline;
    public static double slowdown;
    Telemetry t = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    @Override
    public void runOpMode() throws InterruptedException {

        spline = new Spline.SplineBuilder()
                .add(new EditablePose2D(0, 0, 0, DistanceUnit.INCH), 1, 0)
                .add(new EditablePose2D(10, 0, 0, DistanceUnit.INCH))
                .add(new EditablePose2D(0, 0, 0, DistanceUnit.INCH), 1, 0)
                .build();

        robot = new Robot(hardwareMap);

        slowdown = 0.5;

        t.addData("Robot status", "succesfully initiated");
        t.update();

        waitForStart();
        if (isStopRequested()) return;

        t.clear();
        t.addData("Robot status", "Started!");
        t.update();

        while(opModeIsActive()) {
            double[] moveArray = spline.follow(robot.getDrivetrain().getCurrPos(), robot.getDrivetrain().getRobotHeading(AngleUnit.DEGREES));

            print(moveArray);

            spline.clearLog();
            spline.clearSegmentLog();

            robot.getDrivetrain().fieldCentricDrive(moveArray[0], moveArray[1], moveArray[2], slowdown);
        }
    }

    public void print(double[] moveArray) {
        t.addLine("Spline: ");
        t.addLine(spline.print());
        t.addLine("Follow: ");
        t.addLine(stringFollowPrint(moveArray));

        t.addData("Done", spline.done());
        t.addData("Current Segment", spline.getCurrentSegment());
        t.addLine();

        t.addLine("Spline Log:");
        t.addLine(spline.getLog());
        t.addLine("Segment Log: ");
        t.addLine(spline.getSegmentLog());
        t.update();
    }

    public String stringFollowPrint(double[] follow) {
        String returnStr = "";
        returnStr += "    X: " + follow[0] + "\n";
        returnStr += "    Y: " + follow[1] + "\n";
        returnStr += "    Rotate: " + follow[2] + "\n";
        return returnStr;
    }
}
