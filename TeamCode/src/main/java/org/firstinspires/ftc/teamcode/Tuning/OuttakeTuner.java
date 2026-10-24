package org.firstinspires.ftc.teamcode.Tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

@Config
@TeleOp(name="outtake tuner")
public class OuttakeTuner extends LinearOpMode {
    CRServo leftStingRay;
    CRServo rightStingRay;
    public static double powerL = 0;
    public static double powerR = 0;
    public static double power = 0;
    @Override
    public void runOpMode() {
        leftStingRay = hardwareMap.get(CRServo.class, "leftStingRay");
        rightStingRay = hardwareMap.get(CRServo.class, "rightStingRay");
        rightStingRay.setDirection(DcMotorSimple.Direction.REVERSE);
        waitForStart();
        while(opModeIsActive()){
            if (gamepad2.right_trigger > 0.1){
                rightStingRay.setPower(powerR);
            } else {
                rightStingRay.setPower(0);
            }
            if (gamepad2.left_trigger > 0.1){
                leftStingRay.setPower(powerL);
            } else {
                leftStingRay.setPower(0);
            }

            if (gamepad2.b){
                leftStingRay.setPower(power);
                rightStingRay.setPower(power);
            }
        }
    }
}
