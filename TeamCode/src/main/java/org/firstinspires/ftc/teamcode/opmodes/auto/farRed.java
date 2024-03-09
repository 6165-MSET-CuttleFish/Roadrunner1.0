package org.firstinspires.ftc.teamcode.opmodes.auto;

import static org.firstinspires.ftc.teamcode.architecture.Context.robot;

import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.actions.AutoActions;
import org.firstinspires.ftc.teamcode.architecture.Context;
import org.firstinspires.ftc.teamcode.architecture.EnhancedOpMode;
import org.firstinspires.ftc.teamcode.architecture.Robot;

@Autonomous (name = "Far Red")
public class farRed extends EnhancedOpMode {
    @Override
    public void primaryLoop() {

    }
    @Override
    public void initialize() {
        robot = new Robot(hardwareMap, Context.farRedStart);
    }

    @Override
    public void linearOpMode() {
        Actions.runBlocking(AutoActions.posTrajectory);
        Actions.runBlocking(AutoActions.timeTrajectory);
        die();
    }
}
