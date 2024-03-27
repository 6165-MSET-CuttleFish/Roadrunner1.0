package org.firstinspires.ftc.teamcode.architecture;

import com.arcrobotics.ftclib.gamepad.KeyReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class EnhancedOpMode extends LinearOpMode {
    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        linearOpMode();

        Context.keyReaders = Robot.keyReaders;
        while (opModeIsActive()) {
            primaryLoop();
            for (KeyReader reader : Context.keyReaders)
            {
                reader.readValue();
            }

        }
    }
    public abstract void primaryLoop();

    public abstract void initialize();

    public abstract void linearOpMode();

    public void die()
    {
        while (!isStopRequested() && opModeIsActive()) {}
    }
}
