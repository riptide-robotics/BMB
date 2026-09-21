package org.firstinspires.ftc.teamcode.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.FullLoopSequence;
import org.firstinspires.ftc.teamcode.Sequencer.Primary.Sequences.RepeatedSequence;
import org.firstinspires.ftc.teamcode.Sequencer.SequencedOpMode;
import org.firstinspires.ftc.teamcode.Sequencer.Zones;

@TeleOp(name = "BatteryVoltageOpModeTest", group = "Sequencer Tests")
public class BatteryVoltageOpModeTest extends SequencedOpMode {


    //TODO dunno what they are named - fix this when i get them
    DcMotor drive1 = hardwareMap.dcMotor.get("Drive1");
    DcMotor drive2 = hardwareMap.dcMotor.get("Drive1");
    DcMotor drive3 = hardwareMap.dcMotor.get("Drive1");
    DcMotor drive4 = hardwareMap.dcMotor.get("Drive1");

    VoltageSensor sensor = hardwareMap.get(VoltageSensor.class, "Control Hub");

    @Override
    public void onStart() {
        sequencer.addSequence(new FullLoopSequence(a -> {
            double voltage = sensor.getVoltage();
            telemetry.addData("Voltage", voltage);
            if (voltage < 11) telemetry.addData("WARNING","LOW VOLTAGE");
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
