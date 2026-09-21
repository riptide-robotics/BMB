package org.firstinspires.ftc.teamcode.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.RepeatedSequence;
import org.firstinspires.ftc.teamcode.Sequencer.SequencedOpMode;

@TeleOp(name = "IncrementationTest", group = "Sequencer Tests")
public class IncrementationTest extends SequencedOpMode {

    int i = 0;
    public RepeatedSequence seq = new RepeatedSequence((accessor) -> {
        telemetry.addData("Increment:",i++);
    },1000,1000);

    @Override
    public void onStart() {
        sequencer.addSequence(seq);
    }

    @Override
    public void onLoop() {

    }
}
