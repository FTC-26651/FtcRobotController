package org.firstinspires.ftc.teamcode.core.robot.transfers.pushers;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.Servo;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

public class ServoPusher implements Subsystem {
    public static final ServoPusher INSTANCE = new ServoPusher();
    private ServoPusher() { }

    private double servoPosition = 0;

    private CRServo servo = null;

    public final Command off = new LambdaCommand().setUpdate(() -> servoPosition = 0).requires(this).named("IntakeOff");
    public final Command on = new LambdaCommand().setUpdate(() -> servoPosition = 1).requires(this).named("IntakeOn");

    @Override
    public void initialize() {
        servo = ActiveOpMode.hardwareMap().get(CRServo.class, "transfer_servo");
    }

    @Override
    public void periodic() {
        servo.setPower(servoPosition);
        ActiveOpMode.telemetry().addData("Servo Power: ", servoPosition);
    }
}