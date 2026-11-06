package org.firstinspires.ftc.teamcode.DummyClasses;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {
    CRServo leftStingRay;
    CRServo rightStingRay;

    DcMotor blocker;

    public Outtake(HardwareMap hardwareMap){
        leftStingRay = hardwareMap.get(CRServo.class, "leftStingRay");
        rightStingRay = hardwareMap.get(CRServo.class, "rightStingRay");
        blocker = hardwareMap.dcMotor.get("blocker");
        rightStingRay.setDirection(DcMotorSimple.Direction.REVERSE);
    }

    public void setOuttakePower(double power){
        leftStingRay.setPower(power);
        rightStingRay.setPower(power);
    }

    public void setBlockerPower (double power){
        blocker.setPower(power);
    }
}
