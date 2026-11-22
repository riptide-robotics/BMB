package org.firstinspires.ftc.teamcode.UnitTests;

import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.Autonomous.AutonomousScripts.Meet1Auto.PinpointMove;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.DataWatcherSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.FullLoopSequence;
import org.firstinspires.ftc.teamcode.Modules.Sequencer.Primary.Sequences.RepeatedSequence;

@TeleOp(name = "UnitTestExample")
public class UnitTestExample extends UnitTest {

    PinpointMove move;
    double tarX, tarY, tarH;

    @Override
    public void onStart() {
        move = new PinpointMove(hardwareMap, seqtele);

        add(() -> {
            move.loop(true,tarX = 1,tarY = 6,tarH = 2);
        }, () -> {
            if (move.getDistanceError() < 1 && move.getHeadingError() < 1) return true;
            if (getTestTimeMS() > 3000) return false;
            return null;
        }, (value) -> {
            if (value) return "SUCCESS - position within acceptable error";
            else return "FAILURE - out of time";
        });

       add(() -> {
           move.loop(true,tarX = 1,tarY = 6,tarH = 2);
       });

       add(() -> {
            move.loop(true,tarX = 6,tarY = -2,tarH = 9);
       });

       add(() -> {
            move.loop(true,tarX = -4,tarY = 9,tarH = -1);
       });
    }
}
