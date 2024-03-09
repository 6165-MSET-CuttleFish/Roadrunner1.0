package org.firstinspires.ftc.teamcode.opmodes.tele.wolfdrive;

import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.Vector2d;

import org.firstinspires.ftc.teamcode.architecture.Context;
import static org.firstinspires.ftc.teamcode.architecture.Context.robot;
import org.firstinspires.ftc.teamcode.architecture.Robot;

public class WolfDrive {
    public static double maxVelocityX = 77; // max positive straight velocity. TODO: Record using MaxVelStraightTest.
    public static double maxVelocityY = 51; // max positive sideways velocity. TODO: Record using MaxVelStrafeTest.
    public static double centripetalWeighting = 0.001; // TODO: adjust by trial and error for how much smoothing you need. Wolfpack calculates it but I can't be bothered.
    public static double dashboardVectorScale = 1;

    private Vector2d leftFrontWheelForceVector;  // these vectors should not change during the match,
    private Vector2d leftBackWheelForceVector;   // but are left non-final for modifying maxVelocities
    private Vector2d rightBackWheelForceVector;
    private Vector2d rightFrontWheelForceVector;
    private static double smallestWheelForceMagnitude; // used to scale all force vectors down to what the weakest wheel force can output at full power

    public Vector2d centripetalCircleCenterDrawn = null;
    public Vector2d centripetalCircleRadiusDrawn = null;
    public Vector2d centripetalVectorDrawn = null;
    public Vector2d robotDriveDirectionDrawn = null;
    private final RingBuffer positionBuffer = new RingBuffer(3);
    public WolfDrive(Robot robot) {
        Context.robot = robot;
        updateWheelForceVectors();
    }

    /**
     * Flashes new maxVelocities into the force vectors.
     */
    public void updateWheelForceVectors() {
        leftFrontWheelForceVector = new Vector2d(maxVelocityX, -maxVelocityY);
        leftBackWheelForceVector = new Vector2d(maxVelocityX, maxVelocityY);
        rightBackWheelForceVector = new Vector2d(maxVelocityX, -maxVelocityY);
        rightFrontWheelForceVector = new Vector2d(maxVelocityX, maxVelocityY);
        smallestWheelForceMagnitude = getAbsMin(leftBackWheelForceVector.norm(), leftFrontWheelForceVector.norm(), rightBackWheelForceVector.norm(), rightFrontWheelForceVector.norm());
    }

