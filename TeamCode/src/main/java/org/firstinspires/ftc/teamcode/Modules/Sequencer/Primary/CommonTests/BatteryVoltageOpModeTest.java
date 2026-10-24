package org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.CommonTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.VoltageSensor;

import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.FullLoopSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.SequencedOpMode;

@TeleOp(name = "BatteryVoltageOpModeTest", group = "Sequencer Tests")
public class BatteryVoltageOpModeTest extends SequencedOpMode {




    VoltageSensor sensor;

    @Override
    public void onStart() {;

        sensor = hardwareMap.get(VoltageSensor.class, "Control Hub");

        sequencer.addSequence(new FullLoopSequence(a -> {
            double voltage = sensor.getVoltage();
            telemetry.addData("Voltage", voltage);
            if (voltage < 11) telemetry.addData("WARNING","LOW VOLTAGE");
        }));


    }

    @Override
    public void onLoop() {
    }
}
