package org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.DataWatcherSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.FullLoopSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Zones;

import java.util.List;

@TeleOp(name = "DependentZonePrintTest", group = "Sequencer Tests")
public class DependentZonePrintTest extends SequencedOpMode {

    DataWatcherSequence<List<Zones>> seq = new DataWatcherSequence<>(() -> Zones.getRobotZones(robot), List.class);

    @Override
    public void onStart() {
        sequencer.addSequence(seq);

        sequencer.addSequence(new FullLoopSequence((accessor) -> {
            if (gamepad1.b)
                telemetry.addData("Zones", seq.get());
        }));
    }

    @Override
    public void onLoop() {

    }
}
