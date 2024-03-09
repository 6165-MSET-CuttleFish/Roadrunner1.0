package org.firstinspires.ftc.teamcode.roadrunner

import com.acmerobotics.roadrunner.trajectory.MarkerCallback

/**
 * Trajectory marker that is triggered when the specified time passes.
 */
data class TrajectoryMarker(val time: Double, val callback: MarkerCallback)
