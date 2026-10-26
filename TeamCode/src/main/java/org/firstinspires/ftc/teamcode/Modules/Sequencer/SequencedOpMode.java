package org.firstinspires.ftc.teamcode.Modules.Sequencer;

//import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.Modules.Utils.EditablePose2D;
import org.firstinspires.ftc.teamcode.Robot;


/**
 * A variant of OpMode that has the sequencer system incorporated within. <br>
 *  Note that usage is not identical, relying on #onStart(), #onLoop(), and #onStop().
 *  */
public abstract class SequencedOpMode extends LinearOpMode {

    public Sequencer sequencer;
    public MultipleTelemetry mtele;
    public Robot robot;

    public SequencedTelemetry seqtele;

    public DriverControls driverControls;

    public EditablePose2D dest = null;

    EditablePose2D pose = null;

    public DistanceUnit distunit = DistanceUnit.INCH;

    @Override
    public void runOpMode() throws InterruptedException {
        robot = new Robot(hardwareMap);

        sequencer = new Sequencer();
        sequencer.tele = seqtele = new SequencedTelemetry(this);
        mtele = new MultipleTelemetry(telemetry);
        driverControls = new DriverControls(robot, gamepad1);

        onStart();

        if (isStopRequested()) return;
        waitForStart();

        while (opModeIsActive()) {

            if (useDriverControls() && pose == null) { try {
                pose = robot.getDrivetrain().getCurrPos();
            } catch (Exception e) {
                seqtele.addData("Pose could not be initialized", e.getMessage());
            }}

            else if (!useDriverControls() && pose != null) pose = null;


            if (useDriverControls()) {
                if (dest == null) driverControls.fieldCentricDrive();
                else driverControls.driveToPos(dest);
                if (pose != null)
                    seqtele.addData("Pose", "X:" + pose.getX(distunit) + ", Y:" + pose.getY(distunit) +  ", Z:" + pose.getH());
                else seqtele.addData("Pose", "null");
            }
            seqtele.addData("drivercontrols", driverControls != null);

            sequencer.iterate();
            onLoop();
            telemetry.update();
            mtele.update();
            seqtele.update();
        }
        onStop();
    }

    public abstract void onStart();
    public abstract void onLoop();
    public void onStop() {};

    public boolean useDriverControls() {
        return false;
    }

    public void setDestination(EditablePose2D pose2D) {
        dest = pose2D;
    }

    public EditablePose2D getDestination() {
        return dest;
    }
    public void removeDestination() {
        dest = null;
    }
}
