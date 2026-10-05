package org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences;


/**
 * This sequence starts off just like the TimedSequenceBase it extends. <br>
 * However, after execution, it waits by a new delay.
 * */
public class RepeatedSequence extends TimedSequenceBase {
    long msRepeatDelay;

    public int iterations = 0;
    /**
     * @param msDelay the starting delay.
     * @param msRepeatDelay the delay between one execution from the next.
     * */
    public RepeatedSequence(SequenceDataLambda runnable, long msDelay, long msRepeatDelay) {
        super(runnable, msDelay);
        this.msRepeatDelay = msRepeatDelay;
    }

    @Override
    public boolean iterationLoop() {
        boolean value =  super.iterationLoop();
        end = false;
        if (value) {
            destinationTimer += msRepeatDelay;
            iterations++;
        };
        return value;
    }
}
