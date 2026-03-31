package org.firstinspires.ftc.teamcode.core;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.teamcode.core.robot.Commands;
import org.firstinspires.ftc.teamcode.core.robot.PathParser;
import org.firstinspires.ftc.teamcode.core.robot.Robot;
import org.firstinspires.ftc.teamcode.core.robot.drivetrain.Constants;
import org.firstinspires.ftc.teamcode.decode.robot.Aslan;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.nextftc.core.commands.Command;
import dev.nextftc.core.commands.groups.ParallelDeadlineGroup;
import dev.nextftc.core.commands.groups.ParallelGroup;
import dev.nextftc.core.commands.groups.ParallelRaceGroup;
import dev.nextftc.core.commands.groups.SequentialGroup;
import dev.nextftc.core.commands.utility.NullCommand;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;


@Autonomous(name = "Lions Autonomous", group = "Autonomous")
public class LionsOpMode extends NextFTCOpMode {
    {
        addComponents(
                new PedroComponent(Constants::createFollower)
        );
    }

    private Robot robot;
    private final ElapsedTime timer = new ElapsedTime();

    private final String autoFilePath = "test.yaml";

    private Command allCommands;
    Map<String, CommandFactory> commandFactories;

    boolean isDone = false;

    @Override
    public void onInit() {
        Yaml yaml = new Yaml();
        Map<String, Object> data;

        try {
            data = yaml.load(hardwareMap.appContext.getAssets().open(autoFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        String pathsFilePath = (String) data.get("paths file");
        PathParser.setFilePath(pathsFilePath);

        robot = Aslan.INSTANCE;
        robot.initialize();

        List<Map<String, Object>> commandList = (List<Map<String, Object>>) data.get("commands");
        commandFactories = Commands.getCommands();

        // ---------------------------------------------------------
        // BUILD ALL COMMANDS
        // ---------------------------------------------------------
        ArrayList<Command> builtCommands = new ArrayList<>();

        if (commandList == null) {
            telemetry.addLine("No commands found");
            telemetry.update();
        } else {
            for (Map<String, Object> entry : commandList) {
                Command cmd = buildCommand(entry);
                builtCommands.add(cmd);
            }
        }

        allCommands = new SequentialGroup(
                builtCommands.toArray(new Command[0])
        );
    }

    private Command buildCommand(Map<String, Object> entry) {
        String name = ((String) entry.get("name")).toLowerCase();
        telemetry.addData("Parsing command", name);
        telemetry.update();

        /*
         * There are multiple types of commands groups available to us. This checks to see if we have
         * any of them set in the yaml. If it's not a command group, it will add a regular command
         * (if it has been implemented). If not it will add a null command and register that
         */
        switch (name) {
            /*
             * A sequential group is probably the easiest to wrap your head around. It takes in an
             * arbitrary amount of commands as parameters, and runs them one after another, or sequentially
             */
            case "sequential group": {
                List<Map<String, Object>> children = (List<Map<String, Object>>) entry.get("commands");
                if (children == null || children.isEmpty()) return new NullCommand();
                ArrayList<Command> built = new ArrayList<>();
                for (Map<String, Object> child : children) {
                    built.add(buildCommand(child));
                }
                return new SequentialGroup(built.toArray(new Command[0]));
            }
            /*
             * A parallel group is similar to a sequential group. It differs in how it runs the commands.
             * A sequential group runs each command one after another. A parallel group runs each defined
             * command at the same time.
             */
            case "parallel group": {
                List<Map<String, Object>> children = (List<Map<String, Object>>) entry.get("commands");
                if (children == null || children.isEmpty()) return new NullCommand();
                ArrayList<Command> built = new ArrayList<>();
                for (Map<String, Object> child : children) {
                    built.add(buildCommand(child));
                }
                return new ParallelGroup(built.toArray(new Command[0]));
            }
            /*
             * A race group is where it gets interesting. This is similar to a parallel group. However,
             * what sets it apart is when it finishes. The parallel group requires all of it's children
             * to finish before it does. A race group only requires one command to finish before it does.
             */
            case "race group": {
                List<Map<String, Object>> children = (List<Map<String, Object>>) entry.get("commands");
                if (children == null || children.isEmpty()) return new NullCommand();
                ArrayList<Command> built = new ArrayList<>();
                for (Map<String, Object> child : children) {
                    built.add(buildCommand(child));
                }
                return new ParallelRaceGroup(built.toArray(new Command[0]));
            }
            /*
             * A deadline group is similar to a race group. However, it has one command (the "deadline command")
             * that controls when it finishes. The deadline group is only looking at that one command
             * to finish before it does.
             */
            case "deadline group": {
                // Expect a single "deadline" command (map) and a "commands" array for the rest
                Map<String, Object> deadlineMap = (Map<String, Object>) entry.get("deadline");
                List<Map<String, Object>> children = (List<Map<String, Object>>) entry.get("commands");

                Command deadlineCmd = (deadlineMap == null) ? new NullCommand() : buildCommand(deadlineMap);

                ArrayList<Command> built = new ArrayList<>();
                if (children != null) {
                    for (Map<String, Object> child : children) {
                        built.add(buildCommand(child));
                    }
                }

                // If there are no other commands, return the deadline alone to avoid constructing with empty varargs
                if (built.isEmpty()) {
                    return new ParallelDeadlineGroup(deadlineCmd);
                } else {
                    return new ParallelDeadlineGroup(deadlineCmd, built.toArray(new Command[0]));
                }
            }
            /*
             * If there is no command group declared, we just look to see if there's a command factory
             * for the specific command's name. If not, then we add a null command. If so, then we just
             * create a command and add it.
             */
            default: {
                CommandFactory factory = commandFactories.get(name);
                if (factory == null) {
                    telemetry.addLine("Unknown Command: " + name + ". Adding null command");
                    telemetry.update();
                    return new NullCommand();
                } else {
                    return factory.create(entry);
                }
            }
        }
    }

    @Override
    public void onWaitForStart() {}

    @Override
    public void onStartButtonPressed() {
        timer.reset();
        allCommands.run();
    }

    @Override
    public void onUpdate() {
        robot.periodic();
        telemetry.update();

        if (timer.seconds() >= 30 && !isDone) {
            telemetry.addLine("Out of time, stopping commands");
            allCommands.stop(true);
            isDone = true;
        }
    }

    @Override
    public void onStop() {}
}