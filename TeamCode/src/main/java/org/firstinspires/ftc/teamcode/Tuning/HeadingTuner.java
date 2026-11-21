package org.firstinspires.ftc.teamcode.Tuning;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.teamcode.Modules.PIDController;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import org.firstinspires.ftc.teamcode.Robot;

@Config
@TeleOp(name = "Heading Tuner")
public class HeadingTuner extends LinearOpMode {

    public Robot robot;

    PIDController pid;

    public static double p = 0.07;
    public double pOld = 0;

    public static double i = 0.725;
    public double iOld = 0;

    public static double d = 0.00005;
    public double dOld = 0;

    public static double k = 0;
    public double kOld = 0;


    public static double goal = 0;

    public double current = 0;

    Telemetry t = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

    @Override
    public void runOpMode() throws InterruptedException {

        robot = new Robot(hardwareMap);
        pid = new PIDController(p,i,d);

        robot.getDrivetrain().startOdometry();

        telemetry.addData("Robot status", "succesfully initiated");
        telemetry.update();

        waitForStart();
        if (isStopRequested()) return;

        t.clear();
        t.addData("Robot status", "Started!");
        t.update();

        while (opModeIsActive()){
            current = robot.getDrivetrain().getRobotHeading(AngleUnit.DEGREES);

            if (p != pOld || i != iOld || d != dOld || kOld != k) {
                pOld = p;
                iOld = i;
                dOld = d;
                kOld = k;
                pid = new PIDController(p,i,d, k);
            }

            double error = AngleUnit.normalizeDegrees(goal - current);

            double power = pid.calculate(error, 0);

            print(power);

            robot.getDrivetrain().fieldCentricDrive(0, 0, power);
        }
    }

    void print(double power) {
        t.addData("Goal Pos", goal);
        t.addData("Current Pos", current);
        t.addData("PID Value", power);
        t.update();
    }
}
