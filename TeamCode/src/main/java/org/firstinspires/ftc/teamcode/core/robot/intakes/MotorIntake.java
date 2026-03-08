package org.firstinspires.ftc.teamcode.core.robot.intakes;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class MotorIntake implements Subsystem {
    public static final MotorIntake INSTANCE = new MotorIntake();
    private MotorIntake() { }

    double motorPower;

    private final MotorEx motor = new MotorEx("intake_motor");

//    private final ControlSystem controller = ControlSystem.builder()
//            .velPid(0.005, 0, 0)
//            .basicFF(0.01, 0.02, 0.03)
//            .build();

    public final Command off = new LambdaCommand().setUpdate(() -> motor.setPower(0));//.requires(this).named("TransferOff");
    public final Command forward = new LambdaCommand().setStart(() -> motor.setPower(-1));//.requires(this).named("TransferForward");
    public final Command back = new LambdaCommand().setUpdate(() -> motor.setPower(1));//.requires(this).named("TransferBack");

    @Override
    public void periodic() {
        // motor.setPower(motorPower);
    }
}