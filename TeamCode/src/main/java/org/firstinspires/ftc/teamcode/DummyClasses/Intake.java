package org.firstinspires.ftc.teamcode.DummyClasses;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Intake {
    DcMotor intake;
    Servo intakeServo;
    public Intake (HardwareMap hardwareMap){
        intake = hardwareMap.dcMotor.get("intake");
        intakeServo = hardwareMap.servo.get("intakeServo");
    }

    public void setPower(double power){
        intake.setPower(power);
    }

    public void setIntakePosition(double pos){
        intakeServo.setPosition(pos);
    }
}
