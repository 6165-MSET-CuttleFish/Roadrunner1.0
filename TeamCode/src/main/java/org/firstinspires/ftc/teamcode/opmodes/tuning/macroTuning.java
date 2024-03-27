package org.firstinspires.ftc.teamcode.opmodes.tuning;

import static org.firstinspires.ftc.teamcode.architecture.Context.extendo;
import static org.firstinspires.ftc.teamcode.architecture.Context.poseStorage;
import static org.firstinspires.ftc.teamcode.architecture.Context.robot;
import static org.firstinspires.ftc.teamcode.architecture.Context.robotActions;
import static org.firstinspires.ftc.teamcode.architecture.Context.tel;
import static org.firstinspires.ftc.teamcode.modules.used.Extendo.ExtendoPosition.HALF_EXTEND;

import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.KeyReader;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.architecture.EnhancedOpMode;
import org.firstinspires.ftc.teamcode.architecture.Robot;
import org.firstinspires.ftc.teamcode.architecture.TelemetryReadout;
import org.firstinspires.ftc.teamcode.modules.used.Extendo;

@TeleOp (name="Macro Tuning", group="Tuning")
public class macroTuning extends EnhancedOpMode {
    GamepadEx g1, g2;
    public static double ninja = 1;
    public static int height = 1;
    KeyReader[] keyReaders;
    ButtonReader A, DPAD_UP, DPAD_DOWN;
    TriggerReader LT;
    public enum Mode {
        PICKUP, DEPOSIT
    }
    Mode activeMode = Mode.DEPOSIT;

    @Override
    public void primaryLoop() {
        if (A.wasJustPressed() && activeMode.equals(Mode.DEPOSIT)) {
            Actions.runBlocking(robotActions.depositAndTransfer());
        } else if (A.wasJustReleased() && activeMode.equals(Mode.DEPOSIT)) {
            Actions.runBlocking(new SequentialAction(
                    robotActions.armPickup(1),
                    extendo.setState(Extendo.ExtendoPosition.FULL_EXTEND)
            ));
            activeMode = Mode.PICKUP;
        }
        if (A.wasJustPressed() && activeMode.equals(Mode.PICKUP)) {
            Actions.runBlocking(robotActions.pickupToTransfer());
        } else if (A.wasJustReleased() && activeMode.equals(Mode.PICKUP)) {
            Actions.runBlocking(robotActions.transferToDeposit(height, HALF_EXTEND));
            activeMode = Mode.DEPOSIT;
        }

        if (DPAD_UP.wasJustPressed()) {
            height = Math.min(height + 1, 10);
        } else if (DPAD_DOWN.wasJustPressed()) {
            height = Math.max(height - 1, 1);
        }

        tel.addData("Deposit Mode", activeMode.name());
        TelemetryReadout.addTelemetry();
        tel.update();
    }
    @Override
    public void initialize() {
        g1 = new GamepadEx(gamepad1);
        g2 = new GamepadEx(gamepad2);

        robot = new Robot(this, poseStorage, telemetry);

        keyReaders = new KeyReader[]{
                A = new ToggleButtonReader(g1, GamepadKeys.Button.A),
                DPAD_UP = new ToggleButtonReader(g1, GamepadKeys.Button.DPAD_UP),
                DPAD_DOWN = new ToggleButtonReader(g1, GamepadKeys.Button.DPAD_DOWN),
        };
    }
    @Override
    public void linearOpMode() {}
}