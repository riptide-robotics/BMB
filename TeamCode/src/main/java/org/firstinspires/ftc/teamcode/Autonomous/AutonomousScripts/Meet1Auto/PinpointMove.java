package org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts.Meet1Auto;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.hardware.gobilda.GoBildaPinpointDriver;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.jetbrains.annotations.Nullable;

public class PinpointMove {

    private DcMotor flWheel, blWheel, frWheel, brWheel;
    private GoBildaPinpointDriver pinpoint;

    public static double P_TRANSLATION = 0.1;
    public static double P_HEADING = 2.0;

    Telemetry tele;

    public PinpointMove() {};
    public PinpointMove(HardwareMap hardwareMap, @Nullable Telemetry tele) {
        initalize(hardwareMap,tele);
    }


    public void initalize(HardwareMap hardwareMap, @Nullable Telemetry tele) {
        flWheel = hardwareMap.get(DcMotor.class, "flWheel");
        blWheel = hardwareMap.get(DcMotor.class, "blWheel");
        frWheel = hardwareMap.get(DcMotor.class, "frWheel");
        brWheel = hardwareMap.get(DcMotor.class, "brWheel");

        this.tele = tele;

        pinpoint = hardwareMap.get(GoBildaPinpointDriver.class, "pinpoint");

        pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        pinpoint.setOffsets(0.0, 3.5, DistanceUnit.INCH);
        pinpoint.resetPosAndIMU();
    }
    public void loop(boolean drive, double targetX, double targetY, double targetHeading) {
        if (!drive) return;

        double currentX = pinpoint.getPosX(DistanceUnit.INCH);
        double currentY = pinpoint.getPosY(DistanceUnit.INCH);
        double currentHeading = pinpoint.getHeading(AngleUnit.RADIANS);

        double errorX = targetX - currentX;
        double errorY = targetY - currentY;
        double errorHeading = targetHeading - currentHeading;

        while (errorHeading > Math.PI) errorHeading -= 2 * Math.PI;
        while (errorHeading < -Math.PI) errorHeading += 2 * Math.PI;

        double fx = errorY * P_TRANSLATION;
        double fy = -errorX * P_TRANSLATION;
        double turn = errorHeading * P_HEADING;

        double rotX = fx * Math.cos(currentHeading) + fy * Math.sin(currentHeading);
        double rotY = -fx * Math.sin(currentHeading) + fy * Math.cos(currentHeading);

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

        if (tele != null) {
            tele.addData("Mode", "GOING TO POSITION");
            tele.addData("Target (X, Y)", "%.1f, %.1f", targetX, targetY);
        }
    }

}