//package org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts.Meet1Auto;
//
//import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
//import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
//
//import org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts.Meet1Auto.PositionHold;
//
//@TeleOp(name = "Position Hold Test")
//public class PositionHoldOpMode extends LinearOpMode {
//
//    @Override
//    public void runOpMode() {
//        PositionHold positionHold = new PositionHold(hardwareMap);
//
//        waitForStart();
//        positionHold.lockCurrentPosition();
//
//        while (opModeIsActive()) {
//            if (gamepad1.a) {
//                positionHold.lockCurrentPosition();
//            }
//            positionHold.update();
//        }
//    }
//}
