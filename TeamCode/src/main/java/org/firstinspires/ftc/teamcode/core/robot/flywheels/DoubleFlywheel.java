package org.firstinspires.ftc.teamcode.core.robot.flywheels;

import com.qualcomm.robotcore.util.Range;

import dev.nextftc.control2.feedback.PIDController;
import dev.nextftc.control2.feedforward.SimpleFeedforward;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.MotorGroup;
import dev.nextftc.hardware.impl.MotorEx;

public class DoubleFlywheel implements Subsystem {
    public static final DoubleFlywheel INSTANCE = new DoubleFlywheel();
    private DoubleFlywheel() { }

    private MotorGroup motors;

    private PIDController pidController;
    private SimpleFeedforward feedforward;

    private double targetVelocity = 0;

    public final Command off = new LambdaCommand()
            .setUpdate(() -> targetVelocity = 0)
            .requires(this)
            .named("Flywheel Off");
    public final Command on = new LambdaCommand()
            .setUpdate(() -> targetVelocity = 500)
            .requires(this)
            .named("Flywheel On");

    @Override
    public void initialize() {
        motors = new MotorGroup(
                new MotorEx("right_flywheel_motor"),
                new MotorEx("left_flywheel_motor")
        );
        pidController = new PIDController(0.001, 0, 0);
        feedforward = new SimpleFeedforward(0.003, 0.08, 0.00);
    }

    @Override
    public void periodic() {
        motors.setPower(Range.clip(
                pidController.calculateFromReference(targetVelocity, motors.getVelocity()) +
                feedforward.calculate(targetVelocity),
                0, 1
            )
        );
    }
}