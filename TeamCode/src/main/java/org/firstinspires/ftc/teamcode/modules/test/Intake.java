package org.firstinspires.ftc.teamcode.modules.test;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
public class Intake {
    private final DcMotorEx intake;
    public static IntakePower powerState = IntakePower.INTAKE;

    public enum IntakePower {
        INTAKE(1),
        EXTAKE(-1),
        OFF(0);
        public double power;
        IntakePower(double power){
            this.power = power;
        }
    }
    public Intake(HardwareMap hardwareMap) {
        intake = hardwareMap.get(DcMotorEx.class, "intake");
    }

    public Action setState(IntakePower powerState) {
        Intake.powerState = powerState;
        return packet -> {
            intake.setPower(powerState.power);
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