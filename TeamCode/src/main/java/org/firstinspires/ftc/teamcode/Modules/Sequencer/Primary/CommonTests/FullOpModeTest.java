package org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.DataWatcherSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.OrderedSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.RepeatedSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.TimedSequenceBase;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;

@TeleOp(name = "FullOpModeTest", group = "Sequencer Tests")
public class FullOpModeTest extends SequencedOpMode {

    int i = 0;
    public RepeatedSequence seq1 = new RepeatedSequence((accessor) -> {
        i++;
        seqtele.addData("Seq 1: Incrementation Test", i);
    },1000,1000);

    DataWatcherSequence seq2 = new DataWatcherSequence<>(
            () -> robot.getDrivetrain().getCurrPos(),
            Pose2D.class
    );

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
        sequencer.addSequences(seq1, seq2, tested);
    }

    @Override
    public void onLoop() {
        seqtele.addData("Seq 2: DW Position Test", seq2.get());
        seqtele.addData("Seq 4: Ordered Sequence Test", progress);

        if (gamepad1.aWasPressed()) {
            sequencer.addSequence(new TimedSequenceBase((a) -> {
                seqtele.addData("Seq 3: Button Test", true);
            }, 1000));
        }
    }
}
