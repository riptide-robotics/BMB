package org.firstinspires.ftc.teamcode;




import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;

@TeleOp(name = "Meet 0 FSM")
public class Meet0FSM extends LinearOpMode {

    boolean hasrun = false;
    boolean startPressedG2 = false;
    boolean backPressedG2 = false;
    Robot robot;

    public enum states{
        TELEOP,
        ENDGAME
    }

    public states currentState = states.TELEOP;
    @Override
    public void runOpMode() {

        robot = new Robot(hardwareMap);
        robot.getDrivetrain().resetImu();
        telemetry.addLine("Robot successfully initiated!");

        waitForStart();
        while(opModeIsActive()){
            FSM();
            telemetry.addData("Current state: ", currentState);
        }
    }

    public void FSM(){
        fieldcentricdrive();
        switch (currentState){
            case TELEOP:
                if (!hasrun){
                    // do setup stuff for this case
                    hasrun = true;
                }

                if (gamepad2.start && !startPressedG2){
                    currentState = states.ENDGAME;
                    startPressedG2 = true;
                    hasrun = false;
                }

                break;

            case ENDGAME:
                if (!hasrun){
                    // do setup stuff
                    hasrun = true;
                }

                if (gamepad2.back && !backPressedG2){
                    currentState = states.TELEOP;
                    backPressedG2 = true;
                    hasrun = false;
                }

                break;
        }
        if (!gamepad2.start){
            startPressedG2 = false;
        }
        if (!gamepad2.back){
            backPressedG2 = false;
        }
    }

    public void fieldcentricdrive(){
        double slowdown = gamepad1.right_trigger > 0 ? 0.25 : 1.0;
        double y = -gamepad1.right_stick_y * slowdown;
        double x = gamepad1.right_stick_x * 1.1 * slowdown;
        double rx = gamepad1.left_stick_x * slowdown;

        double heading = robot.getDrivetrain().getRobotHeading(AngleUnit.RADIANS);

        double rotX = x * Math.cos(-heading) - y * Math.sin(-heading);
        double rotY = x * Math.sin(-heading) + y * Math.cos(-heading);

        double denominator = Math.max(Math.abs(rotY) + Math.abs(rotX) + Math.abs(rx), 1.0);
        double frWheelPower = (rotY - rotX - rx) / denominator;
        double flWheelPower = (rotY + rotX + rx) / denominator;
        double brWheelPower = (rotY + rotX - rx) / denominator;
        double blWheelPower = (rotY - rotX + rx) / denominator;

        robot.getDrivetrain().setWheelPowers(flWheelPower, frWheelPower, brWheelPower, blWheelPower);

        if (gamepad1.y) {
            robot.getDrivetrain().resetImu();
        }
    }
}
