package org.firstinspires.ftc.teamcode.modules;

import com.acmerobotics.roadrunner.Action;
import com.arcrobotics.ftclib.command.SubsystemBase;
import com.arcrobotics.ftclib.trajectory.TrapezoidProfile;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.PwmControl;
import com.qualcomm.robotcore.hardware.ServoImplEx;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Arm extends SubsystemBase {

    private ServoImplEx leftServo;
    private ServoImplEx rightServo;
    private TrapezoidProfile.Constraints constraints =
            new TrapezoidProfile.Constraints(1.6, 1.8);
    private TrapezoidProfile armProfile =
            new TrapezoidProfile(constraints, new TrapezoidProfile.State(ArmPositions.DOWN.position, 0),
                    new TrapezoidProfile.State(ArmPositions.DOWN.position, 0)
            );
    private ElapsedTime timer = new ElapsedTime();
    private double prevTarget = ArmPositions.DOWN.position;
    private boolean isRunning = false;
    public enum ArmPositions {
        DOWN(0), SCORING(1);

        public double position;
        ArmPositions(double position){
            this.position = position;
        }
    }
    public Arm(HardwareMap hardwareMap){

        leftServo = hardwareMap.get(ServoImplEx.class, "leftArm");
        rightServo = hardwareMap.get(ServoImplEx.class, "rightArm");

        leftServo.setPwmRange(new PwmControl.PwmRange(500, 2500));
        rightServo.setPwmRange(new PwmControl.PwmRange(500, 2500));
    }

    @Override
    public void periodic(){
        if(!armProfile.isFinished(timer.seconds())) {

            double position = armProfile.calculate(timer.seconds()).position;

            //Set servo positions
            leftServo.setPosition(position);
            rightServo.setPosition(position);
            isRunning = true;

        } else {
            isRunning = false;
        }


    }

    //Set the servos to a numerical position
    public void setPosition(double target) {

        //Create a new profile starting from the last position command
        if(prevTarget != target){
            armProfile = new TrapezoidProfile(
                    constraints,
                    new TrapezoidProfile.State(target, 0),
                    new TrapezoidProfile.State(leftServo.getPosition(), 0)
            );

            timer.reset();
        }

        prevTarget = target;

    }
    public double getPosition(){
        return leftServo.getPosition();
    }
    public Action forceState(ArmPositions armPosition) {
        return packet -> {
            leftServo.setPosition(armPosition.position);
            rightServo.setPosition(armPosition.position);
            return false;
        };
    }
    public Action setState(ArmPositions armPosition) {
        return packet -> {
            setPosition(armPosition.position);
            return isRunning;
        };
    }


}