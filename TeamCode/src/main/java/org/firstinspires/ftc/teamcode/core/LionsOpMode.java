package org.firstinspires.ftc.teamcode.core;

import com.pedropathing.geometry.Pose;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.core.robot.PathParser;
import org.firstinspires.ftc.teamcode.core.robot.Robot;
import org.firstinspires.ftc.teamcode.core.robot.drivetrain.Constants;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import dev.nextftc.core.commands.Command;
import dev.nextftc.extensions.pedro.PedroComponent;
import dev.nextftc.ftc.NextFTCOpMode;

@Autonomous(name = "Lions Autonomous", group = "Autonomous")
public class LionsOpMode extends NextFTCOpMode {
    {
        addComponents(
                new PedroComponent(Constants::createFollower)
                // Whatever other components you may desire
                );
    }

    private Robot robot;

    private List<Map<String, Object>> commandList;
    private Map<String, CommandFactory> commands;
    private int commandIndex = 0;
    private Command currentCommand = null;
    boolean commandStarted;

    private final String autoFilePath = "test.yaml";
    private final String pathsFilePath = "paths.yaml";

    // Call this when a command is finished. I can't think of the right name right now
    private void finishCommand() {
        commandIndex++;
        currentCommand = null;
        commandStarted = false;
    }

    private Command getNextCommand() {
        Map<String, Object> entry = commandList.get(commandIndex);
        String name = ((String) Objects.requireNonNull(entry.get("name"))).toLowerCase();

        CommandFactory factory = commands.get(name);
        if (factory == null) return null;

        return factory.create(entry);
    }

    private String getNextCommandName() {
        Map<String, Object> entry = commandList.get(commandIndex);
        return ((String) Objects.requireNonNull(entry.get("name"))).toLowerCase();
    }

    @Override public void onInit() {
        Yaml yaml = new Yaml();
        Map<String, Object> data;

        try {
            data = yaml.load(hardwareMap.appContext.getAssets().open(autoFilePath));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        PathParser.setFilePath(pathsFilePath);

        robot = new Robot(PedroComponent.follower());
        robot.setStartingPose(new Pose(56, 8, Math.toRadians(90)));
        robot.initialize();

        commandList = (List<Map<String, Object>>) data.get("commands");
        commands = robot.commands.getCommands();
    }
    @Override public void onWaitForStart() {}
    @Override public void onStartButtonPressed() {}
    @Override public void onUpdate() {
        robot.periodic();
        this.telemetry.update();

        if (commandIndex >= commandList.size()) {
            this.telemetry.addLine("All commands complete.");
            return;
        }

        if (currentCommand == null) {
            currentCommand = getNextCommand();

            if (currentCommand == null) {
                this.telemetry.addLine("Warning: unknown command '" + getNextCommandName() + "', ignoring.");
                finishCommand();
            } else {
                currentCommand.schedule();
                commandStarted = true;
            }
        } else {
            currentCommand.update();

            this.telemetry.addLine("Current command: " + currentCommand.name());
            if (currentCommand.isDone()) {
                finishCommand();
            }
        }
    }
    @Override public void onStop() {}
}