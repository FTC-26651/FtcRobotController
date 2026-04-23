package org.firstinspires.ftc.teamcode.core.interpreter;

import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.ftc.ActiveOpMode;


public class CommandBuilder {
    public static Command buildCommand(Map<String, Object> entry) {
        String name = ((String) entry.get("name")).toLowerCase();

        ActiveOpMode.telemetry().addData("Parsing command", name);
        ActiveOpMode.telemetry().update();

        return CommandRegistry.INSTANCE.create(name, entry);
    }
}