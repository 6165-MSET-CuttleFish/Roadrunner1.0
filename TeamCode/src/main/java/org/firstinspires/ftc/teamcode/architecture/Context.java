package org.firstinspires.ftc.teamcode.architecture;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.KeyReader;
import com.qualcomm.hardware.lynx.LynxModule;

import org.firstinspires.ftc.teamcode.actions.AutoActions;
import org.firstinspires.ftc.teamcode.actions.RobotActions;
import org.firstinspires.ftc.teamcode.actions.Trajectories;
import org.firstinspires.ftc.teamcode.modules.test.Intake;
import org.firstinspires.ftc.teamcode.modules.test.MotionProfileModule;
import org.firstinspires.ftc.teamcode.modules.used.Arm;
import org.firstinspires.ftc.teamcode.modules.used.Claw;
import org.firstinspires.ftc.teamcode.modules.used.Extendo;
import org.firstinspires.ftc.teamcode.opmodes.archive.wolfdrive.WolfDrive;
public class Context {
    public static Robot robot;
    public static WolfDrive drive;
    public static RobotActions robotActions;
    public static AutoActions autoActions;
    public static Trajectories trajectories;
    public static LynxModule[] hubs;
    public  static LynxModule controlHub;
    public  static LynxModule expansionHub;
    public static Intake intake;
    public static MotionProfileModule motionProfileModule;
    public static Claw claw;
    public static Extendo extendo;
    public static Arm arm;
    public static Pose2d farRedStart = new Pose2d(15,-61,Math.toRadians(-90));
    public static Pose2d poseStorage = new Pose2d(0,0,0);
    public static MultipleTelemetry tel;

    public static KeyReader[] keyReaders;
    public static ButtonReader A, B, Y;
    public static GamepadEx g1, g2;
}
