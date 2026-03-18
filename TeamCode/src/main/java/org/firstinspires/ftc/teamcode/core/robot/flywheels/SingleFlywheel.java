package org.firstinspires.ftc.teamcode.core.robot.flywheels;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class SingleFlywheel implements Subsystem {
    public static final SingleFlywheel INSTANCE = new SingleFlywheel();
    private SingleFlywheel() { }

    private final MotorEx motor = new MotorEx("flywheel_motor");

//    private final ControlSystem controller = ControlSystem.builder()
//            .velPid(0.005, 0, 0)
//            .basicFF(0.01, 0.02, 0.03)
//            .build();

    public final Command off = new LambdaCommand().setUpdate(() -> motor.setPower(0)).requires(this).named("FlywheelOff");
    public final Command on = new LambdaCommand().setUpdate(() -> motor.setPower(0.60)).requires(this).named("FlywheelOn"); // No longer nice D:
    public final Command onHigh = new LambdaCommand().setUpdate(() -> motor.setPower(1)).requires(this).named("FlywheelOnHigh");

    @Override
    public void initialize() {
        motor.setPower(0);
    }

    @Override
    public void periodic() {
    }
}