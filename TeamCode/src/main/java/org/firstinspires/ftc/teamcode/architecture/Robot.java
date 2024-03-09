package org.firstinspires.ftc.teamcode.architecture;

import static org.firstinspires.ftc.teamcode.architecture.Context.controlHub;
import static org.firstinspires.ftc.teamcode.architecture.Context.expansionHub;
import static org.firstinspires.ftc.teamcode.architecture.Context.hubs;

import com.acmerobotics.roadrunner.Pose2d;
import com.qualcomm.hardware.lynx.LynxModule;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LynxModuleImuType;

import org.firstinspires.ftc.robotcore.external.navigation.CurrentUnit;
import org.firstinspires.ftc.teamcode.modules.Intake;
import org.firstinspires.ftc.teamcode.roadrunner.MecanumDrive;

import java.util.ArrayList;

public class Robot extends MecanumDrive {
    public Robot(HardwareMap hardwareMap, Pose2d pose) {
        super(hardwareMap, pose);
        Context.intake = new Intake(hardwareMap);
        initializeHub(hardwareMap);

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
