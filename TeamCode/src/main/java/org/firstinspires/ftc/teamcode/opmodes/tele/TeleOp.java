package org.firstinspires.ftc.teamcode.opmodes.tele;

import static org.firstinspires.ftc.teamcode.architecture.Context.SLOW_TURN_SPEED;
import static org.firstinspires.ftc.teamcode.architecture.Context.TURN_SPEED;
import static org.firstinspires.ftc.teamcode.architecture.Context.drive;
import static org.firstinspires.ftc.teamcode.architecture.Context.poseStorage;
import static org.firstinspires.ftc.teamcode.architecture.Context.robot;
import static org.firstinspires.ftc.teamcode.architecture.Context.speed;

import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.util.Range;

import org.firstinspires.ftc.teamcode.architecture.EnhancedOpMode;
import org.firstinspires.ftc.teamcode.architecture.Robot;
import org.firstinspires.ftc.teamcode.opmodes.tele.wolfdrive.WolfDrive;

public class TeleOp extends EnhancedOpMode {

    @Override
    public void primaryLoop() {
        drive();
    }
    @Override
    public void initialize() {
        robot = new Robot(hardwareMap, poseStorage);
        drive = new WolfDrive(robot);
    }

    @Override
    public void linearOpMode() {

    }

    public void drive() {
        double input_x = Math.pow(-gamepad1.left_stick_y, 3) * speed;
        double input_y = Math.pow(-gamepad1.left_stick_x, 3) * speed;
        Vector2d input = new Vector2d(input_x, input_y);

        double input_turn = Math.pow(gamepad1.left_trigger - gamepad1.right_trigger, 3) * TURN_SPEED // Turn via triggers
                + Math.pow(-gamepad1.right_stick_x, 3) * TURN_SPEED; // Turn via right stick
        if (gamepad1.left_bumper) input_turn += SLOW_TURN_SPEED;
        if (gamepad1.right_bumper) input_turn -= SLOW_TURN_SPEED;
        input_turn = Range.clip(input_turn, -1, 1);

        PoseVelocity2d currentVel = robot.updatePoseEstimate();
        drive.trackPosition(robot.pose);
        drive.driveWithCorrection(new PoseVelocity2d(input, input_turn), currentVel);
    }

}
