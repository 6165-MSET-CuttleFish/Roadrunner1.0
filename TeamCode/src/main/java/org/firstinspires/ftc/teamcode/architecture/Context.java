package org.firstinspires.ftc.teamcode.architecture;

import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.ButtonReader;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.KeyReader;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
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
    public static Pose2d closeRedStart = new Pose2d(12,-61,Math.toRadians(-180));
    public static Pose2d poseStorage = new Pose2d(0,0,0);
    public static MultipleTelemetry tel;
    public static KeyReader[] keyReaders;
    public static ButtonReader
            A1, B1, Y1, X1,
            LSB1, RSB1,
            DPAD_UP1, DPAD_DOWN1, DPAD_LEFT1, DPAD_RIGHT1,
            LB1, RB1,
            A2, B2, Y2, X2,
            LSB2, RSB2,
            DPAD_UP2, DPAD_DOWN2, DPAD_LEFT2, DPAD_RIGHT2,
            LB2, RB2;
    public static TriggerReader
            LT1, RT1, LT2, RT2;
    public static GamepadEx g1, g2;
    public static String statusError = "";
}
