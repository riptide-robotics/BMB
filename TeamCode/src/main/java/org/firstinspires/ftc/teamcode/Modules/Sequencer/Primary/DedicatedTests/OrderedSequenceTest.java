package org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.DedicatedTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.OrderedSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.RepeatedSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.TimedSequenceBase;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;

@TeleOp(name = "OrderedSequenceTest", group = "Sequencer Dedicated Tests")
public class OrderedSequenceTest extends SequencedOpMode {


    double progress = 0;

    public TimedSequenceBase base1 = new TimedSequenceBase((a) -> {
        progress = 1;
    },1000);

    public TimedSequenceBase base2 = new TimedSequenceBase((a) -> {
        progress = 2;
    },1000);

    public TimedSequenceBase base3 = new TimedSequenceBase((a) -> {
        progress = 3;
    },1000);

    public RepeatedSequence repeatseq = new RepeatedSequence((a) -> {
        progress--;
    },1000,500);

    public TimedSequenceBase unexecutable = new TimedSequenceBase((a) -> {
        progress = Double.POSITIVE_INFINITY;
    },1000);


    public OrderedSequence tested = new OrderedSequence(base1,base2,base3,repeatseq,unexecutable);
    @Override
    public void onStart() {
        sequencer.addSequence(tested);
    }

    @Override
    public void onLoop() {
        telemetry.addData("progress", progress);
    }
}