    /**
     * Sets wheel powers depending on given robot drive direction
     */
    public void setDrivePowers(PoseVelocity2d powers) {
        // Calculate starting power for forwards, using linear velocity and scaling down to weakest wheel
        double drivePower = powers.linearVel.norm();
        double leftFrontPower = drivePower * smallestWheelForceMagnitude / leftFrontWheelForceVector.norm();
        double leftBackPower = drivePower * smallestWheelForceMagnitude / leftBackWheelForceVector.norm();
        double rightBackPower = drivePower * smallestWheelForceMagnitude / rightBackWheelForceVector.norm();
        double rightFrontPower = drivePower * smallestWheelForceMagnitude / rightFrontWheelForceVector.norm();
        // Rotate wheel force vectors and scale with drive power
        double driveDirection = powers.linearVel.angleCast().log();
        Vector2d newLeftFrontWheelForceVector = leftFrontWheelForceVector.angleCast().plus(-driveDirection).vec().times(leftFrontPower);
        Vector2d newLeftBackWheelForceVector = leftBackWheelForceVector.angleCast().plus(-driveDirection).vec().times(leftBackPower);
        Vector2d newRightBackWheelForceVector = rightBackWheelForceVector.angleCast().plus(-driveDirection).vec().times(rightBackPower);
        Vector2d newRightFrontWheelForceVector = rightFrontWheelForceVector.angleCast().plus(-driveDirection).vec().times(rightFrontPower);

        System.out.println(String.format("(%+7.2f, %7.2f)  (%+7.2f, %7.2f)", newLeftFrontWheelForceVector.x, newLeftFrontWheelForceVector.y, newRightFrontWheelForceVector.x, newRightFrontWheelForceVector.y));
        System.out.println(String.format("(%+7.2f, %7.2f)  (%+7.2f, %7.2f)", newLeftBackWheelForceVector.x, newLeftBackWheelForceVector.y, newRightBackWheelForceVector.x, newRightBackWheelForceVector.y));

        // Find vectors with the most X value
        if (Math.abs(newLeftFrontWheelForceVector.x) >= Math.abs(newRightFrontWheelForceVector.x)) { // leftFront and rightBack contribute most to drive direction
            // Reverse primary vectors if necessary
            if (newLeftFrontWheelForceVector.x < 0) {
                newLeftFrontWheelForceVector = newLeftFrontWheelForceVector.times(-1);
                leftFrontPower *= -1;
                newRightBackWheelForceVector = newRightBackWheelForceVector.times(-1);
                rightBackPower *= -1;
            }

            double extraneousY = newLeftFrontWheelForceVector.y;
            if (newRightFrontWheelForceVector.y != 0 && newLeftBackWheelForceVector.y != extraneousY) { // Make secondary vector y's cancel out primary's
                double rightFrontFactor = -extraneousY / newRightFrontWheelForceVector.y;
                newRightFrontWheelForceVector = newRightFrontWheelForceVector.times(rightFrontFactor);
                rightFrontPower *= rightFrontFactor;
                newLeftBackWheelForceVector = newLeftBackWheelForceVector.times(rightFrontFactor);
                leftBackPower *= rightFrontFactor;
            } else if (newLeftBackWheelForceVector.x < 0) { // Reverse secondary vectors if necessary
                newLeftBackWheelForceVector = newLeftBackWheelForceVector.times(-1);
                leftBackPower *= -1;
                newRightFrontWheelForceVector = newRightFrontWheelForceVector.times(-1);
                rightFrontPower *= -1;
            }
        } else { // rightFront and leftBack contribute most to drive direction
            // Reverse primary vectors if necessary
            if (newRightFrontWheelForceVector.x < 0) {
                newRightFrontWheelForceVector = newRightFrontWheelForceVector.times(-1);
                rightFrontPower *= -1;
                newLeftBackWheelForceVector = newLeftBackWheelForceVector.times(-1);
                leftBackPower *= -1;
            }

            double extraneousY = newRightFrontWheelForceVector.y;
            if (newLeftFrontWheelForceVector.y != 0 && newRightBackWheelForceVector.y != extraneousY) { // Make secondary vector y's cancel out primary's
                double leftFrontFactor = -extraneousY / newLeftFrontWheelForceVector.y;
                newLeftFrontWheelForceVector = newLeftFrontWheelForceVector.times(leftFrontFactor);
                leftFrontPower *= leftFrontFactor;
                newRightBackWheelForceVector = newRightBackWheelForceVector.times(leftFrontFactor);
                rightBackPower *= leftFrontFactor;
            } else if (newRightBackWheelForceVector.x < 0) { // Reverse secondary vectors if necessary
                newRightBackWheelForceVector = newRightBackWheelForceVector.times(-1);
                rightBackPower *= -1;
                newLeftFrontWheelForceVector = newLeftFrontWheelForceVector.times(-1);
                leftFrontPower *= -1;
            }
        }

        // consider turn power
        leftFrontPower = leftFrontPower - powers.angVel;
        leftBackPower = leftBackPower - powers.angVel;
        rightBackPower = rightBackPower + powers.angVel;
        rightFrontPower = rightFrontPower + powers.angVel;
        double maxPower = getAbsMax(leftFrontPower, leftBackPower, rightBackPower, rightFrontPower);
        if (maxPower > 1) {
            leftFrontPower = leftFrontPower / maxPower;
            leftBackPower = leftBackPower / maxPower;
            rightBackPower = rightBackPower / maxPower;
            rightFrontPower = rightFrontPower / maxPower;
        }

        // set motor powers
        robot.leftFront.setPower(leftFrontPower);
        robot.leftBack.setPower(leftBackPower);
        robot.rightBack.setPower(rightBackPower);
        robot.rightFront.setPower(rightFrontPower);
    }

    /**
     * Drives with additional centripetal correction
     */
    public void driveWithCorrection(PoseVelocity2d powers, PoseVelocity2d currentVelocity) {
        // Find centripetal power
        Vector2d centripetalPower = calculateCentripetalPower(currentVelocity);

        // Combine with joystick power
        Vector2d combinedPower = combinePower(powers.linearVel, centripetalPower);

        robotDriveDirectionDrawn = combinedPower.times(dashboardVectorScale);

        setDrivePowers(new PoseVelocity2d(combinedPower, powers.angVel));
    }

    /**
     * Adds two power vectors together, ensuring that the result is not of bigger magnitude than the mainPower
     */
    private Vector2d combinePower(Vector2d mainPower, Vector2d correctivePower) {
        // get max power
        double maxPower = mainPower.norm();

        // Sum
        Vector2d combinedPower = mainPower.plus(correctivePower);

        // Scale down
        Vector2d scaledCombinedPower = combinedPower;
        if (combinedPower.norm() > maxPower) { // scale down if over max. Never scale up
            scaledCombinedPower = combinedPower.div(combinedPower.norm()).times(maxPower);
        }

        return scaledCombinedPower;
    }

