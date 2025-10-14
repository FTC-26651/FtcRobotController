package org.firstinspires.ftc.teamcode.decode.robot;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class flywheel implements Subsystem {
    public static final flywheel INSTANCE = new flywheel();
    private flywheel() { }

    private final MotorEx motor = new MotorEx("flywheel_motor");

    private final ControlSystem controller = ControlSystem.builder()
            .velPid(0.005, 0, 0)
            .basicFF(0.01, 0.02, 0.03)
            .build();

    public final Command off = new RunToVelocity(controller, 0.0).requires(this).named("FlywheelOff");
    public final Command on = new RunToVelocity(controller, 500.0).requires(this).named("FlywheelOn");

    @Override
    public void periodic() {
        motor.setPower(controller.calculate(motor.getState()));
    }
}