package org.firstinspires.ftc.teamcode.opmodes.tuning;

import static org.firstinspires.ftc.teamcode.architecture.Context.A;
import static org.firstinspires.ftc.teamcode.architecture.Context.B;
import static org.firstinspires.ftc.teamcode.architecture.Context.Y;
import static org.firstinspires.ftc.teamcode.architecture.Context.arm;
import static org.firstinspires.ftc.teamcode.architecture.Context.poseStorage;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.architecture.EnhancedOpMode;
import org.firstinspires.ftc.teamcode.architecture.Robot;
import org.firstinspires.ftc.teamcode.modules.used.Arm;

@Config
@TeleOp(name = "Arm Tuning", group = "Tuning")
public class armTuning extends EnhancedOpMode {

    Robot robot;
    public void initialize() {
        robot = new Robot(this, poseStorage, telemetry);
    }
    @Override
    public void linearOpMode() {

    }

    public void primaryLoop() {
        if (A.wasJustPressed()) {
            Actions.runBlocking(arm.setState(Arm.ArmPosition.PICKUP));
        }
        if (B.wasJustPressed()) {
            Actions.runBlocking(arm.setState(Arm.ArmPosition.TRANSFER));
        }
        if (Y.wasJustPressed()) {
            Actions.runBlocking(arm.setState(Arm.ArmPosition.DEPOSIT));
        }
        arm.setController();
        telemetry.addData("Current", arm.getCurrent());
        telemetry.addData("Arm State", Arm.positionState);
        telemetry.update();
    }
}
