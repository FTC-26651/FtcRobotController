package org.firstinspires.ftc.teamcode.core.robot;

import org.firstinspires.ftc.teamcode.core.interpreter.CommandBuilder;
import org.firstinspires.ftc.teamcode.core.interpreter.CommandFactory;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.delays.Delay;
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.ParallelRaceGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.NullCommand;

public class CoreCommandDefinitions {
    @CommandFactory("wait")
    public Command createWait(double durationMs) {
        return new Delay(durationMs / 1000);
    }

    @CommandFactory("sequential group")
    public Command createSequentialGroup(List<Map<String, Object>> commands) {
        if (commands == null || commands.isEmpty()) {
            return new NullCommand();
        }

        return new SequentialGroup(
                commands.stream()
                        .map(CommandBuilder::buildCommand)
                        .toArray(Command[]::new)
        );
    }

    @CommandFactory("parallel group")
    public Command createParallelGroup(List<Map<String, Object>> commands) {
        if (commands == null || commands.isEmpty()) {
            return new NullCommand();
        }

        return new ParallelGroup(
                commands.stream()
                        .map(CommandBuilder::buildCommand)
                        .toArray(Command[]::new)
        );
    }

    @CommandFactory("parallel race group")
    public Command createParallelRaceGroup(List<Map<String, Object>> commands) {
        if (commands == null || commands.isEmpty()) {
            return new NullCommand();
        }

        return new ParallelRaceGroup(
                commands.stream()
                        .map(CommandBuilder::buildCommand)
                        .toArray(Command[]::new)
        );
    }

    @CommandFactory("parallel deadline group")
    public Command createParallelDeadlineGroup(List<Map<String, Object>> commands) {
        if (commands == null || commands.isEmpty()) {
            return new NullCommand();
        }

        Command[] allCommands = (commands.stream()
                .map(CommandBuilder::buildCommand)
                .toArray(Command[]::new));


        Command deadline = allCommands[0];
        Command[] others = Arrays.copyOfRange(allCommands, 1, allCommands.length);

        if (others.length > 0) {
            return new ParallelDeadlineGroup(deadline, others);
        } else {
            return new ParallelDeadlineGroup(deadline);
        }
    }
}
