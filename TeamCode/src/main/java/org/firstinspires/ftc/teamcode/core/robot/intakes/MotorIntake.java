package org.firstinspires.ftc.teamcode.core.robot.intakes;

import com.qualcomm.robotcore.hardware.CRServo;

import org.firstinspires.ftc.teamcode.core.robot.Commands;

import java.util.Objects;

import dev.nextftc.control.ControlSystem;
import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.LambdaCommand;
import dev.nextftc.core.subsystems.Subsystem;
import dev.nextftc.ftc.ActiveOpMode;
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

    public final Command off = new LambdaCommand().setUpdate(() -> motor.setPower(0)).requires(this).named("Transfer Off");
    public final Command forward = new LambdaCommand().setStart(() -> motor.setPower(-1)).requires(this).named("Transfer Forward");
    public final Command reverse = new LambdaCommand().setUpdate(() -> motor.setPower(1)).requires(this).named("Transfer Back");

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

        Commands.addCommands(
            "intake", args -> useIntake(
                        ((String) Objects.requireNonNull(args.get("action"))
            ))
        );
    }

    @Override
    public void periodic() {
        // motor.setPower(motorPower);
    }
}