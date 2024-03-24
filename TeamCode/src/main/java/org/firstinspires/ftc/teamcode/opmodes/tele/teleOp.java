package org.firstinspires.ftc.teamcode.opmodes.tele;

import static org.firstinspires.ftc.teamcode.architecture.Context.SLOW_TURN_SPEED;
import static org.firstinspires.ftc.teamcode.architecture.Context.TURN_SPEED;
import static org.firstinspires.ftc.teamcode.architecture.Context.drive;
import static org.firstinspires.ftc.teamcode.architecture.Context.poseStorage;
import static org.firstinspires.ftc.teamcode.architecture.Context.robot;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
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
import org.firstinspires.ftc.teamcode.opmodes.tele.wolfdrive.WolfDrive;

@TeleOp
public class teleOp extends EnhancedOpMode {
    GamepadEx g1, g2;
    public static double ninja = 1;
    KeyReader[] keyReaders;
    ButtonReader A, X;
    TriggerReader LT;
    @Override
    public void primaryLoop() {

        drive(ninja);
        if (LT.wasJustPressed()) {
            ninja = 0.5;
        } else if (LT.wasJustReleased()) {
            ninja = 1;
        }
        /*if (A.wasJustPressed()) {
            claw.toggle();
        }

         */

    }
    @Override
    public void initialize() {
        g1 = new GamepadEx(gamepad1);
        g2 = new GamepadEx(gamepad2);

        robot = new Robot(hardwareMap, poseStorage);
        drive = new WolfDrive(robot);

        keyReaders = new KeyReader[]{
                A = new ToggleButtonReader(g1, GamepadKeys.Button.A),
                X = new ToggleButtonReader(g1, GamepadKeys.Button.X),
                LT = new TriggerReader(g1, GamepadKeys.Trigger.LEFT_TRIGGER)
        };
    }
    @Override
    public void linearOpMode() {
    }
    public void drive(double speed) {
        double input_x = Math.pow(-gamepad1.left_stick_y, 3) * speed;
        double input_y = Math.pow(-gamepad1.left_stick_x, 3) * speed;
        Vector2d input = new Vector2d(input_x, input_y);

        double input_turn = Math.pow(gamepad1.left_trigger - gamepad1.right_trigger, 3) * TURN_SPEED // Turn via triggers
                + Math.pow(-gamepad1.right_stick_x, 3) * TURN_SPEED;
        if (gamepad1.left_bumper) input_turn += SLOW_TURN_SPEED;
        if (gamepad1.right_bumper) input_turn -= SLOW_TURN_SPEED;
        input_turn = Range.clip(input_turn, -1, 1);

        PoseVelocity2d currentVel = robot.updatePoseEstimate();
        drive.trackPosition(robot.pose);
        drive.driveWithCorrection(new PoseVelocity2d(input, input_turn), new PoseVelocity2d(new com.acmerobotics.roadrunner.Vector2d(40.0, 40.0), 5.0));
    }

}
