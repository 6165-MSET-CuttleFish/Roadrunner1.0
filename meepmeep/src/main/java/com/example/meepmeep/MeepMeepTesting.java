package com.example.meepmeep;

import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.geometry.Vector2d;
import com.noahbres.meepmeep.MeepMeep;
import com.noahbres.meepmeep.roadrunner.DefaultBotBuilder;
import com.noahbres.meepmeep.roadrunner.entity.RoadRunnerBotEntity;

public class MeepMeepTesting {

    public static Pose2d closeRedStart = new Pose2d(12,-61,Math.toRadians(-180));
    public static void main(String[] args) {
        MeepMeep meepMeep = new MeepMeep(800);

        RoadRunnerBotEntity myBot = new DefaultBotBuilder(meepMeep)
                // Set bot constraints: maxVel, maxAccel, maxAngVel, maxAngAccel, track width
                .setConstraints(60, 60, Math.toRadians(180), Math.toRadians(180), 15)
                .followTrajectorySequence(drive ->
                        drive.trajectorySequenceBuilder(closeRedStart)
                                .lineTo(new Vector2d(12, -58))
                                .splineToConstantHeading(new Vector2d(10, -42), Math.toRadians(90))
                                .splineToConstantHeading(new Vector2d(10, -36), Math.toRadians(90))
                                .lineTo(new Vector2d(10, -35))
                                .splineToConstantHeading(new Vector2d(20, -30), Math.toRadians(0))
                                .splineToConstantHeading(new Vector2d(35, -30), Math.toRadians(0))

                                .waitSeconds(1)
                                .splineToConstantHeading(new Vector2d(10, -12), Math.toRadians(180))
                                .lineTo(new Vector2d(-40,-12))

                                .lineTo(new Vector2d(10,-12))
                                .splineToSplineHeading(new Pose2d(30, -20, Math.toRadians(150)), Math.toRadians(-30))
                                .build()
                );

        meepMeep.setBackground(MeepMeep.Background.FIELD_CENTERSTAGE_JUICE_DARK)
                .setDarkMode(true)
                .setBackgroundAlpha(0.95f)
                .addEntity(myBot)
                .start();
    }
}

