package org.firstinspires.ftc.teamcode.DummyClasses;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.HardwareMap;

public class Outtake {
    CRServo outtake;
    public Outtake(HardwareMap hardwareMap){
        outtake = hardwareMap.get(CRServo.class, "kicker");
    }

    public void setOuttakePower(double power){
        outtake.setPower(power);
    }
}
