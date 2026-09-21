package org.firstinspires.ftc.teamcode.Sequencer.Primary.CommonTests;

import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.RepeatedSequence;
import org.firstinspires.ftc.teamcode.Sequencer.SequencedOpMode;

public class MultiIncrementationTest extends SequencedOpMode {


    int i = 0;

    public RepeatedSequence seq = new RepeatedSequence((accessor) -> {
        telemetry.addData("Increment:", i > 16 ? "Too Old" : "" + i++);

    },500,500);

    @Override
    public void onStart() {
        sequencer.addSequence(seq);
    }

    @Override
    public void onLoop() {

    }
}
