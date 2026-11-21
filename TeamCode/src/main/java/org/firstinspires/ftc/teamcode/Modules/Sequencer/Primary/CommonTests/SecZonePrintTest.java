package org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.DataWatcherSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.RepeatedSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Zone;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Zones;

import java.util.List;

@TeleOp(name = "SecZonePrintTest", group = "Sequencer Tests")
public class SecZonePrintTest extends SequencedOpMode {



    @Override
    public void onStart() {
        DataWatcherSequence<List<Zone>> dws = new DataWatcherSequence<>(
                () -> Zones.getRobotZones(robot),
                List.class
        );
        sequencer.addSequence(dws);

        sequencer.addSequence( new RepeatedSequence((accessor) -> {
            seqtele.addData("Zones: ", dws.get());
        },1000,1000));
    }

    @Override
    public void onLoop() {

    }
    @Override
    public boolean useDriverControls() {
        return true;
    }
}
