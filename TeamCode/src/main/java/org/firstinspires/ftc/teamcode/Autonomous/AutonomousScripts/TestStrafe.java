package org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline.Spline;
import org.firstinspires.ftc.teamcode.Autonomous.Utils.Spline.Waypoint;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;
import org.firstinspires.ftc.teamcode.Robot;

@Config
@Autonomous(name = "Test Strafing", group = "Test")
public class TestStrafe extends LinearOpMode {
    public Robot robot;
    private Spline spline;
    public static double slowdown;

    Telemetry t = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());
    @Override
    public void runOpMode() throws InterruptedException {

        spline = new Spline.SplineBuilder()
                .add(new EditablePose2D(0, 0, 0, DistanceUnit.INCH), 1, 0)
                .add(new EditablePose2D(10, 0, 0, DistanceUnit.INCH))
                .add(new EditablePose2D(0, 0, 0, DistanceUnit.INCH), -1, 0)
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

            t.addData("Spline: ", spline.print());
            t.addData("Follow: \n", stringFollowPrint(moveArray));
            t.addData("Done", spline.done());
            t.addData("Current Segment: ", spline.getCurrentSegment());
            t.addData("Spline Log:\n", spline.getLog());
            t.addData("\nSegment Log:\n", spline.getSegmentLog());
            t.update();
            spline.clearLog();
            spline.clearSegmentLog();

            fieldCentricDrive(moveArray[0], moveArray[1], moveArray[2]);
        }
    }

    private void fieldCentricDrive(double y, double x, double rx) {
        double heading = robot.getDrivetrain().getRobotHeading(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading); // goal vector relative to robot
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frWheelPower = (rotY - rotX - rx) / denominator; // wheel powers
        double flWheelPower = (rotY + rotX + rx) / denominator;
        double brWheelPower = (rotY + rotX - rx) / denominator;
        double blWheelPower = (rotY - rotX + rx) / denominator;

        robot.getDrivetrain().setWheelPowers(
                flWheelPower * slowdown,
                frWheelPower * slowdown,
                brWheelPower * slowdown,
                blWheelPower * slowdown
        );
    }

    public String stringFollowPrint(double[] follow) {
        String returnStr = "";
        returnStr += "    X: " + follow[0] + "\n";
        returnStr += "    Y: " + follow[1] + "\n";
        returnStr += "    Rotate: " + follow[2] + "\n";
        return returnStr;
    }
}
