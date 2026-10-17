package org.firstinspires.ftc.teamcode.Tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Config
@TeleOp(name="intake tuner")
public class IntakeTuner extends LinearOpMode {
    DcMotor intake;
    public static double power = 0;
    @Override
    public void runOpMode(){
        intake = hardwareMap.dcMotor.get("intake");
        waitForStart();
        while (opModeIsActive()){
            if (gamepad2.right_trigger > 0.1){
                intake.setPower(power);
            } else{
                intake.setPower(0);
            }
        }
    }
}