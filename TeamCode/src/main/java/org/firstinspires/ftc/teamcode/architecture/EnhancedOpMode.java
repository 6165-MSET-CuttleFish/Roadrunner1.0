package org.firstinspires.ftc.teamcode.architecture;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

public abstract class EnhancedOpMode extends LinearOpMode {
    @Override
    public void runOpMode() {
        initialize();

        waitForStart();

        linearOpMode();
        while (opModeIsActive()) {
            primaryLoop();
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
