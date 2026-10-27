package org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.TimedSequenceBase;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.FullLoopSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.ObstructiveSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.OrderedSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Zones;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;

@TeleOp(name = "ZoneBehaviorTest", group = "Sequencer Tests")
public class ZoneBehaviorTest extends SequencedOpMode {




    OrderedSequence seq;

    @Override
    public void onStart() {
        sequencer.addSequence(new FullLoopSequence(a -> {
            if (gamepad1.a) gamepad1.rumble(1000);
            if (gamepad1.b) gamepad1.rumble(5000);

            telemetry.addData("Zones", Zones.getRobotZones(robot));
        }));

        sequencer.addSequence(seq = construct());

    }

    @Override
    public void onLoop() {


        if (seq.end) {
            sequencer.addSequence(seq = construct());
        }
    }


    public OrderedSequence construct() {
        return new OrderedSequence.OrderedSeqConstructor(() -> new OrderedSequence()

                .add(new ObstructiveSequence(() -> !Zones.getRobotZones(robot).isEmpty()))

                .add(new TimedSequenceBase((a) -> {
                    setDestination(new EditablePose2D(10, 10, 0, DistanceUnit.INCH));
                },1000))

                .add(new TimedSequenceBase((a) -> {
                    setDestination(new EditablePose2D(-10, -10, 0, DistanceUnit.INCH));
                },5000))


        ).construct();
    }
    @Override
    public boolean useDriverControls() {
        return true;
    }
}
