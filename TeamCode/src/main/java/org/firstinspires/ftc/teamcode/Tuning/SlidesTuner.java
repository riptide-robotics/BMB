package org.firstinspires.ftc.teamcode.Tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name="slides tuner")
public class SlidesTuner extends LinearOpMode {
    final double extended = 0;
    final double retracted = 0.43;
    public static double pos = 0.43;
    Servo leftSlide;
    @Override
    public void runOpMode(){
        leftSlide = hardwareMap.servo.get("slides");
        waitForStart();
        while(opModeIsActive()){

        }
    }
}
