package org.firstinspires.ftc.teamcode.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.navigation.Pose2D;
import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.DataWatcherSequence;
import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.FullLoopSequence;
import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.RepeatedSequence;
import org.firstinspires.ftc.teamcode.Sequencer.SequencedOpMode;

import java.util.List;

@TeleOp(name = "ZoneOpModeTest", group = "Sequencer Tests")
public class ZoneOpModeTest extends SequencedOpMode {

    DcMotor drive1 = hardwareMap.dcMotor.get("Drive1");
    DcMotor drive2 = hardwareMap.dcMotor.get("Drive1");
    DcMotor drive3 = hardwareMap.dcMotor.get("Drive1");
    DcMotor drive4 = hardwareMap.dcMotor.get("Drive1");


    DataWatcherSequence dws = new DataWatcherSequence<>(
            () -> robot.getDrivetrain().getCurrPos(),
            List.class
    );

    @Override
    public void onStart() {
        sequencer.addSequence(dws);

        sequencer.addSequence( new RepeatedSequence((accessor) -> {
            telemetry.addData("Zones", dws.get());
        },1000,1000));

        sequencer.addSequence(new FullLoopSequence((accessor) -> {
            telemetry.addData("Second-period Zones", dws.get());
        }));

    }

    @Override
    public void onLoop() {
        drive1.setPower(gamepad1.left_stick_y);
        drive2.setPower(gamepad1.left_stick_y);
        drive3.setPower(gamepad1.left_stick_x);
        drive4.setPower(gamepad1.left_stick_x);
    }
}