    /**
     * Adds a new position into ring buffer for centripetal calculation
     */
    public void trackPosition(Pose2d pose) {
        positionBuffer.put(pose);
    }

    /**
     * Calculates the centripetal vector by fitting 3 past positions and considering current linear velocity
     */
    private Vector2d calculateCentripetalPower(PoseVelocity2d currentVelocity) {
        // Read the three points from ring buffer
        if (!positionBuffer.isFull()) return new Vector2d(0, 0);
        Vector2d p3 = positionBuffer.read(0).position; // y(t=3)
        Vector2d p2 = positionBuffer.read(1).position; // y(t=2)
        Vector2d p1 = positionBuffer.read(2).position; // y(t=1)

        // Find circumcenter
        double ax = p1.x;
        double ay = p1.y;
        double bx = p2.x;
        double by = p2.y;
        double cx = p3.x;
        double cy = p3.y;
        double d = 2 * (ax * (by - cy) + bx * (cy - ay) + cx * (ay - by));
        double ux = ((ax * ax + ay * ay) * (by - cy) + (bx * bx + by * by) * (cy - ay) + (cx * cx + cy * cy) * (ay - by)) / d;
        double uy = ((ax * ax + ay * ay) * (cx - bx) + (bx * bx + by * by) * (ax - cx) + (cx * cx + cy * cy) * (bx - ax)) / d;
        Vector2d circumcenter = new Vector2d(ux, uy);

        if (Double.isFinite(circumcenter.x) && Double.isFinite(circumcenter.y)) {
            centripetalCircleCenterDrawn = circumcenter.times(1);

            Vector2d circleVector = circumcenter.minus(p3); // represents vector from p3 to the center of the circle
            centripetalCircleRadiusDrawn = circleVector.times(1);

            Vector2d centripetalVector = circleVector.div(circleVector.sqrNorm()); // scales circleVector with mag r to centripetalVector with mag 1/r

            // Calculate power
            double power = centripetalWeighting * centripetalVector.norm() * currentVelocity.linearVel.sqrNorm(); // F=mv^2/r, power = weight*F

            Vector2d centripetalPower = centripetalVector.div(centripetalVector.norm()).times(power); // change magnitude to power
            centripetalVectorDrawn = centripetalPower.times(dashboardVectorScale);

            return centripetalPower;
        } else {
            centripetalCircleCenterDrawn = null;
            centripetalCircleRadiusDrawn = null;
            centripetalVectorDrawn = null;
            return new Vector2d(0, 0);
        }
    }

    /**
     * Returns the largest magnitude of the given values
     */
    private double getAbsMax(double... values) {
        double max = 0;
        for (double value : values) {
            max = Math.max(max, Math.abs(value));
        }
        return max;
    }

    /**
     * Returns the smallest magnitude of the given values
     */
    private static double getAbsMin(double... values) {
        double min = Double.MAX_VALUE;
        for (double value : values) {
            min = Math.min(min, Math.abs(value));
        }
        return min;
    }

    public String formatVector(Vector2d vector) {
        if (vector == null) return "";
        return String.format("mag=%5.1f, angle=%5.1f°, (%5.2f, %5.2f)", vector.norm(), Math.toDegrees(vector.angleCast().log()), vector.x, vector.y);
    }

    public String getCircleString() {
        if (centripetalCircleCenterDrawn == null) {
            return "No circle";
        } else {
            return String.format("r=%.2f, center=(%.2f, %.2f)", centripetalCircleRadiusDrawn.norm(), centripetalCircleCenterDrawn.x, centripetalCircleCenterDrawn.y);
        }
    }

    public String getCorrectionString() {
        if (centripetalVectorDrawn == null) {
            return "None";
        } else {
            return formatVector(centripetalVectorDrawn);
        }
    }

    public String getDriveDirectionString() {
        if (robotDriveDirectionDrawn == null) {
            return "Stopped";
        } else {
            return formatVector(robotDriveDirectionDrawn);
        }
    }

    public String getWheelPowerString() {
        return String.format("%+1.3f  %+1.3f\n%+1.3f  %+1.3f", robot.leftFront.getPower(), robot.rightFront.getPower(), robot.leftBack.getPower(), robot.rightBack.getPower());
    }


}