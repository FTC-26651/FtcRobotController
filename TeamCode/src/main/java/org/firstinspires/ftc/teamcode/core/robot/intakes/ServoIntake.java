package org.firstinspires.ftc.teamcode.core.robot.intakes;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
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

    public final Command off = new LambdaCommand().setUpdate(() -> servo.setPower(0)).requires(this).named("IntakeOn");
    public final Command on = new LambdaCommand().setUpdate(() -> servo.setPower(1)).requires(this).named("IntakeOn");

    @Override
    public void periodic() {
//        servo.setPower(controller.calculate());
    }
}