package org.firstinspires.ftc.teamcode.Modules;

import static java.lang.Thread.sleep;

import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.IMU;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;
import org.firstinspires.ftc.teamcode.Modules.Utils.GoBildaPinpointDriver;

public class Drivetrain {

    private final DcMotor frWheel, flWheel, brWheel, blWheel;
    private final GoBildaPinpointDriver pinpoint;
    private final IMU imu;
    private ElapsedTime timer;

    private final OdometryLocalizer robotPos;
    private final OdometryLocalizer.OdometryMethod localizationMode = OdometryLocalizer.OdometryMethod.PINPOINT;

    private double targetX = 0;
    private double targetY = 0;
    private double targetHeading = 0;

    public static double P_TRANSLATION = 0.1;
    public static double P_HEADING = 2.0;

    private double waypointX = 0;
    private double waypointY = 0;
    private double waypointHeading = 0;

    public Drivetrain(HardwareMap hardwareMap) {
        frWheel = hardwareMap.dcMotor.get("frWheel");
        flWheel = hardwareMap.dcMotor.get("flWheel");
        brWheel = hardwareMap.dcMotor.get("brWheel");
        blWheel = hardwareMap.dcMotor.get("blWheel");

        blWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        flWheel.setDirection(DcMotorSimple.Direction.REVERSE);
        frWheel.setDirection(DcMotorSimple.Direction.FORWARD);
        brWheel.setDirection(DcMotorSimple.Direction.FORWARD);

        brWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        blWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        flWheel.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        blWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        flWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        brWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frWheel.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        frWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        brWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        flWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        blWheel.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        if(localizationMode == OdometryLocalizer.OdometryMethod.PINPOINT){
            this.pinpoint = hardwareMap.get(GoBildaPinpointDriver.class,"pinpoint");
            pinpoint.setOffsets(0, 3.5, DistanceUnit.INCH);
            pinpoint.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
            pinpoint.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
            robotPos = new OdometryLocalizer(this.pinpoint, 10);
            pinpoint.resetPosAndIMU();
        } else {
            this.pinpoint = null;
            robotPos = new OdometryLocalizer(blWheel, brWheel, flWheel, 10);
        }

        imu = hardwareMap.get(IMU.class, "imu");
        IMU.Parameters parameters = new IMU.Parameters(new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.DOWN,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD));

        imu.resetYaw();
        imu.initialize(parameters);
    }

    public void resetImu() {
        imu.resetYaw();
    }

    public double getRobotHeading(AngleUnit unit) {
        return imu.getRobotYawPitchRollAngles().getYaw(unit);
    }

    public void setWheelPowers(double flWheelPower, double frWheelPower, double brWheelPower, double blWheelPower) {
        frWheel.setPower(frWheelPower);
        flWheel.setPower(flWheelPower);
        brWheel.setPower(brWheelPower);
        blWheel.setPower(blWheelPower);
    }

    public EditablePose2D getCurrPos() {
        return robotPos.getCurrPos();
    }

    public void startOdometry() {
        Thread localizer = new Thread(robotPos);
        localizer.start();
    }

    public OdometryLocalizer getRobotPos() {
        return robotPos;
    }

    public GoBildaPinpointDriver getPinpoint() {
        return pinpoint;
    }

    public void update() {
        if (pinpoint != null) {
            pinpoint.update();
        }
    }

    public double getTargetX() {
        return targetX;
    }

    public void setTargetX(double targetX) {
        this.targetX = targetX;
    }

    public double getTargetY() {
        return targetY;
    }

    public void setTargetY(double targetY) {
        this.targetY = targetY;
    }

    public double getTargetHeading() {
        return targetHeading;
    }

    public void setTargetHeading(double targetHeading) {
        this.targetHeading = targetHeading;
    }

    public void HoldPosition(boolean wasHolding){
        if (!wasHolding) {
            targetX = pinpoint.getPosX(DistanceUnit.INCH);
            targetY = pinpoint.getPosY(DistanceUnit.INCH);
            targetHeading = pinpoint.getHeading(AngleUnit.RADIANS);
        }

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
    }

    public void SetWayPoint(){
        waypointX = pinpoint.getPosX(DistanceUnit.INCH);
        waypointY = pinpoint.getPosY(DistanceUnit.INCH);
        waypointHeading = pinpoint.getHeading(AngleUnit.RADIANS);
    }

    public void GoToWayPoint(){
        targetX = waypointX;
        targetY = waypointY;
        targetHeading = waypointHeading;

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
    }
}