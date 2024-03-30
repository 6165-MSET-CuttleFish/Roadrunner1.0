package org.firstinspires.ftc.teamcode.opmodes.tuning;

import static org.firstinspires.ftc.teamcode.architecture.Context.A1;
import static org.firstinspires.ftc.teamcode.architecture.Context.claw;

import com.acmerobotics.dashboard.config.Config;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.architecture.EnhancedOpMode;
import org.firstinspires.ftc.teamcode.modules.used.Claw;

@Config
@TeleOp(name = "Claw Tuning", group = "Tuning")
public class clawTuning extends EnhancedOpMode {

    public static double closed = Claw.ClawPosition.CLOSED.position, open = Claw.ClawPosition.OPEN.position;
    public enum clawMode {
        closed, open;
    }
    clawMode mode = clawMode.closed;

    public void initialize() {

    }
    @Override
    public void linearOpMode() {

    }
    public void primaryLoop() { // mode is just for automatic update
        if (mode.equals(clawMode.closed)) {
            claw.forcePos(closed);
        } else {
            claw.forcePos(open);
        }

        if (A1.wasJustPressed() && mode.equals(clawMode.open)) {
            mode = clawMode.closed;
        }
        if (A1.wasJustPressed() && mode.equals(clawMode.closed)) {
            mode = clawMode.open;
        }

        telemetry.addData("Mode: ", mode.name());
        telemetry.update();
    }
}
