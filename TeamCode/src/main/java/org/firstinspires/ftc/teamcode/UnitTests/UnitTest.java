package org.firstinspires.ftc.teamcode.UnitTests;

public class UnitTest {
    // https://docs.google.com/presentation/d/15hiayy1XQD8Y_2gFYvPoHPRe8F1i1ZBmPSsw_-aaEP4/edit?slide=id.h2bb3d5c7be0bd53a_3_29#slide=id.h2bb3d5c7be0bd53a_3_29
    /*
        1. Encoder reads a tick from the motor
        2. sends tick and timestamp to converter

        3. converter sends current RPM to Velocity PID
        4. OpMode sends target RPM to Velocity PID
        5. Velocity PID calculates and sends the change in motor power back to OpMode
            a. error = diff between current pos & target pos
            1. P - Proportional
                a = Kp * error
            2. D - Derivative
                b = Kd * error rate
            3. I - Integral
                c = Ki * sum of all errors

            4. power = a + b + c

            5. returns power to OpMode

        6. OpMode sends flywheel.setPower(power) to flywheel

     */

}
