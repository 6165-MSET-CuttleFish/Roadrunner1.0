package org.firstinspires.ftc.teamcode.actions;

import static org.firstinspires.ftc.teamcode.architecture.Context.intake;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;

public class RobotActions {
    public Action intakeSequence = new SequentialAction(
            intake.INTAKE(),
            new SleepAction(1),
            intake.OFF(),
            new SleepAction(1),
            intake.POWER_RAMP(),
            new SleepAction(1)
    );


}


