package org.firstinspires.ftc.teamcode.decode.robot;


import com.acmerobotics.dashboard.FtcDashboard;
import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.teamcode.core.robot.drivetrain.MecanumDrive;
import org.firstinspires.ftc.teamcode.core.robot.flywheels.SingleFlywheel;
import org.firstinspires.ftc.teamcode.core.robot.intakes.MotorIntake;
import org.firstinspires.ftc.teamcode.core.robot.transfers.pushers.ServoPusher;

import dev.nextftc.core.subsystems.SubsystemGroup;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;

public class Aslan extends SubsystemGroup {
    public static final Aslan INSTANCE = new Aslan();

    private Pose2d initialPose = new Pose2d(-72, 0, Math.toRadians(-90.0));

    FtcDashboard dashboard = FtcDashboard.getInstance();
    TelemetryPacket packet = new TelemetryPacket();

    public MecanumDrive drive;

    public Aslan() {
        super(
                MotorIntake.INSTANCE,
                ServoPusher.INSTANCE,
                SingleFlywheel.INSTANCE
        );
    }

    @Override
    public void initialize() {
        super.initialize();

        Gamepads.gamepad1().leftStickX();
        Gamepads.gamepad1().leftStickY();
        Gamepads.gamepad1().rightStickX();
        Gamepads.gamepad1().rightStickY();

        Gamepads.gamepad1().b();

        packet.fieldOverlay()
                .setFill("yellow")
                .fillRect(initialPose.position.x, initialPose.position.y, 1, 1);

        dashboard.sendTelemetryPacket(packet);

        drive = new MecanumDrive(ActiveOpMode.hardwareMap(), initialPose);
    }

    public void initialize(Pose2d pose) {
        initialPose = pose;
        initialize();
    }
}
