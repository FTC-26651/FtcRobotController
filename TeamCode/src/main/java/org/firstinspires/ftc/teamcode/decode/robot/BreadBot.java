package org.firstinspires.ftc.teamcode.decode.robot;


import com.acmerobotics.roadrunner.Pose2d;

import org.firstinspires.ftc.teamcode.core.robot.drivetrain.MecanumDrive;
import org.firstinspires.ftc.teamcode.core.robot.flywheels.SingleFlywheel;
import org.firstinspires.ftc.teamcode.core.robot.intakes.ServoIntake;
import org.firstinspires.ftc.teamcode.core.robot.transfers.wheels.SingleMotorTransfer;

import dev.nextftc.core.subsystems.SubsystemGroup;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.ftc.Gamepads;

public class BreadBot extends SubsystemGroup {
    public static final BreadBot INSTANCE = new BreadBot();

    private final Pose2d initialPose = new Pose2d(9.0, 111.0, Math.toRadians(-90.0));

    public MecanumDrive drive;

    public BreadBot() {
        super(
                ServoIntake.INSTANCE,
                SingleMotorTransfer.INSTANCE,
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

        drive = new MecanumDrive(ActiveOpMode.hardwareMap(), initialPose);
    }
}
