package org.firstinspires.ftc.teamcode.core.robot;

import org.firstinspires.ftc.teamcode.core.CommandFactory;
import org.firstinspires.ftc.teamcode.core.robot.flywheels.SingleFlywheel;
import org.firstinspires.ftc.teamcode.core.robot.intakes.MotorIntake;
import org.firstinspires.ftc.teamcode.core.robot.transfers.pushers.ServoPusher;
import org.firstinspires.ftc.teamcode.decode.robot.subsystems.Launcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;

public class Commands {
    private static final Map<String, CommandFactory> commands = new HashMap<>(Map.of(
            "wait", args -> new Delay(
                    ((Number) Objects.requireNonNull(args.get("duration"))).doubleValue() / 1000
            )
    ));

    public static Map<String, CommandFactory> getCommands() {
        return commands;
    }

    public static void addCommands(Map<String, CommandFactory> newCommands) {
        commands.putAll(newCommands);
    }

    public static void addCommands(String name, CommandFactory factory) {
        commands.put(name, factory);
    }
}