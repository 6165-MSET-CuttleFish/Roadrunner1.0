package org.firstinspires.ftc.teamcode.actions;

import static org.firstinspires.ftc.teamcode.architecture.Context.arm;
import static org.firstinspires.ftc.teamcode.architecture.Context.intake;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;

import org.firstinspires.ftc.teamcode.modules.Arm;
import org.firstinspires.ftc.teamcode.modules.Intake;

public class RobotActions {
    public Action intakeSequence = new SequentialAction(
            intake.setState(Intake.IntakePower.INTAKE),
            new SleepAction(1),
            intake.setState(Intake.IntakePower.OFF),
            new SleepAction(1),
            intake.POWER_RAMP(),
            new SleepAction(1),
            intake.setState(Intake.IntakePower.OFF),
            new SleepAction(1)
    );

    public Action armSequence = new SequentialAction(
            arm.setState(Arm.ArmPositions.SCORING),
            new SleepAction(1),
            arm.setState(Arm.ArmPositions.DOWN)
    );


}


