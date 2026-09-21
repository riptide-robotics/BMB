package org.firstinspires.ftc.teamcode.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.DataWatcherSequence;
import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.FullLoopSequence;
import org.firstinspires.ftc.teamcode.Sequencer.SequencedOpMode;

@TeleOp(name = "LocPrintTest", group = "Sequencer Tests")
public class LocPrintTest extends SequencedOpMode {

    DataWatcherSequence dws = new DataWatcherSequence<>(
            () -> robot.getDrivetrain().getCurrPos(),
            Pose2D.class
    );

    @Override
    public void onStart() {
        sequencer.addSequence(dws);

        sequencer.addSequence(new FullLoopSequence((access) ->
                telemetry.addData("Pose2D", dws.get().toString())));
    }

    @Override
    public void onLoop() {

    }
}
