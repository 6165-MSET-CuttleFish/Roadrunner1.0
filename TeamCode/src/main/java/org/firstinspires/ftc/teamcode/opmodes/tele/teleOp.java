package org.firstinspires.ftc.teamcode.opmodes.tele;

import static org.firstinspires.ftc.teamcode.architecture.Context.drive;
import static org.firstinspires.ftc.teamcode.architecture.Context.extendo;
import static org.firstinspires.ftc.teamcode.architecture.Context.poseStorage;
import static org.firstinspires.ftc.teamcode.architecture.Context.robot;
import static org.firstinspires.ftc.teamcode.architecture.Context.robotActions;
import static org.firstinspires.ftc.teamcode.architecture.Context.tel;
import static org.firstinspires.ftc.teamcode.modules.used.Extendo.ExtendoPosition.HALF_EXTEND;
import static org.firstinspires.ftc.teamcode.opmodes.tele.teleOp.Mode.DEPOSIT;
import static org.firstinspires.ftc.teamcode.opmodes.tele.teleOp.Mode.PICKUP;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.KeyReader;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.architecture.EnhancedOpMode;
import org.firstinspires.ftc.teamcode.architecture.Robot;
import org.firstinspires.ftc.teamcode.modules.used.Extendo;

@Config
@TeleOp (name="I like to move it move it")
public class teleOp extends EnhancedOpMode {
    GamepadEx g1, g2;
    public static double ninja = 1;
    public static int height = 1;
    KeyReader[] keyReaders;
    ButtonReader A, DPAD_UP, DPAD_DOWN;
    TriggerReader LT;
    public enum Mode {
        PICKUP, DEPOSIT
    }
    Mode activeMode;

    @Override
    public void primaryLoop() {
        drive(ninja);
        if (A.wasJustPressed() && activeMode.equals(DEPOSIT)) {
            Actions.runBlocking(robotActions.depositAndTransfer());
        } else if (A.wasJustReleased() && activeMode.equals(DEPOSIT)) {
            Actions.runBlocking(new SequentialAction(
                    robotActions.armPickup(1),
                    extendo.setState(Extendo.ExtendoPosition.FULL_EXTEND)
            ));
            activeMode = Mode.PICKUP;
        }
        if (A.wasJustPressed() && activeMode.equals(PICKUP)) {
            Actions.runBlocking(robotActions.pickupToTransfer());
        } else if (A.wasJustReleased() && activeMode.equals(PICKUP)) {
            Actions.runBlocking(robotActions.transferToDeposit(height, HALF_EXTEND));
            activeMode = Mode.DEPOSIT;
        }

        if (DPAD_UP.wasJustPressed()) {
            height = Math.min(height + 1, 10);
        } else if (DPAD_DOWN.wasJustPressed()) {
            height = Math.max(height - 1, 1);
        }

        tel.addData("Deposit Mode", activeMode.name());
        tel.update();
    }
    @Override
    public void initialize() {
        g1 = new GamepadEx(gamepad1);
        g2 = new GamepadEx(gamepad2);

        robot = new Robot(this, poseStorage);

        keyReaders = new KeyReader[]{
                A = new ToggleButtonReader(g1, GamepadKeys.Button.A),
                DPAD_DOWN = new ToggleButtonReader(g1, GamepadKeys.Button.DPAD_DOWN),
                DPAD_UP = new ToggleButtonReader(g1, GamepadKeys.Button.DPAD_UP),
        };
    }
    @Override
    public void linearOpMode() {
    }
    public void drive(double speed) {
        double input_x = Math.pow(-gamepad1.left_stick_y, 3) * speed;
        double input_y = Math.pow(-gamepad1.left_stick_x, 3) * speed;
        Vector2d input = new Vector2d(input_x, input_y);

        double input_turn = Math.pow(-gamepad1.right_stick_x, 3) * speed;
        input_turn = Range.clip(input_turn, -1, 1);

        PoseVelocity2d currentVel = robot.updatePoseEstimate();
        drive.trackPosition(robot.pose);
        drive.driveWithCorrection(new PoseVelocity2d(input, input_turn), currentVel);
    }

}