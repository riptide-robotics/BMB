package org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts.Meet1Auto.PID2pointAuto;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;
import org.firstinspires.ftc.teamcode.Robot;

@Config
@Autonomous (name = "More Auto testing")
public class MoreTestAuto extends LinearOpMode {

    // pidtoppint autron instance var
    public static PID2pointAuto toPoint;
    public static double slowdown;

    public Robot robot;


    @Override
    public void runOpMode() throws InterruptedException {
        toPoint = new PID2pointAuto();
        toPoint.addPoint(5, 4, 0);
        toPoint.addPoint(3, 9, 1.5);
        toPoint.addPoint(19, 4, 0);

        robot = new Robot(hardwareMap);
        robot.getDrivetrain().startOdometry();
        slowdown = 0.5;

        telemetry.addData("Robot status", "succesfully initiated");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        telemetry.clear();
        telemetry.addData("Robot status", "Started!");
        telemetry.update();

        while (opModeIsActive()){
            EditablePose2D pos = robot.getDrivetrain().getCurrPos();

            double[] wheelPowers = toPoint.run(
                    pos.getX(DistanceUnit.INCH),
                    pos.getY(DistanceUnit.INCH),
                    pos.getH()
            );

            telemetry.addData("IsDone: ", toPoint.isDone());

            robot.getDrivetrain().setWheelPowers(
                    wheelPowers[0] * slowdown,
                    wheelPowers[1] * slowdown,
                    wheelPowers[2] * slowdown,
                    wheelPowers[3] * slowdown
            );


        }
    }


}
