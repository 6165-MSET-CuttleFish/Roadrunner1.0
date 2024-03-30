package org.firstinspires.ftc.teamcode.opmodes.tuning;

import static org.firstinspires.ftc.teamcode.architecture.Context.A1;
import static org.firstinspires.ftc.teamcode.architecture.Context.B1;
import static org.firstinspires.ftc.teamcode.architecture.Context.Y1;
import static org.firstinspires.ftc.teamcode.architecture.Context.arm;
import static org.firstinspires.ftc.teamcode.architecture.Context.poseStorage;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.architecture.Context;
import org.firstinspires.ftc.teamcode.architecture.EnhancedOpMode;
import org.firstinspires.ftc.teamcode.architecture.Robot;
import org.firstinspires.ftc.teamcode.modules.used.Arm;

@Config
@TeleOp(name = "Arm Tuning", group = "Tuning")
public class armTuning extends EnhancedOpMode {
    public void initialize() {
        Context.robot = new Robot(this, poseStorage);
    }
    @Override
    public void linearOpMode() {

    }

    public void primaryLoop() {
        if (A1.wasJustPressed()) {
            Actions.runBlocking(arm.setState(Arm.ArmPosition.PICKUP));
        }
        if (B1.wasJustPressed()) {
            Actions.runBlocking(arm.setState(Arm.ArmPosition.TRANSFER));
        }
        if (Y1.wasJustPressed()) {
            Actions.runBlocking(arm.setState(Arm.ArmPosition.DEPOSIT));
        }
        arm.setController();
    }
}
