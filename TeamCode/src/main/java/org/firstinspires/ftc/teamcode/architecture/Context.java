package org.firstinspires.ftc.teamcode.architecture;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.lynx.LynxModule;

import org.firstinspires.ftc.teamcode.actions.RobotActions;
import org.firstinspires.ftc.teamcode.modules.Arm;
import org.firstinspires.ftc.teamcode.modules.Claw;
import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.opmodes.tele.wolfdrive.WolfDrive;
public class Context {
    public static Robot robot;
    public static WolfDrive drive;
    public static RobotActions actions;
    public static LynxModule[] hubs;
    public  static LynxModule controlHub;
    public  static LynxModule expansionHub;

    public static Intake intake;
    public static Arm arm;
    public static Claw claw;


    public static Pose2d farRedStart = new Pose2d(0,0,0);
    public static Pose2d poseStorage = new Pose2d(0,0,0);
    public static double TURN_SPEED = 0.75;
    public static double SLOW_TURN_SPEED = 0.3;

    public static MultipleTelemetry tel;
}
