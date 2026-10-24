package org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.RepeatedSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;

@TeleOp(name = "HalfIncrementationTest", group = "Sequencer Tests")
public class HalfIncrementationTest extends SequencedOpMode {

    int i = 0;

    public RepeatedSequence seq = new RepeatedSequence((accessor) -> {
        i++;
    },500,500);

    @Override
    public void onStart() {
        sequencer.addSequence(seq);
    }

    @Override
    public void onLoop() {
        telemetry.addData("Increment:", i);
    }
}
