package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.Modules.Drivetrain;
import org.firstinspires.ftc.teamcode.Modules.Utils.GoBildaPinpointDriver;

public class Robot {
    Drivetrain drivetrain;
    public Robot (HardwareMap map){
        drivetrain = new Drivetrain(map);
        drivetrain.resetImu();
        //if (map.tryGet(GoBildaPinpointDriver.class,"pinpoint") != null) drivetrain = new Drivetrain(map);
    }

    public Drivetrain getDrivetrain(){
        return drivetrain;
    }
}
