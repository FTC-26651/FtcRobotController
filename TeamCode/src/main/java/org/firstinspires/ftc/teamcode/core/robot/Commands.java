package org.firstinspires.ftc.teamcode.core.robot;

import org.firstinspires.ftc.teamcode.core.CommandFactory;
import org.firstinspires.ftc.teamcode.core.robot.flywheels.SingleFlywheel;
import org.firstinspires.ftc.teamcode.core.robot.intakes.MotorIntake;
import org.firstinspires.ftc.teamcode.core.robot.transfers.pushers.ServoPusher;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import dev.nextftc.core.commands.delays.Delay;

public class Commands {
    Map<String, CommandFactory> commands = new HashMap<>(Map.of(
            "wait", args -> new Delay(
                    ((Number) Objects.requireNonNull(args.get("time"))).doubleValue() * 1000
            ),

            "turn on flywheel", args -> SingleFlywheel.INSTANCE.on,
            "turn off flywheel", args -> SingleFlywheel.INSTANCE.off,

            "turn on intake", args -> MotorIntake.INSTANCE.forward,
            "turn off intake", args -> MotorIntake.INSTANCE.off,

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