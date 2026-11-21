package org.firstinspires.ftc.teamcode.UnitTests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts.Meet1Auto.PinpointMove;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;

public class UnitTest extends SequencedOpMode {
    // https://docs.google.com/presentation/d/15hiayy1XQD8Y_2gFYvPoHPRe8F1i1ZBmPSsw_-aaEP4/edit?slide=id.h2bb3d5c7be0bd53a_3_29#slide=id.h2bb3d5c7be0bd53a_3_29

    PinpointMove move = new PinpointMove();
    private long lastTime = Long.MIN_VALUE;

    int inst = 0;

    Double[][] points = new Double[][] {{1.0,4.0,1.5},{4.0,2.0,1.2},{6.0,2.0,0.6},{7.0,2.0,2.0},{1.0,6.0,0.2}};

    double tarX, tarY, tarH;

    @Override
    public void onStart() {
        move.initalize(hardwareMap,telemetry);
    }

    @Override
    public void onLoop() {

        if (System.nanoTime() - 5000 > lastTime) {
            tarX = points[inst][0];
            tarY = points[inst][1];
            tarH = points[inst][2];

            inst++;
            lastTime = System.nanoTime();
        }

        move.loop(gamepad1.left_bumper, tarX,tarY,tarH);

        if (gamepad1.yWasPressed()) tarY++;
        if (gamepad1.aWasPressed()) tarY--;
        if (gamepad1.xWasPressed()) tarX--;
        if (gamepad1.bWasPressed()) tarX++;

        if (gamepad1.leftTriggerWasPressed()) tarH -= Math.PI / 8;
        if (gamepad1.rightTriggerWasPressed()) tarH += Math.PI / 8;


    }
}
