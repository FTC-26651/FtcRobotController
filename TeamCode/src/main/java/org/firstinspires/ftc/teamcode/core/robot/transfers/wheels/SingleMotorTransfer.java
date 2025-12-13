package org.firstinspires.ftc.teamcode.core.robot.transfers.wheels;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
import dev.nextftc.hardware.controllable.RunToVelocity;
import dev.nextftc.hardware.impl.MotorEx;

public class SingleMotorTransfer implements Subsystem {
    public static final SingleMotorTransfer INSTANCE = new SingleMotorTransfer();
    private SingleMotorTransfer() { }

    private final MotorEx motor = new MotorEx("transfer_motor");

    private double motorPower = 0;

//    private final ControlSystem controller = ControlSystem.builder()
//            .velPid(0.005, 0, 0)
//            .basicFF(0.01, 0.02, 0.03)
//            .build();

    public final Command off = new LambdaCommand().setUpdate(() -> motorPower = 0);//.requires(this).named("TransferOff");
    public final Command forward = new LambdaCommand().setUpdate(() -> motorPower = -1);//.requires(this).named("TransferForward");
    public final Command back = new LambdaCommand().setUpdate(() -> motorPower = 1);//.requires(this).named("TransferBack");

    @Override
    public void periodic() {
        ActiveOpMode.telemetry().addData("Power: ", motorPower);
        motor.setPower(motorPower);
    }
}