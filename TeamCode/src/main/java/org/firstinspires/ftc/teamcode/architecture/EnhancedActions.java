package org.firstinspires.ftc.teamcode.architecture;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.architecture.Context;

import java.util.List;

public class EnhancedActions {
    public static class awaitX implements Action {
        private final double position;
        private final double threshold;
        private final List<Action> f;
        public awaitX(double xPos, double threshold, List<Action> action) {
            this.position = xPos;
            this.threshold = threshold;
            this.f = action;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket p) {
            Context.robot.updatePoseEstimate();
            boolean reached = Math.abs(Context.robot.pose.position.x - position) <= threshold;
          // boolean reached = Context.robot.returnIfReached(1000); // just to test since no actual robot
            if (reached /* || on action end */) {
                for (Action action: f) action.run(p);
                return false;
            }
            return true;
        }
    }
    public static class awaitY implements Action {
        private final double position;
        private final double threshold;
        private final List<Action> f;
        public awaitY(double xPos, double threshold, List<Action> action) {
            this.position = xPos;
            this.threshold = threshold;
            this.f = action;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket p) {
            Context.robot.updatePoseEstimate();
            boolean reached = Math.abs(Context.robot.pose.position.y - position) <= threshold;
            if (reached /* || on action end */) {
                for (Action action: f)
                    action.run(p);
                return false;
            }
            return true;
        }
    }
    public static class awaitTime implements Action {
        private boolean startTimer = false;
        private final ElapsedTime timer = new ElapsedTime();
        private final double seconds;
        private final List<Action> f;
        public awaitTime(double seconds, List<Action> action) {
            this.seconds = seconds;
            this.f = action;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket p) {
            if (!startTimer) {
                timer.reset();
                startTimer = true;
            }
            if (timer.seconds() > seconds) {
                for (Action action: f) action.run(p);
                return false;
            }
            return true;
        }
    }


}