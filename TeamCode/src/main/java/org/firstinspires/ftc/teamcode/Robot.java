package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

public class Robot {
    HardwareMap hardwareMap;
    Drivetrain drivetrain;
    public Robot (HardwareMap map){
        hardwareMap = map;
        drivetrain = new Drivetrain(hardwareMap);
    }

    public Drivetrain getDrivetrain(){
        return drivetrain;
    }
}
