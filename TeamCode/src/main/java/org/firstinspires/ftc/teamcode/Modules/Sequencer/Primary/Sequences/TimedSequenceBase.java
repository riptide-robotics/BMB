package org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences;

import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.SequenceBase;


/**
 * This class is a basic sequence that waits for a segment of time, then executes. <br>
 * It then kills itself.
 * */
public class TimedSequenceBase extends SequenceBase {
    public Long destinationTimer = null;
    public long msDelay;

    public TimedSequenceBase(SequenceDataLambda runnable, long msDelay) {
        super(runnable);
        this.msDelay = msDelay;
    }

    @Override
    public boolean iterationLoop() {
        if (destinationTimer == null) destinationTimer = System.currentTimeMillis() + msDelay;
        return end = System.currentTimeMillis() > destinationTimer;
    }
}
