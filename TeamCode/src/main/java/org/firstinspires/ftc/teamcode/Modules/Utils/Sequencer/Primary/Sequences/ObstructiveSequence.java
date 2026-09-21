package org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences;

import org.firstinspires.ftc.teamcode.Sequencer.Primary.SequenceBase;


/**Blocks OrderedSequence from running until its condition is fulfilled.*/
public class ObstructiveSequence extends SequenceBase {

    public FunctionalInterface endBlock;

    public ObstructiveSequence(FunctionalInterface endBlock) {
        super((a) -> {});
        this.endBlock = endBlock;
    }

    @Override
    public boolean iterationLoop() {
        return end = endBlock.run();
    }

    public interface FunctionalInterface {
        boolean run();
    }
}
