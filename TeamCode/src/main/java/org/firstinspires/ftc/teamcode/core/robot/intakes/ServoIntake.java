package org.firstinspires.ftc.teamcode.core.robot.intakes;

import com.qualcomm.robotcore.hardware.CRServo;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;

public class ServoIntake implements Subsystem {
    public static final ServoIntake INSTANCE = new ServoIntake();
    private ServoIntake() { }

    private double servoPower = 0;

    private CRServo servo = null;

    public final Command off = new LambdaCommand().setUpdate(() -> servoPower = 0).requires(this).named("IntakeOff");
    public final Command on = new LambdaCommand().setUpdate(() -> servoPower = -1).requires(this).named("IntakeOn");

    @Override
    public void initialize() {
        servo = ActiveOpMode.hardwareMap().get(CRServo.class, "intake_motor");
    }

    @Override
    public void periodic() {
        servo.setPower(servoPower);
    }
}