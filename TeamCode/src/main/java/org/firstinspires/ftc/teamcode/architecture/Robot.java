package org.firstinspires.ftc.teamcode.architecture;

import static org.firstinspires.ftc.teamcode.architecture.Context.controlHub;
import static org.firstinspires.ftc.teamcode.architecture.Context.expansionHub;
import static org.firstinspires.ftc.teamcode.architecture.Context.g1;
import static org.firstinspires.ftc.teamcode.architecture.Context.hubs;
import static org.firstinspires.ftc.teamcode.architecture.Context.robotActions;
import static org.firstinspires.ftc.teamcode.architecture.Context.tel;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.roadrunner.Pose2d;
import com.arcrobotics.ftclib.gamepad.GamepadEx;
import com.arcrobotics.ftclib.gamepad.GamepadKeys;
import com.arcrobotics.ftclib.gamepad.KeyReader;
import com.arcrobotics.ftclib.gamepad.ToggleButtonReader;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LynxModuleImuType;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.actions.RobotActions;
import org.firstinspires.ftc.teamcode.modules.used.Arm;
import org.firstinspires.ftc.teamcode.modules.used.Extendo;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

import java.util.ArrayList;

public class Robot extends MecanumDrive {

    public static KeyReader[] keyReaders;
    public Robot(LinearOpMode opMode, Pose2d pose, Telemetry telemetry) {
        super(opMode.hardwareMap, pose, false);
       /* autoActions = new AutoActions();
        trajectories = new Trajectories(); // re-initialize statics
        Context.drive = new WolfDrive();
        */
        g1 = new GamepadEx(opMode.gamepad1);

        keyReaders = new KeyReader[]{
                Context.A = new ToggleButtonReader(g1, GamepadKeys.Button.A),
                Context.B = new ToggleButtonReader(g1, GamepadKeys.Button.B),
                Context.Y = new ToggleButtonReader(g1, GamepadKeys.Button.Y),
        };


        initializeHub(opMode.hardwareMap);
        tel = new MultipleTelemetry(telemetry, FtcDashboard.getInstance().getTelemetry());

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
            return "x: " + pose.position.x + ", y: " + pose.position.y + ", heading: " + Math.toRadians(pose.heading.real);
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
        return controlHub.getCurrent(CurrentUnit.AMPS) + expansionHub.getCurrent(CurrentUnit.AMPS);
    }
    public double getServoBusCurrent(LynxModule bus) {
        return bus.getGpioBusCurrent(CurrentUnit.AMPS);
    }


}
