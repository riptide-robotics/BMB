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

       addTest(() -> {
           move.loop(true,tarX = 1,tarY = 6,tarH = 2);
       });

       addTest(() -> {
            move.loop(true,tarX = 6,tarY = -2,tarH = 9);
       });

       addTest(() -> {
            move.loop(true,tarX = -4,tarY = 9,tarH = -1);
       });

       addCondition(() -> {
            if (testTime > 3000) return "FAIL out of time";

            if (move.getDistanceError() < 1 && move.getHeadingError() < 1) return "SUCCESS on position";

            return null;
        });
    }


}
