package org.firstinspires.ftc.teamcode.Tuning;

import org.firstinspires.ftc.teamcode.Modules.PIDController;

public class VelocityPIDTuner extends PIDController {

    private double bank = 0;
    public VelocityPIDTuner(double kp, double ki, double kd) {
        super(kp, ki, kd);
    }

    @Override
    public double calculate(double currPos, double goal) {
        bank += super.calculate(currPos, goal);
        return bank;
    }

    @Override
    public double calculate(double currPos, double goal, double currentTime) {
        bank += super.calculate(currPos, goal, currentTime);
        return bank;
    }

    public VelocityPIDTuner(double kp, double ki, double kd, boolean usingExternalClock){

        super(kp, ki, kd, false);
    }

    public VelocityPIDTuner(double kp, double ki, double kd, double kf){
        super(kp, ki, kd, kf, false);
    }

    public VelocityPIDTuner(double kp, double ki, double kd, double kf, double tunedVoltage){
        super(kp, ki, kd, kf, tunedVoltage, false);
    }

    public VelocityPIDTuner(double kp, double ki, double kd, double kf, boolean usingExternalClock){
        super(kp, ki, kd, kf, false);
    }

    public VelocityPIDTuner(double kp, double ki, double kd, double kf, double tunedVoltage, boolean usingExternalClock){
        super(kp, ki, kd, kf, tunedVoltage, false);

    }
}
