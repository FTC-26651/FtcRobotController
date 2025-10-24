package org.firstinspires.ftc.teamcode.core.robot.intakes;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.CRServoEx;

public class ServoIntake implements Subsystem {
    public static final ServoIntake INSTANCE = new ServoIntake();
    private ServoIntake() { }

    private final CRServoEx servo = new CRServoEx("intake_motor");

    private final ControlSystem controller = ControlSystem.builder()
            .velPid(0.005, 0, 0)
            .basicFF(0.01, 0.02, 0.03)
            .build();

    public final Command off = new RunToVelocity(controller, 0.0).requires(this).named("IntakeOff");
    public final Command on = new RunToVelocity(controller, 500.0).requires(this).named("IntakeOn");

    @Override
    public void periodic() {
        servo.setPower(controller.calculate());
    }
}