package org.firstinspires.ftc.teamcode.modules.used;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
public class Extendo {
    private final DcMotorEx left_extendo, right_extendo;
    public static ExtendoPosition positionState = ExtendoPosition.IN;
    private PIDController controller;
    public static double p = 100, i = 0, d = 0, f = 0;
    public static double target = 0;
    public static double targetThresh = 3.0, currentThresh = 4.0; // ticks, amps

    public enum ExtendoPosition {
        FULL_EXTEND(3000),
        HALF_EXTEND(1500),
        IN(0);
        ExtendoPosition(double position){
            target = position;
        }
    }
    public Extendo(HardwareMap hardwareMap) {
        left_extendo = hardwareMap.get(DcMotorEx.class, "left_extendo");
        right_extendo = hardwareMap.get(DcMotorEx.class, "right_extendo");
        controller = new PIDController(p, i, d);
    }
    public void setController() {
        controller = new PIDController(p, i, d);
    }
    public Action setState(ExtendoPosition positionState) {
        Extendo.positionState = positionState;
        double currentPosition = left_extendo.getCurrentPosition();

        return (target - currentPosition <= targetThresh || getCurrent() > currentThresh) ?
                packet -> false :
                packet -> {
                    pid();
                    return true;
                };
    }
    public double getCurrent() {
        return (left_extendo.getCurrent(CurrentUnit.AMPS) + right_extendo.getCurrent(CurrentUnit.AMPS));
    }

    public void pid() {
        double current = left_extendo.getCurrentPosition();
        controller.setPID(p, i, d);
        double pid = controller.calculate(current, target) + f;
        left_extendo.setPower(pid);
        right_extendo.setPower(pid);
    }

}