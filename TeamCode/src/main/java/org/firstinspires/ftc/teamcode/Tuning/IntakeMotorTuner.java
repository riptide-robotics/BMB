package org.firstinspires.ftc.teamcode.Tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

@Config
@TeleOp(name = "Intake Motor Tuner")
public class IntakeMotorTuner extends LinearOpMode {
    public static double power;
    ElapsedTime timer;
    DcMotor motor;

    @Override
    public void runOpMode() throws InterruptedException {
        motor = hardwareMap.dcMotor.get("motor");

        timer.reset();
        waitForStart();
        timer.startTime();

        while (timer.milliseconds() < 3000 && opModeIsActive()) motor.setPower(power);
    }
}
