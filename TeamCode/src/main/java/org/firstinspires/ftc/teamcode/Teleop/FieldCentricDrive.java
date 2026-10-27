package org.firstinspires.ftc.teamcode.Teleop;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;

@TeleOp(name = "Field Centric Drive")
public class FieldCentricDrive extends LinearOpMode {

    public Robot robot;

    @Override
    public void runOpMode() throws InterruptedException {

        robot = new Robot(hardwareMap);
        telemetry.addData("Robot status", "succesfully initiated");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        telemetry.clear();
        telemetry.addData("Robot status", "Started!");
        telemetry.update();

        while (opModeIsActive()){
            fieldCentricDrive();
            print();
        }
    }



    private void fieldCentricDrive() {
        double slowdown = gamepad1.right_trigger > 0 ? 0.25 : 1;
        double y = -gamepad1.left_stick_y * slowdown; // forward
        double x = gamepad1.left_stick_x * 1.1 * slowdown; // straff
        double rx = gamepad1.right_stick_x * slowdown; // rotation

        double heading = robot.getDrivetrain().getRobotHeading(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading); // goal vector relative to robot
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frWheelPower = (rotY - rotX - rx) / denominator; // wheel powers
        double flWheelPower = (rotY + rotX + rx) / denominator;
        double brWheelPower = (rotY + rotX - rx) / denominator;
        double blWheelPower = (rotY - rotX + rx) / denominator;

        robot.getDrivetrain().setWheelPowers(flWheelPower, frWheelPower, brWheelPower, blWheelPower);

        if (gamepad1.y) {
            robot.getDrivetrain().resetImu();
        }
    }

    private void print() {
        telemetry.addData("Left Stick x:", gamepad1.left_stick_x);
        telemetry.addData("Left Stick y:", gamepad1.left_stick_y);
        telemetry.addData("Right Stick x:", gamepad1.right_stick_x);
        telemetry.addData("Heading IMU:", robot.getDrivetrain().getRobotHeading(AngleUnit.RADIANS));
        telemetry.addData("Heading Pinpoint:", robot.getDrivetrain().getCurrPos().getH());
        telemetry.update();
    }
}
