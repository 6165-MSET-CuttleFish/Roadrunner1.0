package org.firstinspires.ftc.teamcode.architecture;

import static org.firstinspires.ftc.teamcode.architecture.Context.controlHub;
import static org.firstinspires.ftc.teamcode.architecture.Context.g1;
import static org.firstinspires.ftc.teamcode.architecture.Context.g2;
import static org.firstinspires.ftc.teamcode.architecture.Context.hubs;
import static org.firstinspires.ftc.teamcode.architecture.Context.robotActions;

import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.KeyReader;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.arcrobotics.ftclib.gamepad.TriggerReader;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LynxModuleImuType;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.actions.AutoActions;
import org.firstinspires.ftc.teamcode.actions.RobotActions;
import org.firstinspires.ftc.teamcode.actions.Trajectories;
import org.firstinspires.ftc.teamcode.modules.used.Arm;
import org.firstinspires.ftc.teamcode.modules.used.Extendo;
import org.firstinspires.ftc.teamcode.opmodes.archive.wolfdrive.WolfDrive;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

import java.util.ArrayList;

public class Robot extends MecanumDrive {

    public static KeyReader[] keyReaders;
    public Robot(LinearOpMode opMode, Pose2d pose) {
        super(opMode.hardwareMap, pose, false);
        Context.autoActions = new AutoActions();
        Context.trajectories = new Trajectories(); // re-initialize statics
        Context.drive = new WolfDrive();
        
        g1 = new GamepadEx(opMode.gamepad1);
        g2 = new GamepadEx(opMode.gamepad2);

        keyReaders = returnKeyReaders(); // loop times are tiny so it's fine to check every button

        initializeHub(opMode.hardwareMap);

        Context.extendo = new Extendo(opMode.hardwareMap);
        Context.arm = new Arm(opMode.hardwareMap);


        robotActions = new RobotActions();


    }
    public boolean returnIfReached(int tolerance) {
        if (this.leftBack.getCurrentPosition() > tolerance) {
            return true;
        }
        return false;
    }
    public String getPose() {
        if (pose != null) {
            return "x: " + pose.position.x + ", y: " + pose.position.y + ", heading: " + Math.round(Math.toRadians(pose.heading.real));
        }
        return "no pose L";
    }

    public void initializeHub(HardwareMap hardwareMap) {
        try {
            hubs = new ArrayList<>(hardwareMap.getAll(LynxModule.class)).toArray(new LynxModule[0]);

            for(LynxModule module: hubs)
            {
                if(module.getImuType()== LynxModuleImuType.BHI260)
                    Context.controlHub=module;
                else if(module.getImuType() == LynxModuleImuType.BNO055)
                    Context.expansionHub=module;
            }
        } catch (RuntimeException e) {
            throw new RuntimeException("One or more of the REV hubs could not be found. More info: " + e);
        }
    }

    public double getCurrent() {
        return controlHub.getCurrent(CurrentUnit.AMPS) + controlHub.getCurrent(CurrentUnit.AMPS);
    }
    public double getServoBusCurrent(LynxModule bus) {
        return bus.getGpioBusCurrent(CurrentUnit.AMPS);
    }

    public KeyReader[] returnKeyReaders() {
        return new KeyReader[]{
                Context.A1 = new ToggleButtonReader(g1, GamepadKeys.Button.A),
                Context.B1 = new ToggleButtonReader(g1, GamepadKeys.Button.B),
                Context.Y1 = new ToggleButtonReader(g1, GamepadKeys.Button.Y),
                Context.X1 = new ToggleButtonReader(g1, GamepadKeys.Button.X),
                Context.LSB1 = new ToggleButtonReader(g1, GamepadKeys.Button.LEFT_STICK_BUTTON),
                Context.RSB1= new ToggleButtonReader(g1, GamepadKeys.Button.RIGHT_STICK_BUTTON),
                Context.DPAD_UP1 = new ToggleButtonReader(g1, GamepadKeys.Button.DPAD_UP),
                Context.DPAD_DOWN1 = new ToggleButtonReader(g1, GamepadKeys.Button.DPAD_DOWN),
                Context.DPAD_LEFT1 = new ToggleButtonReader(g1, GamepadKeys.Button.DPAD_LEFT),
                Context.DPAD_RIGHT1 = new ToggleButtonReader(g1, GamepadKeys.Button.DPAD_RIGHT),
                Context.LB1 = new ToggleButtonReader(g1, GamepadKeys.Button.LEFT_BUMPER),
                Context.RB1 = new ToggleButtonReader(g1, GamepadKeys.Button.RIGHT_BUMPER),

                Context.A2 = new ToggleButtonReader(g2, GamepadKeys.Button.A),
                Context.B2 = new ToggleButtonReader(g2, GamepadKeys.Button.B),
                Context.Y2 = new ToggleButtonReader(g2, GamepadKeys.Button.Y),
                Context.X2 = new ToggleButtonReader(g2, GamepadKeys.Button.X),
                Context.LSB2 = new ToggleButtonReader(g2, GamepadKeys.Button.LEFT_STICK_BUTTON),
                Context.RSB2= new ToggleButtonReader(g2, GamepadKeys.Button.RIGHT_STICK_BUTTON),
                Context.DPAD_UP2 = new ToggleButtonReader(g2, GamepadKeys.Button.DPAD_UP),
                Context.DPAD_DOWN2 = new ToggleButtonReader(g2, GamepadKeys.Button.DPAD_DOWN),
                Context.DPAD_LEFT2 = new ToggleButtonReader(g2, GamepadKeys.Button.DPAD_LEFT),
                Context.DPAD_RIGHT2 = new ToggleButtonReader(g2, GamepadKeys.Button.DPAD_RIGHT),
                Context.LB2 = new ToggleButtonReader(g2, GamepadKeys.Button.LEFT_BUMPER),
                Context.RB2 = new ToggleButtonReader(g2, GamepadKeys.Button.RIGHT_BUMPER),

                Context.LT1 = new TriggerReader(g1, GamepadKeys.Trigger.LEFT_TRIGGER),
                Context.RT1 = new TriggerReader(g1, GamepadKeys.Trigger.RIGHT_TRIGGER),

                Context.LT2 = new TriggerReader(g2, GamepadKeys.Trigger.LEFT_TRIGGER),
                Context.RT2 = new TriggerReader(g2, GamepadKeys.Trigger.RIGHT_TRIGGER),
        };
    }


}
