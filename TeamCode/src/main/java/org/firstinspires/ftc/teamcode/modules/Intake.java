package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
public class Intake {
    private final DcMotorEx intake;
    public static double intakePower = 1;
    public static double extakePower = -1;
    public static double offPower = 0;
    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class, "intake");
    }

    public Action INTAKE() {
        return packet -> {
            intake.setPower(intakePower);
            return false;
        };
    }
    public Action EXTAKE() {
        return packet -> {
            intake.setPower(extakePower);
            return false;
        };
    }
    public Action OFF() {
        return packet -> {
            intake.setPower(offPower);
            return false;
        };
    }
    public Action POWER_RAMP() {
        ElapsedTime timer = new ElapsedTime();
        return packet -> {
            intake.setPower(timer.seconds());
            return intake.getPower() < 0.9;
        };
    }

    public double getCurrent() {
        return intake.getCurrent(CurrentUnit.AMPS);
    }
}