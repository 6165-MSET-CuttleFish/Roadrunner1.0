package org.firstinspires.ftc.teamcode.opmodes.tuning;

import static org.firstinspires.ftc.teamcode.architecture.Context.A1;
import static org.firstinspires.ftc.teamcode.architecture.Context.B1;
import static org.firstinspires.ftc.teamcode.architecture.Context.Y1;
import static org.firstinspires.ftc.teamcode.architecture.Context.extendo;
import static org.firstinspires.ftc.teamcode.architecture.Context.poseStorage;
import static org.firstinspires.ftc.teamcode.architecture.Context.robot;

import com.acmerobotics.dashboard.config.Config;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.architecture.EnhancedOpMode;
import org.firstinspires.ftc.teamcode.architecture.Robot;
import org.firstinspires.ftc.teamcode.modules.used.Extendo;

@Config
@TeleOp(name = "Extendo Tuning", group = "Tuning")
public class extendoTuning extends EnhancedOpMode {
    public void initialize() {
        robot = new Robot(this, poseStorage);
    }
    @Override
    public void linearOpMode() {

    }

    public void primaryLoop() {
        if (A1.wasJustPressed()) {
            Actions.runBlocking(extendo.setState(Extendo.ExtendoPosition.IN));
        }
        if (B1.wasJustPressed()) {
            Actions.runBlocking(extendo.setState(Extendo.ExtendoPosition.HALF_EXTEND));
        }
        if (Y1.wasJustPressed()) {
            Actions.runBlocking(extendo.setState(Extendo.ExtendoPosition.FULL_EXTEND));
        }
        extendo.setController();
    }
}
