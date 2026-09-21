package org.firstinspires.ftc.teamcode.Sequencer.Primary.DedicatedTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Modules.Utils.GoBildaPinpointDriver;
import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.DataWatcherSequence;
import org.firstinspires.ftc.teamcode.Sequencer.SequencedOpMode;

@TeleOp(name = "DataWatcherSequenceTest2", group = "Sequencer Dedicated Tests")
public class DataWatcherSequenceTest2 extends SequencedOpMode {
    GoBildaPinpointDriver odoComputer;
    public DataWatcherSequence<Double> dws;


    @Override
    public void onStart() {
        odoComputer = hardwareMap.get(GoBildaPinpointDriver.class, "odo");
        odoComputer.setOffsets(127, 299.72, DistanceUnit.MM);
        odoComputer.setEncoderResolution(GoBildaPinpointDriver.GoBildaOdometryPods.goBILDA_4_BAR_POD);
        odoComputer.setEncoderDirections(GoBildaPinpointDriver.EncoderDirection.FORWARD, GoBildaPinpointDriver.EncoderDirection.FORWARD);
        odoComputer.resetPosAndIMU();

         dws = new DataWatcherSequence<>(
                () -> odoComputer.getHeading(AngleUnit.RADIANS),
                Double.class
        );
        sequencer.addSequence(dws);
    }

    @Override
    public void onLoop() {
        telemetry.addData("Odometry Pod", dws.get());
    }
}
