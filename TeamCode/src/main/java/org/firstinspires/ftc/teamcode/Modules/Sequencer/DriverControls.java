package org.firstinspires.ftc.teamcode.Modules.Sequencer;

import com.qualcomm.robotcore.hardware.Gamepad;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;
import org.firstinspires.ftc.teamcode.Robot;

public class DriverControls {
    Robot robot;

    Gamepad gamepad;

    public DriverControls(Robot robot, Gamepad gamepad) {
        this.robot = robot;
        this.gamepad = gamepad;
    }

    public void fieldCentricDrive(){

        if (robot.getDrivetrain() == null) return; //wait until it is initialized

        double slowdown = gamepad.right_trigger > 0 ? 0.25 : 1.0;
        double y = -gamepad.right_stick_y * slowdown;
        double x = gamepad.right_stick_x * 1.1 * slowdown;
        double rx = gamepad.left_stick_x * slowdown;

        double heading = robot.getDrivetrain().getRobotHeading(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1.0);
        double frWheelPower = (rotY - rotX - rx) / denominator;
        double flWheelPower = (rotY + rotX + rx) / denominator;
        double brWheelPower = (rotY + rotX - rx) / denominator;
        double blWheelPower = (rotY - rotX + rx) / denominator;

        robot.getDrivetrain().setWheelPowers(flWheelPower, frWheelPower, brWheelPower, blWheelPower);

        if (gamepad.y) {
            robot.getDrivetrain().resetImu();
        }
    }
    public void driveToPos(EditablePose2D desired) {
        EditablePose2D current = robot.getDrivetrain().getCurrPos();

        double y = desired.getY(DistanceUnit.INCH) - current.getX(DistanceUnit.INCH);
        double x = desired.getX(DistanceUnit.INCH) - current.getX(DistanceUnit.INCH);

        if (x * x + y * y < 1) return;


        double heading = (desired.getH() - current.getH()) % (2 * Math.PI);

        while (heading < -Math.PI) heading += Math.PI;
        while (heading > Math.PI) heading -= Math.PI;



        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(heading), 1.0);
        double frWheelPower = (rotY - rotX - heading) / denominator;
        double flWheelPower = (rotY + rotX + heading) / denominator;
        double brWheelPower = (rotY + rotX - heading) / denominator;
        double blWheelPower = (rotY - rotX + heading) / denominator;

        robot.getDrivetrain().setWheelPowers(flWheelPower, frWheelPower, brWheelPower, blWheelPower);




    }
}
