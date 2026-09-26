package org.firstinspires.ftc.teamcode.UnitTests;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@Config
@TeleOp (name="intake")
public class TestDrive extends LinearOpMode {
    public static double power = 0;
    DcMotor br;
    DcMotor bl;
    DcMotor fr;
    DcMotor fl;

    @Override
    public void runOpMode() {

        fl = hardwareMap.dcMotor.get("flWheel");
        fr = hardwareMap.dcMotor.get("frWheel");
        bl = hardwareMap.dcMotor.get("blWheel");
        br = hardwareMap.dcMotor.get("brWheel");

        waitForStart();

        while(opModeIsActive()) {

            // basic driving
            fl.setPower(gamepad1.left_stick_y);
            fr.setPower(gamepad1.left_stick_y);
            bl.setPower(gamepad1.left_stick_y);
            br.setPower(gamepad1.left_stick_y);

            // normal turn with the right stick
            fl.setPower(gamepad1.left_stick_x);
            bl.setPower(gamepad1.left_stick_x);
            fr.setPower(gamepad1.left_stick_x * -1);
            br.setPower(gamepad1.left_stick_x * -1);

            // use right stick to move directly left and right
            fl.setPower(gamepad1.right_stick_x * -1); // might be wrong, to fix, just flip the -1 to the back
            fr.setPower(gamepad1.right_stick_x * -1);
            bl.setPower(gamepad1.right_stick_x);
            br.setPower(gamepad1.right_stick_x);
        }


    }

}
