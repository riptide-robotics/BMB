package org.firstinspires.ftc.teamcode.UnitTests;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Modules.VisionPipelines.CameraShell;

@TeleOp(name = "Camera Tag Sensor Test")
public class CameraTest extends LinearOpMode {

    @Override
    public void runOpMode() {
        // Let's assume your camera is tilted 15 degrees upwards.
        // The hardware map name is "limelight"
        CameraShell camera = new CameraShell(hardwareMap, "limelight", "imu", 15.0);

        waitForStart();

        camera.start();

        while (opModeIsActive()) {
            // Check for Tag 20
            CameraShell.TargetData tag20 = camera.getTagData(38);
            if (tag20 != null) {
                telemetry.addData("Tag 20 Distance (m)", tag20.distanceMeters);
                telemetry.addData("Tag 20 Angle (deg)", tag20.elevationAngleDegrees);
            } else {
                telemetry.addLine("Tag 20 not in view.");
            }

            // Check for Tag 24
            CameraShell.TargetData tag24 = camera.getTagData(41);
            if (tag24 != null) {
                telemetry.addData("Tag 24 Distance (m)", tag24.distanceMeters);
                telemetry.addData("Tag 24 Angle (deg)", tag24.elevationAngleDegrees);
            } else {
                telemetry.addLine("Tag 24 not in view.");
            }

            telemetry.update();
        }

        camera.stop();
    }
}
