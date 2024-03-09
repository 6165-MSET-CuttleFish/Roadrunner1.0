package org.firstinspires.ftc.teamcode.architecture;

import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.canvas.Canvas;
import com.acmerobotics.dashboard.telemetry.MultipleTelemetry;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import kotlin.Metadata;
import kotlin.jvm.internal.Intrinsics;

@Metadata(
        mv = {1, 9, 0},
        k = 2,
        xi = 48,
        d1 = {"\u0000\u000e\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\u001a\u000e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003¨\u0006\u0004"},
        d2 = {"runBlocking", "", "a", "Lcom/acmerobotics/roadrunner/Action;", "RoadRunner_release"}
)
public final class ExtendedActions {
    public static void runBlocking(@NotNull Action a, MultipleTelemetry telemetry) {
        Intrinsics.checkNotNullParameter(a, "a");
        FtcDashboard dash = FtcDashboard.getInstance();
        Canvas c = new Canvas();
        a.preview(c);
        boolean b = true;

        while(b && !Thread.currentThread().isInterrupted()) {
            Context.tel=new MultipleTelemetry(FtcDashboard.getInstance().getTelemetry(), telemetry);
            TelemetryReadout.addTelemetry();
            TelemetryPacket p = new TelemetryPacket();
            List var10000 = p.fieldOverlay().getOperations();
            List var10001 = c.getOperations();
            Intrinsics.checkNotNullExpressionValue(var10001, "getOperations(...)");
            var10000.addAll(var10001);
            b = a.run(p);
            dash.sendTelemetryPacket(p);
        }

    }
}
