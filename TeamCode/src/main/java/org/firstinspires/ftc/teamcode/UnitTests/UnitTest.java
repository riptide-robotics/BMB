package org.firstinspires.ftc.teamcode.UnitTests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts.Meet1Auto.PinpointMove;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.SequenceBase;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.DataWatcherSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.FullLoopSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.ObstructiveSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.RepeatedSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedTelemetry;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

public abstract class UnitTest extends SequencedOpMode {

    int testID = -1;
    public Map<Integer, FullLoopSequence> tests;
    public Map<Integer, DataWatcherSequence<String>> conditions;
    FullLoopSequence activeTest;
    DataWatcherSequence<String> activeCondition = null;
    public Map<Integer, String> results;

    long testTime = 0;

    boolean finished = false;

    @Override
    public void onLoop() {
        seqtele.addData("Results:", results.toString());

        if (activeCondition != null && activeCondition.get() != null) {
            results.put(testID, activeCondition.get());
            sequencer.sequence.clear();
        }

        if (sequencer.sequence.isEmpty()) {
            testID++;
            testTime = System.nanoTime();

            finished = true;
            if (tests.size() > testID) { finished = false; activeTest = tests.get(testID); }
            if (conditions.size() > testID) { finished = false; activeCondition = conditions.get(testID); }

            if (!finished)
                sequencer.addSequences(activeTest, activeCondition);
        }
    }

    int testAdded = 0;
    public void addTest(Runnable run) {
        tests.put(testAdded++, new FullLoopSequence((a) -> {
            run.run();
        }));
    }
    int conditionsAdded = 0;
    public void addCondition(Supplier<String> run) {
        conditions.put(conditionsAdded++, new DataWatcherSequence<>(run::get, String.class));
    }
    public long getTestTimeMS() {
        return System.nanoTime() - testTime;
    }

}
