package org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.DataWatcherSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.FullLoopSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.RepeatedSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;

import java.util.List;

@TeleOp(name = "ZoneOpModeTest", group = "Sequencer Tests")
public class ZoneOpModeTest extends SequencedOpMode {



    DataWatcherSequence dws = new DataWatcherSequence<>(
            () -> robot.getDrivetrain().getCurrPos(),
            List.class
    );

    @Override
    public void onStart() {
        sequencer.addSequence(dws);

        sequencer.addSequence( new RepeatedSequence((accessor) -> {
            seqtele.addData("Zones", dws.get());
        },1000,1000));

        sequencer.addSequence(new FullLoopSequence((accessor) -> {
            seqtele.addData("Second-period Zones", dws.get());
        }));

    }

    @Override
    public void onLoop() {

    }
    @Override
    public boolean useDriverControls() {
        return true;
    }
}
