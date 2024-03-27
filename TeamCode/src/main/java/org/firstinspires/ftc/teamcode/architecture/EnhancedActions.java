package org.firstinspires.ftc.teamcode.architecture;

import static org.firstinspires.ftc.teamcode.architecture.Context.robot;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

import java.util.List;

public class EnhancedActions {
    public static int thresh = 1;
    public static class awaitX implements Action {
        private final double position;
        private final List<Action> f;
        public awaitX(double xPos, List<Action> action) {
            this.position = xPos;
            this.f = action;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket p) {
            robot.updatePoseEstimate();
            boolean reached = Math.abs(robot.pose.position.x - position) <= thresh;
            boolean failsafeVel = (robot.leftBack.getVelocity() + robot.leftFront.getVelocity() + robot.rightBack.getVelocity() + robot.rightFront.getVelocity()) < 100;
          // boolean reached = Context.robot.returnIfReached(1000); // just to test since no actual robot
            if (reached || failsafeVel) {
                for (Action action: f) action.run(p);
                return false;
            }
            return true;
        }
    }
    public static class awaitY implements Action {
        private final double position;
        private final List<Action> f;
        public awaitY(double xPos, List<Action> action) {
            this.position = xPos;
            this.f = action;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket p) {
            robot.updatePoseEstimate();
            boolean reached = Math.abs(robot.pose.position.y - position) <= thresh;
            boolean failsafeVel = (robot.leftBack.getVelocity() + robot.leftFront.getVelocity() + robot.rightBack.getVelocity() + robot.rightFront.getVelocity()) < 100;
            // boolean reached = Context.robot.returnIfReached(1000); // just to test since no actual robot
            if (reached || failsafeVel) {
                for (Action action: f) action.run(p);
                return false;
            }
            return true;
        }
    }
    public static class awaitCondition implements Action {
        private final boolean condition;
        private final List<Action> f;
        public awaitCondition(boolean condition, List<Action> action) {
            this.condition = condition;
            this.f = action;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket p) {
            robot.updatePoseEstimate();
            if (condition) {
                for (Action action: f) action.run(p);
                return false;
            }
            return true;
        }
    }
    public static class stopOnCondition implements Action {
        private final boolean condition;
        public stopOnCondition(boolean condition) {
            this.condition = condition;
        }
        @Override
        public boolean run(@NonNull TelemetryPacket p) {
            robot.updatePoseEstimate();
            if (condition) {
                robot.setDrivePowers(new PoseVelocity2d(new Vector2d(0, 0), 0));
                return false;
            }
            return true;
        }
    } // for sensors or smthn
}


