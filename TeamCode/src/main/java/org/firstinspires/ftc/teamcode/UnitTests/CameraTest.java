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
            CameraShell.TargetData tag38 = camera.getTagData(38);
            if (tag38 != null) {
                telemetry.addData("Tag 38 Distance (m)", tag38.distanceMeters);
                telemetry.addData("Tag 38 Angle (deg)", tag38.elevationAngleDegrees);
            } else {
                telemetry.addLine("Tag 38 not in view.");
            }

            // Check for Tag 24
            CameraShell.TargetData tag41 = camera.getTagData(41);
            if (tag41 != null) {
                telemetry.addData("Tag 41 Distance (m)", tag41.distanceMeters);
                telemetry.addData("Tag 41 Angle (deg)", tag41.elevationAngleDegrees);
            } else {
                telemetry.addLine("Tag 41 not in view.");
            }

            telemetry.update();
        }

        camera.stop();
    }
}
