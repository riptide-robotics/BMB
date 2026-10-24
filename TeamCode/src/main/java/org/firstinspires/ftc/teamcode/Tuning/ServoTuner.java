package org.firstinspires.ftc.teamcode.Tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;


@Config
@TeleOp(name = "Servo Tuner")
public class ServoTuner extends LinearOpMode {

    public static double position = 0;
    Servo servo;

    @Override
    public void runOpMode() throws InterruptedException {
        servo = hardwareMap.servo.get("servo");

        waitForStart();

        while (opModeIsActive()) {
            servo.setPosition(position);
        }
    }
}
