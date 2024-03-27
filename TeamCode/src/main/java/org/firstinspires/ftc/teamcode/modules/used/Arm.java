package org.firstinspires.ftc.teamcode.modules.used;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.controller.PIDController;
import com.qualcomm.robotcore.hardware.DcMotorEx;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;

@Config
public class Arm {
    public static DcMotorEx arm;
    public static ArmPosition positionState = ArmPosition.TRANSFER;
    private PIDController controller;
    public static double p = 0, i = 0, d = 0, f = 0;
    public static double target = 0;
    public static double targetThresh = 3.0, currentThresh = 4.0; // ticks, amps
    public static double cycleOffset = 30; // amount of tick change for every pixel up the stack
    public static double pixelOffset = 30; // amount of tick change for every pixel up the backdrop

    public enum ArmPosition {
        PICKUP(-1500),
        DEPOSIT(1500),
        TRANSFER(0);
        public double target;
        ArmPosition(double position){
            Extendo.target = position;
            target = position;
        }
    }
    public Arm(HardwareMap hardwareMap) {
        arm = hardwareMap.get(DcMotorEx.class, "arm");
        controller = new PIDController(p, i, d);
    }

    public void setController() {
        controller = new PIDController(p, i, d);
    }
    public Action setState(ArmPosition positionState) {
        Arm.positionState = positionState;
        double currentPosition = arm.getCurrentPosition();
        return (target - currentPosition <= targetThresh || getCurrent() > currentThresh) ?
                packet -> false :
                packet -> {
                    pid();
                    return true;
                };
    }


    public Action setAutoHeight(int cycle) { // 1 is 1,2 ... 4 is 4,5
        Arm.positionState = ArmPosition.PICKUP; // baseline
        double offset = (cycle - 1) * cycleOffset;
        target = positionState.target + offset;
        double currentPosition = arm.getCurrentPosition();
        return (target - currentPosition <= targetThresh || getCurrent() > currentThresh) ?
                packet -> false :
                packet -> {
                    pid();
                    return true;
                };
    }

    public Action setDepositHeight(int depositHeight) { // 1 is 1,2 ... 4 is 4,5
        Arm.positionState = ArmPosition.DEPOSIT; // baseline (first pixel height)
        double offset = (depositHeight - 1) * -pixelOffset;
        target = positionState.target + offset;
        double currentPosition = arm.getCurrentPosition();
        return (target - currentPosition <= targetThresh || getCurrent() > currentThresh) ?
                packet -> false :
                packet -> {
                    pid();
                    return true;
                };
    }
    public double getCurrent() {
        return arm.getCurrent(CurrentUnit.AMPS);
    }

    public void pid() {
        double current = arm.getCurrentPosition();
        controller.setPID(p, i, d);
        double pid = controller.calculate(current, target) + f;
        arm.setPower(pid);
    }

}