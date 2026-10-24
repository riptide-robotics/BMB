package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Modules.Drivetrain;
import org.firstinspires.ftc.teamcode.Modules.Utils.GoBildaPinpointDriver;

public class Robot {
    HardwareMap hardwareMap;
    Drivetrain drivetrain = null;
    public Robot (HardwareMap map){
        hardwareMap = map;
        if (map.tryGet(GoBildaPinpointDriver.class,"odo") != null) drivetrain = new Drivetrain(map);
    }

    public Drivetrain getDrivetrain(){

        return drivetrain;
    }
}
