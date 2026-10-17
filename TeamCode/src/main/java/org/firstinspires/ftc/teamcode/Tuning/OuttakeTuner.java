package org.firstinspires.ftc.teamcode.Tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name="outtake tuner")
public class OuttakeTuner extends LinearOpMode {
    CRServo outtake;
    public static double power = 0;
    @Override
    public void runOpMode() {
        outtake = hardwareMap.get(CRServo.class, "kicker");
        waitForStart();
        while(opModeIsActive()){
            if (gamepad2.right_trigger > 0.1){
                outtake.setPower(power);
            } else {
                outtake.setPower(0);
            }
        }
    }
}
