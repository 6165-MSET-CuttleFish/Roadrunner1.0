package org.firstinspires.ftc.teamcode.actions;

import static org.firstinspires.ftc.teamcode.architecture.Context.actions;
import static org.firstinspires.ftc.teamcode.actions.EnhancedActions.awaitX;
import static org.firstinspires.ftc.teamcode.actions.EnhancedActions.awaitTime;


import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.SequentialAction;

import java.util.Arrays;

public class AutoActions {
    public static Action timeTrajectory = new SequentialAction(
            Trajectories.traj,
            new awaitTime(3, Arrays.asList(
                    actions.intakeSequence
            ))
    );
    public static Action posTrajectory = new SequentialAction(
            Trajectories.traj,
            new awaitX(10, 1, Arrays.asList(
                    actions.intakeSequence
            ))
    );

}
