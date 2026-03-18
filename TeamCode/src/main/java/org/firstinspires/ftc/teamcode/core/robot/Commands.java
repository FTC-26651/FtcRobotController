package org.firstinspires.ftc.teamcode.core.robot;

import org.firstinspires.ftc.teamcode.core.CommandFactory;
import org.firstinspires.ftc.teamcode.core.robot.flywheels.SingleFlywheel;
import org.firstinspires.ftc.teamcode.core.robot.intakes.MotorIntake;
import org.firstinspires.ftc.teamcode.core.robot.transfers.pushers.ServoPusher;
import org.firstinspires.ftc.teamcode.decode.robot.subsystems.Launcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import dev.nextftc.core.commands.delays.Delay;

public class Commands {
    private final Map<String, CommandFactory> commands = new HashMap<>(Map.of(
            "wait", args -> new Delay(
                    ((Number) Objects.requireNonNull(args.get("duration"))).doubleValue() / 1000
            ),

            "turn on flywheel", args -> SingleFlywheel.INSTANCE.on,
            "turn off flywheel", args -> SingleFlywheel.INSTANCE.off,

            "intake", args -> MotorIntake.INSTANCE.useIntake(
                        ((String) Objects.requireNonNull(args.get("action"))
            )),

            "launcher", args -> Launcher.INSTANCE.useLauncher(
                    ((String) Objects.requireNonNull(args.get("action"))
            )),

            "turn on transfer", args -> ServoPusher.INSTANCE.on,
            "turn off transfer", args -> ServoPusher.INSTANCE.off
    ));

    public Map<String, CommandFactory> getCommands() {
        return commands;
    }

    public void addCommands(Map<String, CommandFactory> newCommands) {
        commands.putAll(newCommands);
    }
}