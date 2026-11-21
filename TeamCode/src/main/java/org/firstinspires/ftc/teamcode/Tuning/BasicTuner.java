package org.firstinspires.ftc.teamcode.Tuning;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Modules.PIDController;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;

@Config
@TeleOp(name = "Basic Tuner")
public class BasicTuner extends SequencedOpMode {

    DcMotor motor;

    PIDController pid;

    public double p = 0;
    public double pOld = 0;
    public double i = 0;
    public double iOld = 0;
    public double d = 0;
    public double dOld = 0;
    public double k = 0;

    public double goal = 0;

    public double current = 0;

    @Override
    public void onStart() {
        motor = hardwareMap.dcMotor.get("UntunedMotor");

        pid = new PIDController(p,i,d);
    }

    @Override
    public void onLoop() {

        current = motor.getCurrentPosition();

        if (p != pOld || i != iOld || d != dOld) {
            pOld = p;
            iOld = i;
            dOld = d;
            pid = new PIDController(p,i,d);
        }

        motor.setPower(pid.calculate(current,goal) * k);
    }
}
