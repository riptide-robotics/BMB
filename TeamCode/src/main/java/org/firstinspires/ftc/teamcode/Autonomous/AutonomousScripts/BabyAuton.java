package org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@Autonomous(name = "Baby Auton")
public class BabyAuton extends LinearOpMode {

    public static double speed = 1;
    public ElapsedTime timer;
    DcMotor flWheel;
    DcMotor frWheel;
    DcMotor blWheel;
    DcMotor brWheel;




    @Override
    public void runOpMode() throws InterruptedException {
        flWheel = hardwareMap.dcMotor.get("flWheel");
        frWheel = hardwareMap.dcMotor.get("frWheel");
        blWheel = hardwareMap.dcMotor.get("blWheel");
        brWheel = hardwareMap.dcMotor.get("brWheel");
        timer = new ElapsedTime();
        waitForStart();
        timer.reset();
        timer.startTime();

        while (opModeIsActive()) {

            if (timer.milliseconds() <= 500) {
                flWheel.setPower(speed);
                frWheel.setPower(-speed);
                blWheel.setPower(-speed);
                brWheel.setPower(speed);
            }
            else {
                flWheel.setPower(0);
                frWheel.setPower(0);
                blWheel.setPower(0);
                brWheel.setPower(0);
            }

        }
    }
}
