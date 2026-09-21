package org.firstinspires.ftc.teamcode.Sequencer.Primary.DedicatedTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.TimedSequenceBase;
import org.firstinspires.ftc.teamcode.Sequencer.SequencedOpMode;

@TeleOp(name = "TimedSequenceBaseTest", group = "Sequencer Dedicated Tests")
public class TimedSequenceBaseTest extends SequencedOpMode {

    public TimedSequenceBase base = new TimedSequenceBase((a) -> {
        telemetry.addData("TimesSequenceBase",true);
    },1000);

    @Override
    public void onStart() {
        sequencer.addSequence(base);
    }


    @Override
    public void onLoop() {

    }
}