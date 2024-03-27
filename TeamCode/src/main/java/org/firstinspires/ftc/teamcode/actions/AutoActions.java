package org.firstinspires.ftc.teamcode.actions;

import static org.firstinspires.ftc.teamcode.architecture.Context.robotActions;
import static org.firstinspires.ftc.teamcode.architecture.Context.trajectories;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;

import org.firstinspires.ftc.teamcode.architecture.EnhancedActions.awaitX;
import org.firstinspires.ftc.teamcode.modules.used.Extendo;

import java.util.Arrays;

public class AutoActions {
    public Action purple_yellow = new ParallelAction(
            trajectories.purple_yellow,
            robotActions.transferToDeposit(0, Extendo.ExtendoPosition.HALF_EXTEND),
            new awaitX(30, Arrays.asList(
                    robotActions.depositAndTransfer()
            ))
    );
    public Action stack1 = new ParallelAction(
            trajectories.stack1,
            new awaitX(-15, Arrays.asList(
                    robotActions.armResetPickup(4, 100)
            ))
    );
    public Action back1 = new SequentialAction(
            trajectories.stack1,
            new awaitX(12, Arrays.asList(
                    robotActions.transferToDeposit(0, Extendo.ExtendoPosition.HALF_EXTEND)
            )),
            new awaitX(30, Arrays.asList(
                    robotActions.depositAndTransfer()
            ))
    );
}
