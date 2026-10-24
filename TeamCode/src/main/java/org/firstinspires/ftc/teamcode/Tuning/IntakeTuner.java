package org.firstinspires.ftc.teamcode.Tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

@Config
@TeleOp(name="intake tuner")
public class IntakeTuner extends LinearOpMode {
    DcMotor intake;
    Servo intakeServo;
    public static double power = 0;
    public static double pos = 0;
    @Override
    public void runOpMode(){
        intake = hardwareMap.dcMotor.get("intakeMotor");
        intakeServo = hardwareMap.servo.get("intakeServo");
        waitForStart();
        while (opModeIsActive()){
            if (gamepad2.right_trigger > 0.1){
                intake.setPower(power);
            } else{
                intake.setPower(0);
            }

            intakeServo.setPosition(pos);

            telemetry.addData("Target Servo Pos", pos);
            telemetry.addData("Actual Servo Class", intakeServo.getClass().getSimpleName());
            telemetry.update();
        }
    }
}