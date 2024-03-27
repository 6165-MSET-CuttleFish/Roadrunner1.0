package org.firstinspires.ftc.teamcode.actions;

import static org.firstinspires.ftc.teamcode.architecture.Context.robot;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;

public class Trajectories {
    public Action purple_yellow = robot.actionBuilder(robot.pose)
            .lineToY(-58)
            .splineToConstantHeading(new Vector2d(10, -42), Math.toRadians(90))
            .splineToConstantHeading(new Vector2d(10, -36), Math.toRadians(90))
            .lineToY( -35)
            .splineToConstantHeading(new Vector2d(20, -30), Math.toRadians(0))
            .splineToConstantHeading(new Vector2d(35, -30), Math.toRadians(0))
            .build();
    public Action stack1 = robot.actionBuilder(robot.pose)
            .splineToConstantHeading(new Vector2d(10, -12), Math.toRadians(180))
            .lineToX(-40)
            .build();

    public Action back1 = robot.actionBuilder(robot.pose)
            .lineToX(10)
            .splineToSplineHeading(new Pose2d(30, -20, Math.toRadians(150)), Math.toRadians(-30))
            .build();

    public Action[] action = {purple_yellow, stack1, back1};
}