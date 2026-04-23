package org.firstinspires.ftc.teamcode.core.robot.intakes;


import android.widget.Switch;

import org.firstinspires.ftc.teamcode.core.interpreter.CommandFactory;
import org.firstinspires.ftc.teamcode.core.interpreter.CommandRegistry;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.InstantCommand;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.hardware.impl.MotorEx;

public class MotorIntake implements Subsystem {
    public static final MotorIntake INSTANCE = new MotorIntake();
    private MotorIntake() {
        CommandRegistry.INSTANCE.addCommandObject(MotorIntake.INSTANCE);
    }

    private final MotorEx motor = new MotorEx("intake_motor");

    public final Command off = new InstantCommand(() -> motor.setPower(0)).requires(this).named("Transfer Off");
    public final Command forward = new InstantCommand(() -> motor.setPower(-1)).requires(this).named("Transfer Forward");
    public final Command reverse = new InstantCommand(() -> motor.setPower(1)).requires(this).named("Transfer Back");

    @CommandFactory("use intake")
    public Command useIntake(String action) {
        return new LambdaCommand().setStart(() -> {
            switch (action) {
                case "forward":
                    forward.schedule();
                case "reverse":
                    reverse.schedule();
                case "off":
                    off.schedule();
           }
        });
    }

    @Override
    public void initialize() {
        motor.setPower(0);
    }

    @Override
    public void periodic() {
        // motor.setPower(motorPower);
    }
}