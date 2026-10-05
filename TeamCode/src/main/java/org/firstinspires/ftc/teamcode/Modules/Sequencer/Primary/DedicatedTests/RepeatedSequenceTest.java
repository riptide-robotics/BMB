package org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.DedicatedTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.RepeatedSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;

@TeleOp(name = "RepeatedSequenceTest", group = "Sequencer Dedicated Tests")
public class RepeatedSequenceTest extends SequencedOpMode {


    public RepeatedSequence sequence = new RepeatedSequence((a) -> {
    telemetry.addData("Time",System.currentTimeMillis());
    },5000,1000);

    @Override
    public void onStart() {
        sequencer.addSequence(sequence);
    }

    @Override
    public void onLoop() {

    }
}
