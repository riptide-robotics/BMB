package org.firstinspires.ftc.teamcode.Teleop;

import static org.firstinspires.ftc.teamcode.riptideUtil.intakePower;
import static org.firstinspires.ftc.teamcode.riptideUtil.intakeServoDown;
import static org.firstinspires.ftc.teamcode.riptideUtil.intakeServoUp;
import static org.firstinspires.ftc.teamcode.riptideUtil.outtakePower;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Robot;

@Config
@TeleOp(name = "Meet 0 FSM")
public class Meet0FSM extends LinearOpMode {

    Robot robot;

    boolean hasrun = false;
    boolean wasHolding = false;

    public enum states{
        IDLE,
        INTAKE,
        OUTTAKE
    }

    public states currentState = states.IDLE;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        hasrun = false;
        telemetry.addData("Robot status:", "succesfully initiated");
        telemetry.update();
        robot.getIntake().setIntakePosition(intakeServoUp);
        waitForStart();
        if (isStopRequested()) return;

        telemetry.clear();
        telemetry.addData("Robot status", "Started!");
        telemetry.update();

        while(opModeIsActive()){
            robot.getDrivetrain().update();
            telemetry.addData("Current State:", currentState);
            telemetry.update();
            FSM();
        }
    }

    private void FSM(){
        switch(currentState){
            case IDLE:

                if (!hasrun){
                    robot.getIntake().setIntakePosition(intakeServoDown);
                    hasrun = true;
                }

                if (gamepad2.right_trigger > 0.1){
                    robot.getIntake().setPower(intakePower);
                } else if (gamepad2.back){
                    robot.getIntake().setPower(-intakePower);
                } else {
                    robot.getIntake().setPower(0);
                }

                if (gamepad2.left_trigger > 0.1){
                    robot.getOuttake().setOuttakePower(outtakePower);
                } else if (gamepad2.start){
                    robot.getOuttake().setOuttakePower(-outtakePower);
                } else {
                    robot.getOuttake().setOuttakePower(0);
                }

                if (gamepad2.right_bumper){
                    robot.getIntake().setIntakePosition(intakeServoDown);
                }
                if (gamepad2.left_bumper){
                    robot.getIntake().setIntakePosition(intakeServoUp);
                }

                if (gamepad1.a){
                    robot.getDrivetrain().SetWayPoint();
                    telemetry.addData("Mode", "SETTING WAYPOINT");
                } else if (gamepad1.b){
                    robot.getDrivetrain().GoToWayPoint();
                    telemetry.addData("Mode", "GOING TO WAYPOINT");
                } else {
                    fieldCentricDrive();
                    telemetry.addData("Mode", "NORMAL DRIVING");
                }

                if (gamepad2.x){
                    robot.getOuttake().setBlockerPower(1);
                } else if (gamepad2.b){
                    robot.getOuttake().setBlockerPower(-1);
                } else {
                    robot.getOuttake().setBlockerPower(0);
                }



                break;
        }

        if (!gamepad1.a){
            wasHolding = false;
        }
    }

    private void fieldCentricDrive() {
        double slowdown = gamepad1.right_trigger > 0 ? 0.25 : 1;
        double y = -gamepad1.left_stick_y * slowdown;
        double x = gamepad1.left_stick_x * slowdown;
        double rx = -gamepad1.right_stick_x * slowdown;

        double heading = robot.getDrivetrain().getRobotHeading(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1);
        double frWheelPower = (rotY - rotX - rx) / denominator;
        double flWheelPower = (rotY + rotX + rx) / denominator;
        double brWheelPower = (rotY + rotX - rx) / denominator;
        double blWheelPower = (rotY - rotX + rx) / denominator;

        robot.getDrivetrain().setWheelPowers(flWheelPower, frWheelPower, brWheelPower, blWheelPower);

        if (gamepad1.y) {
            robot.getDrivetrain().resetImu();
        }
    }
}