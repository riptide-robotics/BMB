package org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences;

import org.firstinspires.ftc.teamcode.Sequencer.Primary.SequenceBase;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Stream;

/**The OrderedSequence is a system that will attempt to run sequences in a specific order. <br>
 * If it is forbidden to execute by the next sequence's logic, it will stop executing for this cycle. <br>
 * It will pick up from the same spot next cycle, potentially doing nothing if it is forbidden again. <br>
 * If it reaches its end, it will not loop back. It must be reinstantiated or recreated with OrderedSeqConstructor.
 * */
public class OrderedSequence extends SequenceBase {
    LinkedList<SequenceBase> sequences = new LinkedList<>();
    Stream<SequenceBase> sequenceBaseStream = sequences.stream();
    Iterator<SequenceBase> iterate;

    SequenceBase currBase = null;
    public OrderedSequence() {
        super((a) -> {}); //has to be edited after constructed lol
        iterate = sequenceBaseStream.iterator();
        this.runnable = (a) -> {
            if (currBase == null) currBase = iterate.next();

//            while (true) {
//                if (!(currBase.canExecute = currBase.iterationLoop())) break;
//
//                currBase.run();
//
//                if (!currBase.end) break;
//                else currBase = iterate.next();
//            }
//            this.end = !iterate.hasNext();

            //Rewrote to run a single instance every time.
            if (!(currBase.canExecute = currBase.iterationLoop())) return;
            currBase.run();
            if (currBase.end) currBase = iterate.next();
        };
    }

    public OrderedSequence(SequenceBase... bases) {
        this();
        sequences.addAll(Arrays.asList(bases));
    }
    /**Chainable.*/
    public OrderedSequence add(int slot, SequenceBase base) {
        sequences.add(slot, base);
        return this;
    }
    /**Chainable.*/
    public OrderedSequence add(SequenceBase base) {
        base.values = this.values;
        sequences.add(base);
        return this;
    }
    /**Chainable.*/
    public OrderedSequence remove(int slot) {
        sequences.remove(slot);
        return this;
    }
    /**Chainable.*/
    public OrderedSequence addAll(SequenceBase... base) {
        sequences.addAll(Arrays.asList(base));
        return this;
    }


    public void resetPosition() {
        sequenceBaseStream = sequences.stream();
        iterate = sequenceBaseStream.iterator();
    }

    @Override
    public boolean iterationLoop() {
        return true;
    }

    public interface SeqLambda {
        OrderedSequence run();
    }


    /**
     * OrderedSeqConstructor stores a single OrderedSequence constructor within a lambda. <br>
     * It uses this to create a new instance as necessary, bypassing the usual limit of one cycle.
     **/
    public static class OrderedSeqConstructor {
        public SeqLambda seq;

        public OrderedSeqConstructor(SeqLambda seq) {
            this.seq = seq;
        }

        public OrderedSequence assign(SeqLambda s) {
            this.seq = s;
            return construct();
        }

        public OrderedSequence construct() {
            return seq.run();
        }
    }
}
