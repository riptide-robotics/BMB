package org.firstinspires.ftc.teamcode.Modules.Sequencer;

//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.Robot;


/**
 * A variant of OpMode that has the sequencer system incorporated within. <br>
 *  Note that usage is not identical, relying on #onStart(), #onLoop(), and #onStop().
 *  */
public abstract class SequencedOpMode extends LinearOpMode {

    public Sequencer sequencer;
//    public MultipleTelemetry mtele;
    public Robot robot;
    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);
        sequencer = new Sequencer();
        sequencer.tele = new SequencedTelemetry(this);
//        mtele = new MultipleTelemetry(telemetry);

        onStart();

        if (isStopRequested()) return;
        waitForStart();

        while (opModeIsActive()) {

            sequencer.iterate();
            onLoop();
            telemetry.update();
////            mtele.update();
        }
        onStop();
    }

    public abstract void onStart();
    public abstract void onLoop();
    public void onStop() {};
}
