package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

@Config
public class Claw {
    private final Servo claw;
    public static ClawPosition positionState = ClawPosition.CLOSED;

    public enum ClawPosition {
        CLOSED(1),
        OPEN(-1);

        public double position;
        ClawPosition(double position){
            this.position = position;
        }
    }
    public Claw(HardwareMap hardwareMap) {
        claw = hardwareMap.get(Servo.class, "claw");
    }


    public Action setState(ClawPosition positionState) {
        Claw.positionState = positionState;
        return packet -> {
            claw.setPosition(positionState.position);
            return false;
        };
    }

    public Action toggle() {
        if (claw.getPosition() == ClawPosition.CLOSED.position) {
            Claw.positionState = ClawPosition.OPEN;
            return packet -> {
                claw.setPosition(positionState.position);
                return false;
            };
        }
        Claw.positionState = ClawPosition.CLOSED;
        return packet -> {
            claw.setPosition(positionState.position);
            return false;
        };
    }
}