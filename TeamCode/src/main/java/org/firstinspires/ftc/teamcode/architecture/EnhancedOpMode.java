package org.firstinspires.ftc.teamcode.architecture;

import static org.firstinspires.ftc.teamcode.architecture.Context.tel;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.arcrobotics.ftclib.gamepad.KeyReader;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

public abstract class EnhancedOpMode extends LinearOpMode {
    @Override
    public void runOpMode() {

        initialize();

        tel = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

        waitForStart();

        linearOpMode();

        Context.keyReaders = Robot.keyReaders; // for some reason it didn't work any other way

        int counter = 0;
        int telemetryTimer = 500;
        ElapsedTime timer = new ElapsedTime();
        while (opModeIsActive()) {
            primaryLoop();
            for (KeyReader reader : Context.keyReaders)
            {
                reader.readValue();
            }
            if (counter % telemetryTimer == 0) { // ~50 ms update rate, can go down to ~5 ms without loop times falling off
                TelemetryReadout.addTelemetry(counter, timer);
            }
            counter++;

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
