package org.firstinspires.ftc.teamcode.core.robot.flywheels;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.control.KineticState;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class DoubleFlywheel implements Subsystem {
    public static final DoubleFlywheel INSTANCE = new DoubleFlywheel();
    private DoubleFlywheel() { }

    private ControlSystem controller;
    private MotorGroup motors;

    public final Command off = new RunToVelocity(controller, 0.0).requires(this).named("Flywheel Off");
    public final Command on = new RunToVelocity(controller, 500.0).requires(this).named("Flywheel On");

    @Override
    public void initialize() {
        motors = new MotorGroup(
                new MotorEx("right_flywheel_motor"),
                new MotorEx("left_flywheel_motor")
        );
        controller = ControlSystem.builder()
                .velPid(0.001, 0, 0)
                .basicFF(0.003, 0.08, 0.00)
                .build();
        controller.setGoal(new KineticState(0.0, 0.0));
    }

    @Override
    public void periodic() {
        motors.setPower(controller.calculate(new KineticState(
                motors.getCurrentPosition(),
                motors.getVelocity()))
        );
    }
}