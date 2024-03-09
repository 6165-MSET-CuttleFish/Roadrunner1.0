package org.firstinspires.ftc.teamcode.architecture;

import static org.firstinspires.ftc.teamcode.architecture.Context.controlHub;
import static org.firstinspires.ftc.teamcode.architecture.Context.expansionHub;
import static org.firstinspires.ftc.teamcode.architecture.Context.intake;
import static org.firstinspires.ftc.teamcode.architecture.Context.robot;
import static org.firstinspires.ftc.teamcode.architecture.Context.tel;

import org.firstinspires.ftc.teamcode.roadrunner.TwoDeadWheelLocalizer;

public class TelemetryReadout {
    public static void addTelemetry() {
        header("Pose");
        tel.addData("Pose", robot.getPose());
        tel.addData("Velocity", TwoDeadWheelLocalizer.getVelocity());

        header("Current Draw");
        tel.addData("Total Current", robot.getCurrent());
        tel.addData("Control Hub Servos", robot.getServoBusCurrent(controlHub));
        tel.addData("Expansion Hub Servos", robot.getServoBusCurrent(expansionHub));
        tel.addData("Intake", intake.getCurrent());
    }
    public static void header(String s) {
        tel.addLine("-------------------------");
        tel.addLine(s);
        tel.addLine();
    }
}
