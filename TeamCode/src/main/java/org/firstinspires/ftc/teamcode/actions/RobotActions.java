package org.firstinspires.ftc.teamcode.actions;

import static org.firstinspires.ftc.teamcode.architecture.Context.arm;
import static org.firstinspires.ftc.teamcode.architecture.Context.claw;
import static org.firstinspires.ftc.teamcode.architecture.Context.extendo;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;

import org.firstinspires.ftc.teamcode.architecture.EnhancedActions.awaitCondition;
import org.firstinspires.ftc.teamcode.modules.used.Arm;
import org.firstinspires.ftc.teamcode.modules.used.Claw;
import org.firstinspires.ftc.teamcode.modules.used.Extendo;

import java.util.Arrays;

public class RobotActions {

    public Action armPickup(int height) {
        return new ParallelAction(
                arm.setAutoHeight(height),
                claw.setState(Claw.ClawPosition.OPEN)
                );
    }
    public Action armResetPickup(int height, int thresh) {
        boolean waitForArm = (Math.abs(Arm.arm.getCurrentPosition() - Arm.arm.getTargetPosition()) < thresh);
        return new ParallelAction(
                arm.setAutoHeight(height),
                claw.setState(Claw.ClawPosition.OPEN),
                new awaitCondition(waitForArm, Arrays.asList(
                        extendo.setState(Extendo.ExtendoPosition.FULL_EXTEND)
                ))

        );
    }
    public Action pickupToTransfer() {
        return new ParallelAction(
                claw.setState(Claw.ClawPosition.CLOSED),
                new ParallelAction(
                        new SleepAction(0.5),
                        extendo.setState(Extendo.ExtendoPosition.IN)
                )
        );
    }
    public Action transferToDeposit(int depositHeight, Extendo.ExtendoPosition extendoPos) {
        boolean waitForArm = (Math.abs(Arm.arm.getCurrentPosition() - Arm.arm.getTargetPosition()) < 500);
        return new ParallelAction(
                arm.setDepositHeight(depositHeight),
                new awaitCondition(waitForArm, Arrays.asList(
                        extendo.setState(extendoPos)
                ))
        );
    }

    public Action depositAndTransfer() {
        return new ParallelAction(
                claw.setState(Claw.ClawPosition.OPEN),
                new SequentialAction(
                    new SleepAction(0.3),
                    arm.setState(Arm.ArmPosition.TRANSFER),
                    extendo.setState(Extendo.ExtendoPosition.IN)
                )
        );
    }

}


