package org.firstinspires.ftc.teamcode.Modules;

// ----- READY TO TRANSFER ----- //
// I don't think we will ever need to change this

// NEED TO ADD TUNED VOLTAGE

public class PIDController {

    private double kp;
    private double ki;
    private double kd;
    private double kf;
    private double tunedVoltage = 11.5;


    private double pError;
    private double dError;
    private double iError;

    private double previousError = 0;
    private double elapsedTime = 0;

    private double deadZone = 0;

    // depends if you want to make a "atGoal" method
//    private double pTolorance = 0.05;
//    private double dTolerance = Double.POSITIVE_INFINITY;

    // because motors only output powers for -1 to 1
    private double integralCeil = 0.5;

    private double lastTime = 0;

    private boolean usingExternalClock;

    public PIDController(double kp, double ki, double kd){
        this(kp, ki, kd, false);
    }
    public PIDController(double kp, double ki, double kd, double kf){
        this(kp, ki, kd, kf, false);
    }

    public PIDController(double kp, double ki, double kd, double kf, double tunedVoltage){
        this(kp, ki, kd, kf, tunedVoltage, false);
    }

    public PIDController(double kp, double ki, double kd, boolean usingExternalClock){
        this.kd = kd;
        this.kp = kp;
        this.ki = ki;
        this.usingExternalClock = usingExternalClock;
    }

    public PIDController(double kp, double ki, double kd, double kf, boolean usingExternalClock){
        this.kd = kd;
        this.kp = kp;
        this.ki = ki;
        this.kf = kf;
        this.usingExternalClock = usingExternalClock;
    }

    public PIDController(double kp, double ki, double kd, double kf, double tunedVoltage, boolean usingExternalClock){
        this.kd = kd;
        this.kp = kp;
        this.ki = ki;
        this.kf = kf;
        this.tunedVoltage = tunedVoltage;
        this.usingExternalClock = usingExternalClock;
    }

    public void setDeadZone(double d){
        deadZone = d;
    }

    public double calculate(double currPos, double goal){
        if(usingExternalClock){throw new SecurityException("Using an externalClock!!");}
        if (Math.abs(currPos - goal) <= deadZone){return 0;}

        double currentTime = (double) System.nanoTime() / 1E9;

        if (lastTime == 0) lastTime = currentTime;

        elapsedTime = currentTime - lastTime;
        lastTime = currentTime;

        pError = goal - currPos;

        //Filter out really small times
        if (Math.abs(elapsedTime) > 1E-6) {
            dError = (pError - previousError) / elapsedTime;
        } else {
            dError = 0;
        }

        iError += elapsedTime * (pError);
        iError = Math.abs(iError) > integralCeil ? integralCeil : iError;

        previousError = pError;

        return kf + kp * pError + kd * dError + ki * iError;
    }

    /**
     * @param currPos encoderTicks
     * @param goal encoder ticks
     * @param currentTime Time should be in seconds
     * @return output powers
     */
    public double calculate(double currPos, double goal, double currentTime){
        if (!usingExternalClock) {usingExternalClock = true;}
        else { throw new RuntimeException("not using an externalClock!");}

        if (lastTime == 0) lastTime = currentTime;

        elapsedTime = currentTime - lastTime;
        lastTime = currentTime;

        pError = goal - currPos;

        //Filter out really small times
        if (Math.abs(elapsedTime) > 1E-6) {
            dError = (pError - previousError) / elapsedTime;
        } else {
            dError = 0;
        }

        iError += elapsedTime * (pError);
        iError = Math.abs(iError) > integralCeil ? integralCeil : iError;

        return kf + kp * pError + kd * dError + ki * iError;
    }

    public void reset(){
        lastTime = 0;
    }

    // Method to tune the PID gains
    public void setPID(double kP, double ki, double kd) {
        this.kp = kP;
        this.ki = ki;
        this.kd = kd;
    }

    public void setPID(double kP, double ki, double kd, double kf) {
        this.kp = kP;
        this.ki = ki;
        this.kd = kd;
        this.kf = kf;
    }

    public void resetIntegral(){
        iError = 0;
    }

}
