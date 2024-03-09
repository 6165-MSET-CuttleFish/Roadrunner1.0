package org.firstinspires.ftc.teamcode.opmodes.tuning;

import static org.firstinspires.ftc.teamcode.architecture.Context.intake;
import static org.firstinspires.ftc.teamcode.architecture.Context.poseStorage;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.architecture.Context;
import org.firstinspires.ftc.teamcode.architecture.EnhancedOpMode;
import org.firstinspires.ftc.teamcode.architecture.Robot;
@Config
@TeleOp(name = "Intake Tuning", group = "Tuning")
public class intakeTuning extends EnhancedOpMode {
    public void initialize() {
        Context.robot = new Robot(hardwareMap, poseStorage);
    }
    @Override
    public void linearOpMode() {

    }

    public void primaryLoop() {
        if (gamepad1.a) {
            Actions.runBlocking(intake.INTAKE());
        }
        if (gamepad1.b) {
            Actions.runBlocking(intake.EXTAKE());
        }
        if (gamepad1.y) {
            Actions.runBlocking(intake.POWER_RAMP());
        }

    }
}
