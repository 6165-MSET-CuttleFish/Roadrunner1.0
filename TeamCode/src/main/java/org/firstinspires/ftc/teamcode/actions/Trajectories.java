package org.firstinspires.ftc.teamcode.actions;

import static org.firstinspires.ftc.teamcode.architecture.Context.robot;
import static org.firstinspires.ftc.teamcode.roadrunner.Constraints.A;
import static org.firstinspires.ftc.teamcode.roadrunner.Constraints.V;

import com.acmerobotics.roadrunner.Action;

public class Trajectories {
    public static Action traj = robot.actionBuilder(robot.pose)
            .lineToYSplineHeading(33, Math.toRadians(0), V(30), A(30, 50))
            .waitSeconds(2)
            .lineToX(30)
            .waitSeconds(1)
            .build();
    public static Action traj2 = robot.actionBuilder(robot.pose)
            .lineToYSplineHeading(33, Math.toRadians(0), V(30), A(30, 50))
            .lineToX(30)
            .build();

    public static Action traj3 = robot.actionBuilder(robot.pose)
            .lineToYSplineHeading(33, Math.toRadians(0), V(30), A(30, 50))
            .lineToX(30)
            .build();

    public static Action[] actions = {traj, traj2, traj3};
}