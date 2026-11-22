package org.firstinspires.ftc.teamcode.UnitTests;

import androidx.annotation.Nullable;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts.Meet1Auto.PinpointMove;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.SequenceBase;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.DataWatcherSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.FullLoopSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.LinkedSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.ObstructiveSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.RepeatedSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.TimedSequenceBase;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedTelemetry;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class UnitTest extends SequencedOpMode {

    int testID = -1;
    public Map<Integer, FullLoopSequence> tests = new HashMap<>();
    public Map<Integer, DataWatcherSequence<Boolean>> conditions = new HashMap<>();
    public Map<Integer, DataWatcherSequence<String>> printers = new HashMap<>();

    FullLoopSequence activeTest;
    DataWatcherSequence<Boolean> activeCondition;
    DataWatcherSequence<String> activePrinter;

    private long testTime = 0;

    private Boolean testResult = null;

    public static int setActiveTest = 0; //not standard naming conventions, but from mtele it will not look like a var

    @Override
    public void onLoop() {
        if (testID < 0) {
            testID = 0;
            testTime = System.nanoTime();
            setActiveTest = testID;

            activeTest = tests.get(0);
            activeCondition = conditions.get(0);
            activePrinter = printers.get(0);

            sequencer.addSequences(activePrinter, activeTest, activeCondition);
        }

        if (setActiveTest != testID) {
            seqtele.addData("Test Result " + testID, "Skipped!");
            testID = setActiveTest;
            testResult = null;

            if (seqtele.updates.containsKey("Test Result " + testID)) {

                int i = 0;
                while (seqtele.updates.containsKey( "Test Result " + testID + ":" + i))
                    i++;

                seqtele.addData("Test Result " + testID + ":" + i, seqtele.updates.get("Test Result " + testID));
                seqtele.addData("Test Result " + testID, "Overwriting...");
            }

            sequencer.sequence.remove(activeTest);
            sequencer.sequence.remove(activeCondition);
            sequencer.sequence.remove(activePrinter);

            int stepC = testID;
            while (conditions.get(testID) == null) stepC--;

            int stepP = testID;
            while (printers.get(testID) == null) stepP--;

            activeTest = tests.get(testID);
            activeCondition = conditions.get(stepC);
            activePrinter = printers.get(stepP);

            sequencer.addSequences(activeTest, activeCondition, activePrinter);
        }

        if (activeCondition.get() != null) {
            testTime = System.nanoTime();
            setActiveTest = testID;
            testResult = activeCondition.get();

            sequencer.sequence.remove(activeTest);
            sequencer.sequence.remove(activeCondition);

            activePrinter.run();
            seqtele.addData("Test Result " + testID, activePrinter.get());

            testResult = null;

            if (testID > tests.size()) {//stops right here
                seqtele.addData("", "HALTED");
                return;
            }
            testID++;

            activeTest = tests.get(testID);
            activeCondition = conditions.get(testID) != null ? conditions.get(testID) : activeCondition;
            activePrinter = printers.get(testID) != null ? printers.get(testID) : activePrinter;

            sequencer.addSequences(activeTest, activeCondition, activePrinter);
        }
    }

    int testAdded = 0;
    private void addTest(Runnable run) {
        tests.put(testAdded, new FullLoopSequence((a) -> {
            run.run();
        }));
    }
    private void addCondition(Supplier<Boolean> run) {
        conditions.put(testAdded, new DataWatcherSequence<>(run::get, Boolean.class));
    }

    private void addPrinter(Function<Boolean, String> run) {
        printers.put(testAdded, new DataWatcherSequence<>(() -> run.apply(testResult), String.class));
    }

    public void add(Runnable test, @Nullable Supplier<Boolean> condition, @Nullable Function<Boolean, String> printer) {
        addTest(test);
        if (condition != null) addCondition(condition); else conditions.put(testAdded, null);
        if (printer != null) addPrinter(printer); else printers.put(testAdded, null);

        testAdded++;
    }
    public void add(Runnable test, @Nullable Supplier<Boolean> condition) {
        add(test, condition, null);
    }
    public void add(Runnable test) {
        add(test, null, null);
    }

    public long getTestTimeMS() {
        return System.nanoTime() - testTime;
    }
}
