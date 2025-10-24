package org.firstinspires.ftc.teamcode.core.robot.lifts;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToPosition;
import dev.nextftc.hardware.impl.MotorEx;

public class SingleLift implements Subsystem {
    public static final SingleLift INSTANCE = new SingleLift();
    private SingleLift() { }

    private final MotorEx motor = new MotorEx("lift_motor");

    private final ControlSystem controlSystem = ControlSystem.builder()
            .posPid(0.005, 0, 0)
            .elevatorFF(0)
            .build();

    public Command toLow = new RunToPosition(controlSystem, 0).requires(this);
    public Command toMiddle = new RunToPosition(controlSystem, 500).requires(this);
    public Command toHigh = new RunToPosition(controlSystem, 1200).requires(this);

    @Override
    public void periodic() {
        motor.setPower(controlSystem.calculate(motor.getState()));
    }
}
