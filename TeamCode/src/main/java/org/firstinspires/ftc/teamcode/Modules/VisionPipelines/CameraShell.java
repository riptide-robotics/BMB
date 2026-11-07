package org.firstinspires.ftc.teamcode.Modules.VisionPipelines;

import com.qualcomm.hardware.limelightvision.LLResultTypes;
import com.qualcomm.hardware.limelightvision.Limelight3A;
import com.qualcomm.hardware.rev.RevHubOrientationOnRobot;
import com.qualcomm.robotcore.hardware.HardwareMap;
import org.firstinspires.ftc.robotcore.external.navigation.Pose3D;
import org.firstinspires.ftc.robotcore.external.navigation.YawPitchRollAngles;

import com.qualcomm.hardware.limelightvision.LLResult;
import com.qualcomm.robotcore.hardware.IMU;

import java.util.List;

public class CameraShell {
    private Limelight3A limelight;
    private IMU imu;

    // Physical camera mount configuration
    private double cameraPitchDegrees;

    /**
     * Initializes the Limelight Camera Shell.
     *
     * @param hwMap              The FTC hardware map
     * @param LLdeviceName         The name of the Limelight in the robot controller config
     * @param imuName             The name of the IMU in the robot config
     * @param cameraPitchDegrees The angle the camera is tilted up from the ground (0 = perfectly level)
     */
    public CameraShell(HardwareMap hwMap, String LLdeviceName, String imuName, double cameraPitchDegrees) {
        this.limelight = hwMap.get(Limelight3A.class, LLdeviceName);
        this.imu = hwMap.get(IMU.class, imuName);
        this.cameraPitchDegrees = cameraPitchDegrees;

        // Ensure the Limelight is polling at the default rate
        limelight.pipelineSwitch(0);
        limelight.setPollRateHz(100);

        // make sure IMU is properly initialized
        RevHubOrientationOnRobot orientation = new RevHubOrientationOnRobot(
                RevHubOrientationOnRobot.LogoFacingDirection.UP,
                RevHubOrientationOnRobot.UsbFacingDirection.BACKWARD
        );
        imu.initialize(new IMU.Parameters(orientation));
    }

    public void start() {
        // Starts processing data. Make sure your Limelight is set to an AprilTag pipeline (e.g., pipeline 0)
        limelight.pipelineSwitch(0);
        limelight.start();
    }

    public void stop() {
        limelight.stop();
    }

    /**
     * Looks for a specific AprilTag ID and calculates its distance and angle.
     *
     * @param targetId The AprilTag ID to look for (e.g., 20 or 24)
     * @return A TargetData object containing distance and angle, or null if the tag is not currently in view.
     */
    public TargetData getTagData(int targetId) {
        YawPitchRollAngles orientation = imu.getRobotYawPitchRollAngles();
        limelight.updateRobotOrientation(orientation.getYaw()+cameraPitchDegrees);
        LLResult result = limelight.getLatestResult();

        if (result != null && result.isValid()) {
            List<LLResultTypes.FiducialResult> tags = result.getFiducialResults();

            for (LLResultTypes.FiducialResult tag : tags) {
                if (tag.getFiducialId() == targetId) {
                    Pose3D botPose = tag.getRobotPoseTargetSpace();

                    // 1. Calculate Distance
                    // The targetPoseCameraSpace gives the X, Y, Z coordinates of the tag relative to the camera lens in meters.
                    Pose3D pose = tag.getTargetPoseCameraSpace();
                    double x = botPose.getPosition().x;
                    double y = botPose.getPosition().y;
                    double z = botPose.getPosition().z;
//                    x = tag.getTargetXDegrees();
//                    y = tag.getTargetYDegrees();
//                    x = tag.getX

                    // 3D Distance formula: sqrt(x^2 + y^2 + z^2)
                    double distanceMeters = Math.sqrt((x * x) + (y * y) + (z * z));

                    // 2. Calculate Angle Above Bot
                    // ty is the crosshair's vertical offset from the center of the camera lens to the target in degrees.
                    // By adding the physical tilt of the camera, we get the true angle relative to the floor.
                    double ty = tag.getTargetYDegrees();
                    double trueElevationAngle = cameraPitchDegrees + ty;

                    return new TargetData(distanceMeters, tag.getTargetXDegrees());
                }
            }
        }
        return null; // Tag not found
    }

    /**
     * Simple data class to hold the returned distance and angle.
     */
    public static class TargetData {
        public double distanceMeters;
        public double elevationAngleDegrees;

        public TargetData(double distanceMeters, double elevationAngleDegrees) {
            this.distanceMeters = distanceMeters;
            this.elevationAngleDegrees = elevationAngleDegrees;
        }
    }
}
