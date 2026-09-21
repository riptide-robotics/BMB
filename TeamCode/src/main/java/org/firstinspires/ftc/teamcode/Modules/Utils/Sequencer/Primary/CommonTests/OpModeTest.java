package org.firstinspires.ftc.teamcode.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.FullLoopSequence;
import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.RepeatedSequence;
import org.firstinspires.ftc.teamcode.Sequencer.SequencedOpMode;
import org.firstinspires.ftc.teamcode.Sequencer.Zones;

@TeleOp(name = "OpModeTest", group = "Sequencer Tests")
public class OpModeTest extends SequencedOpMode {


    //TODO dunno what they are named - fix this when i get them
    DcMotor drive1 = hardwareMap.dcMotor.get("Drive1");
    DcMotor drive2 = hardwareMap.dcMotor.get("Drive1");
    DcMotor drive3 = hardwareMap.dcMotor.get("Drive1");
    DcMotor drive4 = hardwareMap.dcMotor.get("Drive1");

    @Override
    public void onStart() {
        sequencer.addSequence(new FullLoopSequence(a -> {
            if (gamepad1.a) gamepad1.rumble(1000);
            if (gamepad1.b) gamepad1.rumble(5000);

            telemetry.addData("Zones", Zones.getRobotZones(robot));
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
