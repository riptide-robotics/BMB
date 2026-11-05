package org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts.Meet1Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
@Config
@TeleOp(name = "Pinpoint Position Hold")
public class PinpointPositionHold extends LinearOpMode {

    private DcMotor flWheel, blWheel, frWheel, brWheel;
    private GoBildaPinpointDriver pinpoint;

    private double targetX = 0;
    private double targetY = 0;
    private double targetHeading = 0;
    private boolean wasHolding = false;

    private final double P_TRANSLATION = 0.05;
    private final double P_HEADING = 2.0;

    @Override
    public void runOpMode() {
        flWheel = hardwareMap.get(DcMotor.class, "flWheel");
        blWheel = hardwareMap.get(DcMotor.class, "blWheel");
        frWheel = hardwareMap.get(DcMotor.class, "frWheel");
        brWheel = hardwareMap.get(DcMotor.class, "brWheel");

        flWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        blWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        frWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        brWheel.setDirection(DcMotorSimple.Direction.FORWARD);

        flWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        blWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        frWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

            pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.REVERSED, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.setOffsets(0.0, 5.0, DistanceUnit.INCH);
        pinpoint.resetPosAndIMU();

        telemetry.addData("Status", "Initialized. Waiting for start...");
        telemetry.update();

        waitForStart();

        while (opModeIsActive()) {
            pinpoint.update();

            if (gamepad1.a) {
                if (!wasHolding) {
                    targetX = pinpoint.getPosX(DistanceUnit.INCH);
                    targetY = pinpoint.getPosY(DistanceUnit.INCH);
                    targetHeading = pinpoint.getHeading(AngleUnit.RADIANS);
                    wasHolding = true;
                }

                double currentX = pinpoint.getPosX(DistanceUnit.INCH);
                double currentY = pinpoint.getPosY(DistanceUnit.INCH);
                double currentHeading = pinpoint.getHeading(AngleUnit.RADIANS);

                double errorX = targetX - currentX;
                double errorY = targetY - currentY;
                double errorHeading = targetHeading - currentHeading;

                while (errorHeading > Math.PI) errorHeading -= 2 * Math.PI;
                while (errorHeading < -Math.PI) errorHeading += 2 * Math.PI;

                double fx = errorX * P_TRANSLATION;
                double fy = errorY * P_TRANSLATION;
                double turn = errorHeading * P_HEADING;

                double rotX = fx * Math.cos(-currentHeading) - fy * Math.sin(-currentHeading);
                double rotY = fx * Math.sin(-currentHeading) + fy * Math.cos(-currentHeading);

                double lfPower = rotY + rotX + turn;
                double lrPower = rotY - rotX + turn;
                double rfPower = rotY - rotX - turn;
                double rrPower = rotY + rotX - turn;

                double max = Math.max(Math.max(Math.abs(lfPower), Math.abs(lrPower)),
                        Math.max(Math.abs(rfPower), Math.abs(rrPower)));
                if (max > 1.0) {
                    lfPower /= max;
                    lrPower /= max;
                    rfPower /= max;
                    rrPower /= max;
                }

                flWheel.setPower(lfPower);
                blWheel.setPower(lrPower);
                frWheel.setPower(rfPower);
                brWheel.setPower(rrPower);

                telemetry.addData("Mode", "HOLDING POSITION");
                telemetry.addData("Target (X, Y)", "%.1f, %.1f", targetX, targetY);

            } else {
                wasHolding = false;

                double y = -gamepad1.left_stick_y;
                double x = gamepad1.left_stick_x;
                double rx = gamepad1.right_stick_x;

                double lfPower = y + x + rx;
                double lrPower = y - x + rx;
                double rfPower = y - x - rx;
                double rrPower = y + x - rx;

                double max = Math.max(Math.max(Math.abs(lfPower), Math.abs(lrPower)),
                        Math.max(Math.abs(rfPower), Math.abs(rrPower)));
                if (max > 1.0) {
                    lfPower /= max;
                    lrPower /= max;
                    rfPower /= max;
                    rrPower /= max;
                }

                flWheel.setPower(lfPower);
                blWheel.setPower(lrPower);
                frWheel.setPower(rfPower);
                brWheel.setPower(rrPower);

                telemetry.addData("Mode", "NORMAL DRIVING");
            }

            telemetry.addData("Pinpoint X (in)", pinpoint.getPosX(DistanceUnit.INCH));
            telemetry.addData("Pinpoint Y (in)", pinpoint.getPosY(DistanceUnit.INCH));
            telemetry.addData("Pinpoint Heading (deg)", pinpoint.getHeading(AngleUnit.DEGREES));
            telemetry.update();
        }
    }
}