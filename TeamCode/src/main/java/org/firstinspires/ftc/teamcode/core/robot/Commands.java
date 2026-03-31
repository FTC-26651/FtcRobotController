package org.firstinspires.ftc.teamcode.core.robot;

import org.firstinspires.ftc.teamcode.core.CommandBuilder;
import org.firstinspires.ftc.teamcode.core.CommandFactory;
import org.firstinspires.ftc.teamcode.core.robot.flywheels.SingleFlywheel;
import org.firstinspires.ftc.teamcode.core.robot.intakes.MotorIntake;
import org.firstinspires.ftc.teamcode.core.robot.transfers.pushers.ServoPusher;
import org.firstinspires.ftc.teamcode.decode.robot.subsystems.Launcher;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.ParallelRaceGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.NullCommand;

public class Commands {
    /*
     * This contains all of the universal commands, i.e. commands that will not change from year to year.
     * First off, we have the wait command. This takes in one parameter, duration, in milliseconds.
     * It waits for the received duration to end when called.
     *
     * The other commands are command groups. I go over each briefly before implementing them, but
     * I shall also link the docs here: https://nextftc.dev/nextftc/commands/groups.
     */
    private static final Map<String, CommandFactory> commands = new HashMap<>(Map.of(
            "wait", args ->
                    new Delay(((Number) args.get("duration")).doubleValue() / 1000
            ),

            /*
             * A sequential group is probably the easiest to wrap your head around. It takes in an
             * arbitrary amount of commands as parameters, and runs them one after another, or sequentially
             */
            "sequential group", args -> {
                List<Map<String, Object>> children = (List<Map<String, Object>>) args.get("commands");
                if (children == null || children.isEmpty()) return new NullCommand();

                return new SequentialGroup(
                        children.stream()
                                .map(CommandBuilder::buildCommand)
                                .toArray(Command[]::new)
                );
            },

            /*
             * A parallel group is similar to a sequential group. It differs in how it runs the commands.
             * A sequential group runs each command one after another. A parallel group runs each defined
             * command at the same time.
             */
            "parallel group", args -> {
                List<Map<String, Object>> children = (List<Map<String, Object>>) args.get("commands");
                if (children == null || children.isEmpty()) return new NullCommand();

                return new ParallelGroup(
                    children.stream()
                        .map(CommandBuilder::buildCommand)
                        .toArray(Command[]::new)
                );
            },

            /*
             * A race group is where it gets interesting. This is similar to a parallel group. However,
             * what sets it apart is when it finishes. The parallel group requires all of it's children
             * to finish before it does. A race group only requires one command to finish before it does.
             */
            "race group", args -> {
                List<Map<String, Object>> children = (List<Map<String, Object>>) args.get("commands");
                if (children == null || children.isEmpty()) return new NullCommand();

                return new ParallelRaceGroup(
                        children.stream()
                                .map(CommandBuilder::buildCommand)
                                .toArray(Command[]::new)
                );
            },

            /*
             * A deadline group is similar to a race group. However, it has one command (the "deadline command")
             * that controls when it finishes. The deadline group is only looking at that one command
             * to finish before it does.
             */
            "deadline group", args -> {
                Map<String, Object> deadlineMap = (Map<String, Object>) args.get("deadline");
                List<Map<String, Object>> children = (List<Map<String, Object>>) args.get("commands");

                // Check to see if the deadline command is null. If it is, we replace it with a null command
                Command deadline = (deadlineMap == null)
                        ? new NullCommand()
                        : CommandBuilder.buildCommand(deadlineMap);

                // Check to see if the children exist. If not, just give an empty table.
                Command[] others = (children == null)
                        ? new Command[0]
                        : children.stream()
                        .map(CommandBuilder::buildCommand)
                        .toArray(Command[]::new);

                return new ParallelDeadlineGroup(deadline, others);
            }
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