package org.firstinspires.ftc.teamcode.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.DataWatcherSequence;
import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.FullLoopSequence;
import org.firstinspires.ftc.teamcode.Sequencer.SequencedOpMode;
import org.firstinspires.ftc.teamcode.Sequencer.Zones;

import java.util.List;

@TeleOp(name = "ZonePrintTest", group = "Sequencer Tests")
public class ZonePrintTest extends SequencedOpMode {

    DataWatcherSequence<List<Zones>> seq = new DataWatcherSequence<>(() -> Zones.getRobotZones(robot), List.class);

    @Override
    public void onStart() {
        sequencer.addSequence(seq);

        sequencer.addSequence(new FullLoopSequence((accessor) -> {
            telemetry.addData("Zones", seq.get());
        }));
    }

    @Override
    public void onLoop() {

    }
}
