package org.firstinspires.ftc.teamcode.roadrunner;

import com.acmerobotics.roadrunner.AngularVelConstraint;
import com.acmerobotics.roadrunner.MinVelConstraint;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;

import java.util.Arrays;

public class Constraints {
    public static MinVelConstraint V(double VTranslate, double VAngular) {
        return new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(VTranslate),
                new AngularVelConstraint(VAngular)
        ));
    }
    public static MinVelConstraint V(double VTranslate) {
        return new MinVelConstraint(Arrays.asList(
                new TranslationalVelConstraint(VTranslate)
        ));
    }
    public static ProfileAccelConstraint A(double decel, double accel) {
        decel = -1 * Math.abs(decel);
        return new ProfileAccelConstraint(decel, accel);
    }
}
