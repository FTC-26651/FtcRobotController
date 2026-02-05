package org.firstinspires.ftc.teamcode.decode;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import org.firstinspires.ftc.teamcode.decode.robot.Aslan;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.components.BindingsComponent;
import dev.nextftc.core.components.SubsystemComponent;
import dev.nextftc.ftc.NextFTCOpMode;
import dev.nextftc.ftc.components.BulkReadComponent;

@Autonomous(name = "Aslan Move", group = "Robot")
public class AslanFarAutoBlue extends NextFTCOpMode {
    TrajectoryActionBuilder move;
    Command driveCommand;

    public AslanFarAutoBlue() {
        addComponents(
                new SubsystemComponent(Aslan.INSTANCE),
                BulkReadComponent.INSTANCE,
                BindingsComponent.INSTANCE
        );
    }

    @Override
    public void onInit() {
        driveCommand = Aslan.INSTANCE.drive.commandBuilder(new Pose2d(-72, 0, Math.toRadians(-90.0)))
                .strafeTo(new Vector2d(-52, -25))
                .build();
    }

    @Override
    public void onStartButtonPressed() {
        driveCommand.schedule();
    }

    @Override
    public void onUpdate() {
    }
}