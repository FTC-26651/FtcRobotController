package org.firstinspires.ftc.teamcode.core;

import org.firstinspires.ftc.teamcode.core.robot.Commands;

import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.utility.NullCommand;
import dev.nextftc.ftc.ActiveOpMode;

/*
 * This class takes in a string and object and builds a command off of that. If the command is not registered
 * it will add a null command and output to telemetry that it was unable to parse that command.
 */
public class CommandBuilder {
    public static Command buildCommand(Map<String, Object> entry) {
        String name = ((String) entry.get("name")).toLowerCase();
        ActiveOpMode.telemetry().addData("Parsing command", name);
        ActiveOpMode.telemetry().update();

        CommandFactory factory = Commands.getCommands().get(name);

        if (factory == null) {
            ActiveOpMode.telemetry().addLine("Unknown Command: " + name + ". Adding null command");
            ActiveOpMode.telemetry().update();
            return new NullCommand();
        }

        return factory.create(entry);
    }
}
