package org.firstinspires.ftc.teamcode.Modules;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareDevice;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class EncoderMotor {
    // https://docs.google.com/presentation/d/15hiayy1XQD8Y_2gFYvPoHPRe8F1i1ZBmPSsw_-aaEP4/edit?slide=id.g40a0aadc85c_0_49#slide=id.g40a0aadc85c_0_49
    // what to actually do to implement this

    private ElapsedTime lastTickTime;
    private DcMotor motor;
    private PIDController controller;
    private boolean usingPositionPID = false;
    private double startVal;

    private double changePower;

    public EncoderMotor(HardwareMap hardwareMap, String name, double kf, double kp, double ki, double kd, double startVal, double tunedVoltage) {
        this.lastTickTime = new ElapsedTime();
        this.motor = hardwareMap.dcMotor.get(name);
        this.controller = new PIDController(kp, ki, kd, kf, tunedVoltage);
        this.startVal = startVal;
        this.changePower = 0;
    }

    public void setToUsingPosition() {
        this.usingPositionPID = true;
    }

    /**
     * javadocs
     */
    public double convertEncoderTicks() {
        // @Joseph
        // you do this
        // add javadocs as well
        return 0;
    }
}
