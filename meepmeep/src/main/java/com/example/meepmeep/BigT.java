package com.example.meepmeep;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.Vector2d;
import com.noahbres.meepmeep.roadrunner.Constraints;
import com.noahbres.meepmeep.roadrunner.DriveShim;
import com.noahbres.meepmeep.roadrunner.DriveTrainType;

public class BigT {
    double MAX_VEL = 60; double MAX_ACCEL = 60; double MAX_ANGLE_VEL = 3; double MAX_ANGLE_ACCEL = 3; double TRACK_WIDTH = 12;
    public static Pose2d closeRedStart = new Pose2d(12,-61,Math.toRadians(-180));

    DriveShim robot = new DriveShim(
            DriveTrainType.MECANUM,
            new Constraints(MAX_VEL, MAX_ACCEL, MAX_ANGLE_VEL, MAX_ANGLE_ACCEL, TRACK_WIDTH),
            closeRedStart
    );
    public Action purple_yellow = robot.actionBuilder(robot.getPoseEstimate())
            .strafeToConstantHeading(new Vector2d(12, -58))
            .splineToConstantHeading(new Vector2d(10, -42), Math.toRadians(90))
            .splineToConstantHeading(new Vector2d(10, -36), Math.toRadians(90))
            .splineToConstantHeading(new Vector2d(20, -30), Math.toRadians(0))
            .splineToConstantHeading(new Vector2d(35, -30), Math.toRadians(0))
            .build();
    public Action stack1 = robot.actionBuilder(robot.getPoseEstimate())
            .splineToConstantHeading(new Vector2d(10, -12), Math.toRadians(180))
            .lineToX(-40)
            .build();

    public Action back1 = robot.actionBuilder(robot.getPoseEstimate())
            .lineToX(10)
            .splineToSplineHeading(new Pose2d(30, -20, Math.toRadians(150)), Math.toRadians(-30))
            .build();

    public Action[] action = {purple_yellow, stack1, back1};
}