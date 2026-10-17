package org.firstinspires.ftc.teamcode.DummyClasses;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class HorizontalSlides {
    Servo leftSlide;

    public HorizontalSlides(HardwareMap hardwareMap){
        leftSlide = hardwareMap.servo.get("slides");
    }

    public void retractLeftSlide(double pos){
        leftSlide.setPosition(pos);
    }

    public void extendLeftSlide(double pos){
        leftSlide.setPosition(pos);
    }

}
