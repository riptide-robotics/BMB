package org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.DedicatedTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.TimedSequenceBase;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;

@TeleOp(name = "TimedSequenceBaseTest", group = "Sequencer Dedicated Tests")
public class TimedSequenceBaseTest extends SequencedOpMode {

    boolean check;

    public TimedSequenceBase base = new TimedSequenceBase((a) -> {
        check = true;
    },1000);

    @Override
    public void onStart() {
        sequencer.addSequence(base);
    }


    @Override
    public void onLoop() {
        telemetry.addData("TimesSequenceBase",check);
    }
}