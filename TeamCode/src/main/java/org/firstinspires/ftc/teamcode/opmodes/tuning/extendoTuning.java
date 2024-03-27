package org.firstinspires.ftc.teamcode.opmodes.tuning;

import static org.firstinspires.ftc.teamcode.architecture.Context.extendo;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.KeyReader;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.architecture.EnhancedOpMode;
import org.firstinspires.ftc.teamcode.modules.used.Extendo;

@Config
@TeleOp(name = "Extendo Tuning", group = "Tuning")
public class extendoTuning extends EnhancedOpMode {
    KeyReader[] keyReaders;
    ButtonReader A, B, Y;
    GamepadEx g1, g2;

    public void initialize() {
        extendo = new Extendo(hardwareMap);
        g1 = new GamepadEx(gamepad1);
        keyReaders = new KeyReader[]{
                A = new ToggleButtonReader(g1, GamepadKeys.Button.A),
                B = new ToggleButtonReader(g1, GamepadKeys.Button.B),
                Y = new ToggleButtonReader(g1, GamepadKeys.Button.Y),
        };
    }
    @Override
    public void linearOpMode() {

    }

    public void primaryLoop() {
        if (A.wasJustPressed()) {
            Actions.runBlocking(extendo.setState(Extendo.ExtendoPosition.IN));
        }
        if (B.wasJustPressed()) {
            Actions.runBlocking(extendo.setState(Extendo.ExtendoPosition.HALF_EXTEND));
        }
        if (Y.wasJustPressed()) {
            Actions.runBlocking(extendo.setState(Extendo.ExtendoPosition.FULL_EXTEND));
        }
        extendo.setController();
        telemetry.addData("Current", extendo.getCurrent());
        telemetry.update();
    }
}
