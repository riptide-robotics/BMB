package org.firstinspires.ftc.teamcode.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.TimedSequenceBase;
import org.firstinspires.ftc.teamcode.Sequencer.SequencedOpMode;

@TeleOp(name = "KeyTest", group = "Sequencer Tests")
public class KeyTest extends SequencedOpMode {
    @Override
    public void onStart() {
        telemetry.addData("A",false);
    }

    @Override
    public void onLoop() {
        if (gamepad1.aWasPressed()) {
            sequencer.addSequence(new TimedSequenceBase((a) -> {
                telemetry.addData("A",true);
            }, 1000));
        }
    }
}
