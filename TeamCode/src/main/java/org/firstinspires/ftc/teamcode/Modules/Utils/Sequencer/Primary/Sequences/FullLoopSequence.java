package org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences;

/**
 * A variant of TimedSequeneBase that runs every cycle. <br>
 * Use for clarity or an easier time creating new sequence types.
 * */
public class FullLoopSequence extends RepeatedSequence {
    public FullLoopSequence(SequenceDataLambda runnable) {
        super(runnable, 0, 0); //can only run once per iter anyway
    }
}
