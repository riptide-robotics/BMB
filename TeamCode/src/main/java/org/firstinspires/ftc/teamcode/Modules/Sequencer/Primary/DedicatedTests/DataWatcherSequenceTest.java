package org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.DedicatedTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Modules.Sequencer.CommonSequences;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.DataWatcherSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;

@TeleOp(name = "DataWatcherSequenceTest", group = "Sequencer Dedicated Tests")
public class DataWatcherSequenceTest extends SequencedOpMode {

    DataWatcherSequence<Integer> dws;
    private DcMotor odo;
    @Override
    public void onStart() {

        odo = hardwareMap.dcMotor.get("pinpoint");

        dws = new DataWatcherSequence<>(
                () -> odo.getCurrentPosition(),
                Integer.class
        );

        this.sequencer.addSequence(CommonSequences.ODOTEST.getSequence(hardwareMap));


    }
    @Override
    public void onLoop() {
        telemetry.addData("Odo Pod Position", dws.get());
    }
}
