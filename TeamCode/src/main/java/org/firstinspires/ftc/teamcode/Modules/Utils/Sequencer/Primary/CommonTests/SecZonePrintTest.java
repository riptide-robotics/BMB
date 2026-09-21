package org.firstinspires.ftc.teamcode.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.DataWatcherSequence;
import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.RepeatedSequence;
import org.firstinspires.ftc.teamcode.Sequencer.SequencedOpMode;

import java.util.List;

@TeleOp(name = "SecZonePrintTest", group = "Sequencer Tests")
public class SecZonePrintTest extends SequencedOpMode {



    @Override
    public void onStart() {
        DataWatcherSequence<Pose2D> dws = new DataWatcherSequence<>(
                () -> robot.getDrivetrain().getCurrPos(),
                Pose2D.class
        );
        sequencer.addSequence(dws);

        sequencer.addSequence( new RepeatedSequence((accessor) -> {
            telemetry.addData("Zones: ", dws.get());
        },1000,1000));
    }

    @Override
    public void onLoop() {

    }
}
