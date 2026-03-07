package org.firstinspires.ftc.teamcode.core.robot;

import org.firstinspires.ftc.teamcode.core.robot.flywheels.SingleFlywheel;

import java.util.Map;

import dev.nextftc.core.commands.Command;

public class Commands {
    private static Map<String, Command> commands = Map.of(
            "throw", SingleFlywheel.INSTANCE.on
    );

    public static Map<String, Command> getCommands() {
        return commands;
    }

    public static void addCommands(Map<String, Command> newCommands) {
        // The putAll command keeps the values in the original map
        // We want to be able to overwrite, thus this
        newCommands.putAll(commands);
        commands = newCommands;
    }
}
