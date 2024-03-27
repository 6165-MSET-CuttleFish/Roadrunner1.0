package org.firstinspires.ftc.teamcode.opmodes.tuning;

import static org.firstinspires.ftc.teamcode.architecture.Context.claw;

import com.acmerobotics.dashboard.config.Config;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.KeyReader;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.architecture.Context;
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
    KeyReader[] keyReaders;
    ButtonReader A, B, Y;
    GamepadEx g1, g2;

    public void initialize() {
        Context.claw = new Claw(hardwareMap);
        keyReaders = new KeyReader[]{
                A = new ToggleButtonReader(g1, GamepadKeys.Button.A),
                B = new ToggleButtonReader(g2, GamepadKeys.Button.B),
                Y = new ToggleButtonReader(g2, GamepadKeys.Button.Y),
        };
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

        if (A.wasJustPressed() && mode.equals(clawMode.open)) {
            mode = clawMode.closed;
        }
        if (A.wasJustPressed() && mode.equals(clawMode.closed)) {
            mode = clawMode.open;
        }

        telemetry.addData("Mode: ", mode.name());
        telemetry.update();
    }
}
