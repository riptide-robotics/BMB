package org.firstinspires.ftc.teamcode.Tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@TeleOp(name = "Motor Tuner")
public class MotorTuner extends LinearOpMode {

    public ElapsedTime timer;
    public static double power;
    DcMotor motor;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.dcMotor.get("motor");

        waitForStart();
        timer.reset();
        timer.startTime();

        while (opModeIsActive() && timer.milliseconds() <= 3000) {
            motor.setPower(power);
        }

    }
}
