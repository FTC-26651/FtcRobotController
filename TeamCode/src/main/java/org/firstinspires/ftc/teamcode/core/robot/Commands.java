package org.firstinspires.ftc.teamcode.core.robot;

import org.firstinspires.ftc.teamcode.core.robot.flywheels.SingleFlywheel;
import org.firstinspires.ftc.teamcode.core.robot.intakes.MotorIntake;

import java.util.HashMap;
import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;

public class Commands {
    private Map<String, Command> commands = new HashMap<>(Map.of(
            "throw", SingleFlywheel.INSTANCE.on,
            "turn on intake", MotorIntake.INSTANCE.forward,
            "wait 5 seconds", new Delay(5),
            "turn off intake", MotorIntake.INSTANCE.off
    ));

    public Map<String, Command> getCommands() {
        return commands;
    }

    public void addCommands(Map<String, Command> newCommands) {
        // The putAll command keeps the values in the original map
        // We want to be able to overwrite, thus this
        // newCommands.putAll(commands);
        commands.putAll(newCommands);
    }
}
