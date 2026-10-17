package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.teamcode.DummyClasses.Outtake;
import org.firstinspires.ftc.teamcode.Modules.Drivetrain;
import org.firstinspires.ftc.teamcode.DummyClasses.HorizontalSlides;
import org.firstinspires.ftc.teamcode.DummyClasses.Intake;

public class Robot {
    HardwareMap hardwareMap;
    Drivetrain drivetrain;
    HorizontalSlides horizontalSlides;
    Intake intake;
    Outtake outtake;
    public Robot (HardwareMap map){
        hardwareMap = map;
        drivetrain = new Drivetrain(hardwareMap);
        horizontalSlides = new HorizontalSlides(hardwareMap);
        intake = new Intake(hardwareMap);
        outtake = new Outtake(hardwareMap);
    }

    public Drivetrain getDrivetrain(){
        return drivetrain;
    }
    public HorizontalSlides getHorizontalSlides(){return horizontalSlides;}
    public Intake getIntake() {return intake;}
    public Outtake getOuttake() {return outtake;}
}
