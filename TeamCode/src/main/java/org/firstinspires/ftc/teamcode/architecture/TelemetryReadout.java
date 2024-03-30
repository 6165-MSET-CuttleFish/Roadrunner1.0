package org.firstinspires.ftc.teamcode.architecture;

import static org.firstinspires.ftc.teamcode.architecture.Context.arm;
import static org.firstinspires.ftc.teamcode.architecture.Context.extendo;
import static org.firstinspires.ftc.teamcode.architecture.Context.robot;
import static org.firstinspires.ftc.teamcode.architecture.Context.tel;

import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.roadrunner.TwoDeadWheelLocalizer;

public class TelemetryReadout {
    public static void addTelemetry(int counter, ElapsedTime timer) {
        header();

        tel.addData("Pose", robot.getPose());
        tel.addData("Velocity", TwoDeadWheelLocalizer.getVelocity());

        header();

        tel.addData("Extendo", extendo.getCurrent());
        tel.addData("Arm", arm.getCurrent());

        header();

        tel.addData("Loop Times", timer.milliseconds()/counter);

        tel.update();



    }

    public static void header() {
        tel.addLine();
        tel.addLine("-------------------------");
        tel.addLine();
    }
}
